package com.wrox.begspring;

public class ScreenWriter implements ResultWriter {
    public ScreenWriter() {
    }

    public void showResult(String result) {
        System.out.println(result);
    }
}