package com.vincent.teng.projectservice.daily.dothread;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/1920:08
 */
public class LocalDateTest {

    public static void main(String[] args) {
        //获取当前时间
        LocalDateTime nowTime= LocalDateTime.now();
        //自定义时间
        LocalDateTime endTime = LocalDateTime.of(2019, 8, 19, 19, 10, 10);

        Duration duration =Duration.between(endTime,nowTime);
        System.out.println(duration.toDays());
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes()-duration.toHours()*60);
    }
}
