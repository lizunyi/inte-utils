package com.weaver.inte.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class ParallThread extends Thread {

    private CountDownGroup countDownGroup;

    public void setCountDownGroup(CountDownGroup countDownGroup) {
        this.countDownGroup = countDownGroup;
    }

    protected abstract void work();

    @Override
    public void run() {
        try {
            work();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.exit();
        }
    }

    protected void exit() {
        countDownGroup.countDown();
    }

    protected <T> T getObject(Class<?> c) {
        return countDownGroup.getObject(c);
    }
}
