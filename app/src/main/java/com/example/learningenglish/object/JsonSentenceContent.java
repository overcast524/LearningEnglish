package com.example.learningenglish.object;

import java.util.List;

public class JsonSentenceContent {

    private List<JsonSentence> sentences;

    private String desc;

    public java.util.List<JsonSentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<JsonSentence>  sentences) {
        this.sentences = sentences;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
