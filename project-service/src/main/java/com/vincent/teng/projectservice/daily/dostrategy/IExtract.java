package com.vincent.teng.projectservice.daily.dostrategy;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/1418:15
 */
public interface IExtract {
    boolean support(String channelNo);

    void extract(String param);
}
