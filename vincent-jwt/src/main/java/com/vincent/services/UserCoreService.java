package com.vincent.services;

import com.vincent.dto.CheckAuthReq;
import com.vincent.dto.CheckAuthResp;
import com.vincent.dto.UserLoginReq;
import com.vincent.dto.UserLoginResp;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1916:14
 */
public interface UserCoreService {
    UserLoginResp login(UserLoginReq request);

    CheckAuthResp validToken(CheckAuthReq req);
}
