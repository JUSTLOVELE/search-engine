package com.hse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


/**
 * IKAnalyzer分词器集成测试:
 * 细粒度切分：把词分到最细
 * 智能切分：根据词库进行拆分符合我们的语言习惯
 *
 * @author THINKPAD
 *
 */
public class IKAnalyzerTest {

    @Test
    public void search4() {

        try{
            String[] fields = {"name", "price"};
            //设置影响相关度排序的参数
            Map<String, Float> boots = new HashMap<>();
            boots.put("price", 1000000f);
            Analyzer analyzer = new IKAnalyzer();
            MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(fields, analyzer, boots);
            Query q = multiFieldQueryParser.parse("杨");
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\ik"));
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            TopDocs topDocs = indexSearcher.search(q, 100);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void search3() {

        try{
            Analyzer analyzer = new IKAnalyzer();
            QueryParser queryParser = new QueryParser("name", analyzer);
            Query query = queryParser.parse("name:杨 AND 手机"); // name: 杨 OR 沈
            Query q2 = IntPoint.newRangeQuery("price", 100, 1000);
            // BooleanClause.Occur.MUST 并且相当于and
            //  BooleanClause.Occur.SHOULD 或者相当于or
            //  BooleanClause.Occur.MUST_NOT 非相当于not
            BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
            booleanQuery.add(query, BooleanClause.Occur.MUST);
            booleanQuery.add(q2, BooleanClause.Occur.MUST);
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\ik"));
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            TopDocs topDocs = indexSearcher.search(booleanQuery.build(), 100);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search2() {

        try {
            Analyzer analyzer = new IKAnalyzer();
            QueryParser queryParser = new QueryParser("name", analyzer);
            Query query = queryParser.parse("杨");
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\ik"));
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            TopDocs topDocs = indexSearcher.search(query, 100);
            TotalHits totalHits = topDocs.totalHits;
            System.out.println(totalHits.value);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for(ScoreDoc scoreDoc : scoreDocs){
                //文档id
                int docId = scoreDoc.doc;
                //通过文档id获得文档对象
                Document doc = indexSearcher.doc(docId);
                System.out.println("id:"+doc.get("id"));
                System.out.println("name:"+doc.get("name"));
            }
            //资源释放
            indexReader.close();


        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void createIndex() {
        try {
            //1.指定索引文件的存储位置，索引具体的表现形式就是一组有规则的文件
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\ik"));
            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
//            boolean create = true;
//
//            if(create) {
//                config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
//            }else{
//                config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
//            }
            //3.创建IndexWriter对象，作用就是创建索引
            IndexWriter indexWriter = new IndexWriter(directory, config);
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
            System.out.println("create index success");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doToken(TokenStream ts) throws IOException {
        ts.reset();
        CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(cta.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();
    }

    public static void main(String[] args) throws IOException {

        String etext = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
        String chineseText = "张三说的确实在理。";
        /**
         * ikanalyzer 中文分词器 因为Analyzer的createComponents方法API改变了 需要我们自己实现
         * 分析器IKAnalyzer4Lucene7和分词器IKTokenizer4Lucene7
         */
        // IKAnalyzer 细粒度切分
        try (Analyzer ik = new IKAnalyzer();) {
            TokenStream ts = ik.tokenStream("content", etext);
            System.out.println("IKAnalyzer中文分词器 细粒度切分，英文分词效果：");
            doToken(ts);
            ts = ik.tokenStream("content", chineseText);
            System.out.println("IKAnalyzer中文分词器 细粒度切分，中文分词效果：");
            doToken(ts);
        }catch (Exception e) {
            e.printStackTrace();
        }

        // IKAnalyzer 智能切分
        try (Analyzer ik = new IKAnalyzer(true);) {
            TokenStream ts = ik.tokenStream("content", etext);
            System.out.println("IKAnalyzer中文分词器 智能切分，英文分词效果：");
            doToken(ts);
            ts = ik.tokenStream("content", chineseText);
            System.out.println("IKAnalyzer中文分词器 智能切分，中文分词效果：");
            doToken(ts);
        }catch (Exception e ) {
            e.printStackTrace();
        }
    }
}