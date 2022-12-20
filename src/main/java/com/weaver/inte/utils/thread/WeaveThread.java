package com.weaver.inte.utils.thread;


public abstract class WeaveThread extends Thread {

    private WeaveThreadCore weaveThreadCore;

    public void setThreadWeaveCore(WeaveThreadCore weaveThreadCore) {
        this.weaveThreadCore = weaveThreadCore;
    }

    protected abstract void work() throws Exception;

    protected <T> T getObject(Class<T> c) {
        return weaveThreadCore.getObject(c);
    }

    protected <T> T getObjectByAlias(String alias) {
        return weaveThreadCore.getObject(alias);
    }
}
