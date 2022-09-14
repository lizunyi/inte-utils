package com.weaver.inte.utils;



public abstract class ParallThread extends Thread {

    private ThreadWeave threadWeave;

    public void setThreadWeave(ThreadWeave threadWeave) {
        this.threadWeave = threadWeave;
    }

    protected abstract void work() throws Exception;

    protected <T> T getObject(Class<T> c) {
        return threadWeave.getObject(c);
    }
}
