package com.weaver.inte;

import com.weaver.inte.utils.BaseThread;

public class Thread1 extends BaseThread {
    @Override
    protected void work() {
        try {
            Thread.sleep(2000);
            Example a = getObject(Example.class);
            System.out.println("Tread1：" + a.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread1结束");
    }
}
