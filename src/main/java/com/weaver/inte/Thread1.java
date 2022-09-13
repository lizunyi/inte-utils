package com.weaver.inte;

import com.weaver.inte.utils.ParallThread;

public class Thread1 extends ParallThread {
    @Override
    protected void work() throws Exception {
        Thread.sleep(2000);
        Example a = getObject(Example.class);
        System.out.println("Tread1：" + a.getName());
        System.out.println("Thread1结束");
    }
}
