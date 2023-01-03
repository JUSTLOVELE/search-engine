package com.hse.ikanalyzer.util;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.cn.smart.HMMChineseTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;

import java.util.List;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022-05-06
 * @version 1.00.00
 * @history:
 */
public class CustomSmartChineseAnalyzer extends Analyzer {

    private CharArraySet extendWords;

    private List<String> words;

    private CharArraySet stopWords;

    public CustomSmartChineseAnalyzer(CharArraySet stopWords, List<String> words) {
        this.stopWords = stopWords;
        this.words = words;
    }

    @Override
    public Analyzer.TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer tokenizer = new HMMChineseTokenizer();
        TokenStream result = tokenizer;
        result = new LowerCaseFilter(result);

        result = new PorterStemFilter(result);

        if (!stopWords.isEmpty()) {
            result = new StopFilter(result, stopWords);
        }

        if (!words.isEmpty()) {
            result = new ExtendWordFilter(result, words);
        }

        return new TokenStreamComponents(tokenizer, result);
    }

    @Override
    protected TokenStream normalize(String fieldName, TokenStream in) {
        return new LowerCaseFilter(in);
    }
}