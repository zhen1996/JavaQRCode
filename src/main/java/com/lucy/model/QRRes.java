package com.lucy.model;

public class QRRes {
    private int errno;

    private String errmsg;

    private String parseText;

    public QRRes(int errno, String errmsg) {
        this.errno = errno;
        this.errmsg = errmsg;
    }


    public int getErrno() {
        return this.errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getParseText() {
        return parseText;
    }

    public void setParseText(String parseText) {
        this.parseText = parseText;
    }
}