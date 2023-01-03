package com.hse.backend.action;

import com.hse.core.BaseAction;
import com.hse.utils.Constant;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description:搜索action
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022.05.31
 * @version 1.00.00
 * @history:
 */
@RestController
public class SearchAction extends BaseAction {

    private final static Log _logger = LogFactory.getLog(SearchAction.class);

    @Autowired
    private Directory _directory;

    /**
     * 根据name查询
     * {"field":"","text":""}
     * @param json
     * @return
     */
    @RequestMapping(value = "/searchByName", produces = "application/json; charset=utf-8")
    public String searchByName(String json) {

        try{

            JSONObject data = JSONObject.fromObject(json);
            IndexSearcher indexSearcher = getSearcher();
            Query query = new TermQuery(new Term(data.getString(Constant.Key.FIELD),data.getString(Constant.Key.TEXT)));
            TopDocs topDocs = indexSearcher.search(query, 10);
            TotalHits totalHits = topDocs.totalHits;
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<String> results = new ArrayList<>();
            for(ScoreDoc scoreDoc : scoreDocs){
                //文档id
                int docId = scoreDoc.doc;
                //通过文档id获得文档对象
                Document doc = indexSearcher.doc(docId);
                Iterator<IndexableField> iterator = doc.iterator();

                while(iterator.hasNext()) {

                    IndexableField next = iterator.next();
                    results.add(next.stringValue());
                }
            }

            return renderSuccessList(results.size(), results, null);
        }catch (Exception e) {
            _logger.error("", e);
            return renderFailureList(e.getMessage());
        }
    }

    //@GetMapping(value = "/testSearch", produces = "application/json; charset=utf-8")
//    public String testSearch() {
//
//        try{
//            IndexSearcher indexSearcher = getSearcher();
//            Query query = new TermQuery(new Term("name","杨"));
//            TopDocs topDocs = indexSearcher.search(query, 100);
//            //获得符合查询条件的文档数
//            TotalHits totalHits = topDocs.totalHits;
//            System.out.println(totalHits.value);
//            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//            for(ScoreDoc scoreDoc : scoreDocs){
//                //文档id
//                int docId = scoreDoc.doc;
//                //通过文档id获得文档对象
//                Document doc = indexSearcher.doc(docId);
//                System.out.println("id:"+doc.get("id"));
//                System.out.println("name:"+doc.get("name"));
//            }
//
//            return renderSuccessList(0, "ok");
//        }catch (Exception e) {
//            _logger.error("", e);
//            return renderFailureList(e.getMessage());
//        }
//    }

    private static IndexReader _indexReader;

    /**
     * 设置一个单例
     * @return
     */
    public IndexSearcher getSearcher() {

        try {

            if(_indexReader == null) {
                _indexReader = DirectoryReader.open(_directory);
            }else{
                IndexReader indexReader = DirectoryReader.openIfChanged((DirectoryReader)_indexReader);

                if(indexReader != null) {
                    _indexReader.close();
                    _indexReader = indexReader;
                }
            }

            return new IndexSearcher(_indexReader);

        }catch (Exception e) {
            _logger.error("", e);
        }
        return  null;
    }
}
