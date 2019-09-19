package com.vincent.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1916:44
 */
@Valid
public class CheckAuthReq implements Serializable {
    private static final long serialVersionUID = 7817459248864805986L;

    @NotNull
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CheckAuthRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
