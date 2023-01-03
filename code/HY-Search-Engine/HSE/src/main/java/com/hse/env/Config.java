package com.hse.env;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022.05.31
 * @version 1.00.00
 * @history:
 */
@Configuration
public class Config {

    @Autowired
    private YmlProjectConfig _ymlProjectConfig;

    private final static Log _logger = LogFactory.getLog(Config.class);

    /**
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Directory directory() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(_ymlProjectConfig.getIndexpath()));
        return directory;
    }

    /**
     *
     * @return
     */
    @Bean
    public IndexWriterConfig indexWriterConfig() {
        Analyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        return config;
    }
}
