package com.hse.backend.action;

import com.hse.core.BaseAction;
import com.hse.utils.Constant;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Description:写action
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022.06.06
 * @version 1.00.00
 * @history:
 */
@RestController
public class WriteAction extends BaseAction {

    private final static Log _logger = LogFactory.getLog(WriteAction.class);

    @Autowired
    private Directory directory;

    @Autowired
    private IndexWriterConfig indexWriterConfig;

    private static IndexWriter _indexWriter;

    public IndexWriter getIndexWriter() throws IOException {

        if(_indexWriter == null) {
            _indexWriter = new IndexWriter(directory, indexWriterConfig);
        }

        return _indexWriter;
    }

    /**
     * 创建新的索引
     * type、name默认是text类型,value要根据type变化而变化
     * {"datas":[{"name":"", "value":"", "type":"text"},{},{}]}
     * @param json
     * @return
     */
    @PostMapping(value = "/createIndex", produces = "application/json; charset=utf-8")
    public String createIndex(@RequestBody String json) {

        try{

            IndexWriter indexWriter = getIndexWriter();
            Document document = new Document();
            JSONObject jsonObject = JSONObject.fromObject(json);
            JSONArray datas = jsonObject.getJSONArray(Constant.Key.DATAS);

            for(int i=0; i<datas.size(); i++) {

                JSONObject data = datas.getJSONObject(i);
                //不包含type或者type值为text都是text类型,因为text是默认类型
                if(!data.containsKey(Constant.Key.TYPE) ||
                    data.getString(Constant.Key.TYPE).equals(Constant.Key.TEXT)) {

                    document.add(new TextField(data.getString(Constant.Key.NAME),
                            data.getString(Constant.Key.VALUE),
                            Field.Store.YES));
                }
            }

            indexWriter.addDocument(document);
            indexWriter.commit();

            return renderSuccessList(0, Constant.CREATE_INDEX_SUCCESS);
        }catch (Exception e) {
            _logger.error("", e);

            return renderFailureList(e.getMessage());
        }
    }

    /**
     * 测试1
     * @return
     */
    //@GetMapping(value = "/testWrite1", produces = "application/json; charset=utf-8")
    public String test() {

        try{
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            //创建Document对象
            Document document = new Document();
            //创建Field对象
            //切分词、索引、存储
            document.add(new TextField("name", "杨祖亮", Field.Store.YES));
            document.add(new TextField("addr", "钱隆珠宝城", Field.Store.YES));
            document.add(new TextField("info", "办公地址", Field.Store.YES));
            document.add(new TextField("id", "1001", Field.Store.YES));
            //将文档追加到索引库中
            indexWriter.addDocument(document);
            indexWriter.close();
            return renderSuccessList(0, "ok");
        }catch (Exception e) {
            _logger.error("", e);
            return renderFailureList(e.getMessage());
        }
    }

}
