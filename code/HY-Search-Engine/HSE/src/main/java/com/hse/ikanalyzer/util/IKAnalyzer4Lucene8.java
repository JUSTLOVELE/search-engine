//package com.hse.ikanalyzer.util;
//
//
//import org.apache.lucene.analysis.Analyzer;
//
///**
// * 因为Analyzer的createComponents方法API改变了需要重新实现分析器
// * @author THINKPAD
// *
// */
//public class IKAnalyzer4Lucene8 extends Analyzer {
//
//    private boolean useSmart = false;
//
//    public IKAnalyzer4Lucene8() {
//        this(false);
//    }
//
//    public IKAnalyzer4Lucene8(boolean useSmart) {
//        super();
//        this.useSmart = useSmart;
//    }
//
//    public boolean isUseSmart() {
//        return useSmart;
//    }
//
//    public void setUseSmart(boolean useSmart) {
//        this.useSmart = useSmart;
//    }
//
//    @Override
//    protected TokenStreamComponents createComponents(String fieldName) {
//        IKTokenizer4Lucene8 tk = new IKTokenizer4Lucene8(this.useSmart);
//        return new TokenStreamComponents(tk);
//    }
//
//}