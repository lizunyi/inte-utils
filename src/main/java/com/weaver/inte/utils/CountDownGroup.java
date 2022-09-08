package com.weaver.inte.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CountDownGroup {

    private List<ParallThread> threadList = new ArrayList<>();
    private CountDownLatch latch;
    private Map<String, Object> cacheObjectMap = new HashMap<>();

    public CountDownGroup addThread(ParallThread d) {
        d.setCountDownGroup(this);
        threadList.add(d);
        return this;
    }

    public CountDownGroup addObject(Object o) {
        cacheObjectMap.put(o.getClass().getName(), o);
        return this;
    }

    public CountDownGroup execute() {
        if(threadList.isEmpty()){
            return this;
        }
        if (latch == null) {
            latch = new CountDownLatch(threadList.size());
        }
        for (ParallThread t : threadList) {
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.reset();
        return this;
    }

    void reset() {
        threadList.clear();
        cacheObjectMap.clear();
        latch = null;
    }

    void countDown() {
        latch.countDown();
    }

    <T> T getObject(Class<T> c) {
        String className = c.getName();
        Object obj = cacheObjectMap.get(className);
        if (obj != null) {
            return (T) obj;
        }
        return null;
    }

    void error(Exception e) throws Exception {
        throw e;
    }
}
