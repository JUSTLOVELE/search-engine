package com.hse;

import com.hse.ikanalyzer.util.CustomSmartChineseAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//https://www.cnblogs.com/leeSmall/p/8994176.html
public class LuceneSmartChineseAnalyzerTest {

    @Test
    public void test_custom_smart_chinese_analyzer() throws Exception {
        String text = "交易中台架构设计：海量并发的高扩展，新业务秒级接入";
        CharArraySet stopWords = CharArraySet.unmodifiableSet(WordlistLoader.getWordSet(
                IOUtils.getDecodingReader(
                        new ClassPathResource("smart_cn_stopword.txt").getInputStream(),
                        StandardCharsets.UTF_8)));
        List<String> words = Collections.singletonList("中台");
        Analyzer analyzer = new CustomSmartChineseAnalyzer(stopWords, words);
        TokenStream tokenStream = analyzer.tokenStream("testField", text);

        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        tokenStream.reset();

        List<String> tokens = new ArrayList<>();
        while (tokenStream.incrementToken()) {
            tokens.add(offsetAttribute.toString());
        }
        tokenStream.end();

        System.out.println(String.format("tokens:%s", tokens));
    }

    @Test
    public void smartTest(){

        try{
            String etext = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
            String chineseText = "张三说的确实在理";
            FileInputStream fileInputStream = new FileInputStream("C:\\workspaces\\EnterpriseFrontFrame\\HY-Search-Engine\\HSE\\src\\main\\resources\\smart_cn_stopword.txt");
            CharArraySet stopWords = CharArraySet.unmodifiableSet(WordlistLoader.getWordSet(
                    IOUtils.getDecodingReader(
                            //new ClassPathResource("C:\\workspaces\\EnterpriseFrontFrame\\HY-Search-Engine\\HSE\\src\\main\\resources\\smart_cn_stopword.txt").getInputStream(),
                            fileInputStream,
                            StandardCharsets.UTF_8)));
            // Lucene 的中文分词器 SmartChineseAnalyzer
            Analyzer smart = new SmartChineseAnalyzer(stopWords);
            TokenStream ts = smart.tokenStream("content", etext);
            System.out.println("smart中文分词器，英文分词效果：");
            doToken(ts);
            ts = smart.tokenStream("content", chineseText);
            System.out.println("smart中文分词器，中文分词效果：");
            doToken(ts);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doToken(TokenStream ts) throws IOException {
        ts.reset();
        CharTermAttribute cta = ts.getAttribute(CharTermAttribute.class);
        while (ts.incrementToken()) {
            System.out.print(cta.toString() + "|");
        }
        System.out.println();
        ts.end();
        ts.close();
    }
}
