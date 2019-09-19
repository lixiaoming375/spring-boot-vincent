package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/2219:19
 */
public class LockTest {
    static Lock lock=new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
    }
}
