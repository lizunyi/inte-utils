package com.weaver.inte.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountDownGroup {
    private List<List<ParallThread>> threadList = new ArrayList<>();
    private CountDownLatch latch;
    private Map<String, Object> cacheObjectMap = new HashMap<String, Object>();

    public CountDownGroup addThread(ParallThread d) {
        threadGroup(d);
        return this;
    }

    public CountDownGroup threadGroup(ParallThread... threads) {
        for (ParallThread d : threads) {
            d.setCountDownGroup(this);
        }
        threadList.add(Stream.of(threads).collect(Collectors.toList()));
        return this;
    }

    public CountDownGroup addObject(Object o) {
        String className = o.getClass().getName();
        if (className.contains("$Proxy")) {
            cacheObjectMap.put(o.getClass().getInterfaces()[0].getName(), o);
        } else {
            cacheObjectMap.put(className, o);
        }
        return this;
    }

    public CountDownGroup execute() {
        if (threadList.isEmpty()) {
            return this;
        }
        if (latch == null) {
            latch = new CountDownLatch(threadList.size());
        }
        for (List<ParallThread> t : threadList) {
            if (t.size() == 1) {
                t.get(0).start();
            } else {
                new Thread(() -> {
                    for (int i = 0; i < t.size(); i++) {
                        ParallThread a = t.get(i);
                        try {
                            a.work();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    latch.countDown();
                }).start();
            }
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
