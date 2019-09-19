package com.vincent.dto;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1916:16
 */
public class UserLoginResp {

    private String token;
    private Long uid;
    private String code;
    private String msg;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
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
        return "UserLoginResp{" +
                "token='" + token + '\'' +
                ", uid=" + uid +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
