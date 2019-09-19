package com.vincent.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1916:15
 */
@Valid
public class UserLoginReq implements Serializable {

    private static final long serialVersionUID = -5515626212731643167L;
    @NotNull
    private String userName;
    @NotNull
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLoginReq{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
