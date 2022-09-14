package com.weaver.inte.utils;



public abstract class WeaveThread extends Thread {

    private ThreadWeaveCore threadWeave;

    public void setThreadWeaveCore(ThreadWeaveCore threadWeave) {
        this.threadWeave = threadWeave;
    }

    protected abstract void work() throws Exception;

    protected <T> T getObject(Class<T> c) {
        return threadWeave.getObject(c);
    }
}
