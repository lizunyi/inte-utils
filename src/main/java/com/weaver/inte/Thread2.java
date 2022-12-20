package com.weaver.inte;

import com.weaver.inte.utils.thread.WeaveThread;

import java.util.Date;
import java.util.Map;

public class Thread2 extends WeaveThread {
    @Override
    protected void work() throws Exception {
        Example a = getObject(Example.class);
        System.out.println(new Date() + " Tread2：" + a.getName() + "结束");
        Map<String, Object> dataMap = this.getObjectByAlias("data");
        dataMap.put("thread2", "你好");
    }
}
