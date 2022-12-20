package com.weaver.inte;

import com.weaver.inte.utils.thread.WeaveThread;

import java.util.Date;
import java.util.Map;

public class Thread1 extends WeaveThread {
    @Override
    protected void work() throws Exception {
        Thread.sleep(30000);
        Example a = getObject(Example.class);
        System.out.println(new Date() + " Tread1：" + a.getName() + "结束");
        Map<String, Object> dataMap = this.getObjectByAlias("data");
        dataMap.put("thread1", "再见了");
    }
}
