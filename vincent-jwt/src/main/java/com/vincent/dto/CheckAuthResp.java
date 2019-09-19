package com.vincent.dto;

import java.io.Serializable;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1916:46
 */
public class CheckAuthResp implements Serializable {

    private static final long serialVersionUID = 2647572961859306476L;

    private String uid;

    private String code;
    private String msg;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CheckAuthResp{" +
                "uid='" + uid + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
