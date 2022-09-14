package com.weaver.inte.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreadWeave {
    private List<List<ParallThread>> threadList = new ArrayList<>();
    private CountDownLatch latch;
    private Map<String, Object> cacheObjectMap = new HashMap<String, Object>();

    public ThreadWeave group(ParallThread... threads) {
        for (ParallThread d : threads) {
            d.setThreadWeave(this);
        }
        threadList.add(Stream.of(threads).collect(Collectors.toList()));
        return this;
    }

    public ThreadWeave addObject(Object o) {
        String className = o.getClass().getName();
        if (className.contains("$Proxy")) {
            cacheObjectMap.put(o.getClass().getInterfaces()[0].getName(), o);
        } else {
            cacheObjectMap.put(className, o);
        }
        return this;
    }

    public ThreadWeave execute() {
        if (threadList.isEmpty()) {
            return this;
        }
        if (latch == null) {
            latch = new CountDownLatch(threadList.size());
        }
        for (List<ParallThread> t : threadList) {
            new Thread(() -> {
                try {
                    for (int i = 0; i < t.size(); i++) {
                        ParallThread a = t.get(i);
                        try {
                            a.work();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            }).start();
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
}
