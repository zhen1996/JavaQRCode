package com.lucy.model;

public class QRRes {
    private int errno;

    private String text;

    public QRRes(int errno, String text) {
        this.errno = errno;
        this.text = text;
    }


    public int getErrno() {
        return this.errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}