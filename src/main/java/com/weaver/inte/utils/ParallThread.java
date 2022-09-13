package com.weaver.inte.utils;



public abstract class ParallThread extends Thread {


    private CountDownGroup countDownGroup;

    public void setCountDownGroup(CountDownGroup countDownGroup) {
        this.countDownGroup = countDownGroup;
    }

    protected abstract void work() throws Exception;

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

    protected <T> T getObject(Class<T> c) {
        return countDownGroup.getObject(c);
    }
}
