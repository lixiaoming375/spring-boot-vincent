package com.vincent.teng.projectservice.daily.dostrategy;

import org.springframework.stereotype.Component;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/1418:17
 */
@Component
public class OperationSubstract implements IExtract {
    @Override
    public boolean support(String channelNo) {
        return channelNo.equals("sub")?true:false;
    }

    @Override
    public void extract(String param) {
        System.out.println("执行减法。。。。");
    }
}
