package com.hse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.nio.file.Paths;


public class MainTest {

    public void deleteIndexTest() {
        try{
            Document document = new Document();
            document.add(new TextField("name", "杨33",  Field.Store.YES));
            document.add(new TextField("id", "1002",  Field.Store.YES));
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\index"));
            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, config);
            //第一个参数:词元，指的是指定修改条件
            //第二个参数：修改的具体内容
            indexWriter.deleteDocuments(new Term("id", "1001"));
            indexWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void updateIndexTest() {

        try {
            Document document = new Document();
            document.add(new TextField("name", "杨33",  Field.Store.YES));
            document.add(new TextField("id", "1002",  Field.Store.YES));
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\index"));
            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, config);
            //第一个参数:词元，指的是指定修改条件
            //第二个参数：修改的具体内容

            //indexWriter.updateDocument(new Term("id", "1001"), document);
            indexWriter.addDocument(document);
            indexWriter.close();
            System.out.println("更新一个index");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void search2() {

        try {
            Analyzer analyzer = new SmartChineseAnalyzer();
            QueryParser queryParser = new QueryParser("name", analyzer);
            Query query = queryParser.parse("杨");
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\index"));
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
    public void demoSearchIndex() {

        try{
            //1.指定索引文件的存储位置，索引具体的表现形式就是一组有规则的文件
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\index"));
            //2.IndexReader对象
            IndexReader indexReader = DirectoryReader.open(directory);
            //3.创建查询对象，IndexSearcher
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //使用term,查询公司名称中包含"北京"的所有的文档对象
            Query query = new TermQuery(new Term("name","杨"));
            TopDocs topDocs = indexSearcher.search(query, 100);
            //获得符合查询条件的文档数
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
            Directory directory = FSDirectory.open(Paths.get("C:\\logs\\index"));
            Analyzer analyzer = new SmartChineseAnalyzer();
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
            indexWriter.commit();
            System.out.println("create index success");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
