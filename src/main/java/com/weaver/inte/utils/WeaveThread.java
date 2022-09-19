package com.weaver.inte.utils;


public abstract class WeaveThread extends Thread {

    private WeaveThreadCore weaveThreadCore;

    public void setThreadWeaveCore(WeaveThreadCore weaveThreadCore) {
        this.weaveThreadCore = weaveThreadCore;
    }

    protected abstract void work() throws Exception;

    protected <T> T getObject(Class<T> c) {
        return weaveThreadCore.getObject(c);
    }
}
