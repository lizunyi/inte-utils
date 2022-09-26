package com.weaver.inte.utils;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeaveThreadCore {
    private List<List<WeaveThread>> threadList = new ArrayList<>();
    private CountDownLatch latch;
    private Map<String, Object> cacheObjectMap = new HashMap<String, Object>();
    private List<Thread> threadControlList = new ArrayList<>();
    private int timeout = -1;

    /***
     * timeout 单位秒
     * @param threads
     * @param timeout
     * @return
     */
    public WeaveThreadCore timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public WeaveThreadCore group(WeaveThread... threads) {
        for (WeaveThread d : threads) {
            d.setThreadWeaveCore(this);
        }
        threadList.add(Stream.of(threads).collect(Collectors.toList()));
        return this;
    }

    public WeaveThreadCore addObject(Object o) {
        String className = o.getClass().getName();
        if (className.contains("$Proxy")) {
            cacheObjectMap.put(o.getClass().getInterfaces()[0].getName(), o);
        } else {
            cacheObjectMap.put(className, o);
        }
        return this;
    }

    public WeaveThreadCore addObject(String alias, Object o) {
        cacheObjectMap.put(alias, o);
        return this;
    }

    public WeaveThreadCore execute() {
        if (threadList.isEmpty()) {
            return this;
        }
        if (latch == null) {
            latch = new CountDownLatch(threadList.size());
        }
        for (List<WeaveThread> t : threadList) {
            Thread createThread = new Thread(() -> {
                try {
                    for (int i = 0; i < t.size(); i++) {
                        WeaveThread a = t.get(i);
                        a.work();
                    }
                } catch (InterruptedException e) {

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (latch != null) latch.countDown();
                }
            });
            threadControlList.add(createThread);
            createThread.start();
        }
        try {
            if (timeout == -1) {
                latch.await();
            } else {
                boolean isStop = latch.await(timeout, TimeUnit.SECONDS);
                System.out.println(new Date() + " 终止," + isStop);
                if(!isStop) {
                    for (Thread t : threadControlList) {
                        try {
                            t.interrupt();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.reset();
        return this;
    }

    void reset() {
        threadList.clear();
        cacheObjectMap.clear();
        threadControlList.clear();
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

    <T> T getObject(String alias) {
        Object obj = cacheObjectMap.get(alias);
        if (obj != null) {
            return (T) obj;
        }
        return null;
    }
}
