package com.holyw.holyw.config;


import com.holyw.holyw.timmer.BaiduTask;
import com.holyw.holyw.timmer.MM131Task;
import com.holyw.holyw.timmer.TencentTask;
import com.holyw.holyw.timmer.ToutiaoTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduleConfig {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    public static Map<String,List<ThreadPoolTaskScheduler>> threadPoolTaskSchedulerMap = new HashMap<>();


    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        return new ThreadPoolTaskScheduler();
    }
    private ScheduledFuture<?> future;

    private String cron = "";


    public String getCron() {
        return cron;
    }

    public boolean stopCron(String threadPoolTaskSchedulerName) {
        List<ThreadPoolTaskScheduler> threadPoolTaskSchedulers = threadPoolTaskSchedulerMap.get(threadPoolTaskSchedulerName);
        if(CollectionUtils.isEmpty(threadPoolTaskSchedulers)) return false;
        threadPoolTaskSchedulers.stream().forEach(p->{
            p.shutdown();
        });
        /*if (future != null) {
            future.cancel(true);            //取消定时任务
        }*/
        future = null;
        threadPoolTaskSchedulerMap.remove(threadPoolTaskSchedulerName);
        return true;

    }
    public String setCron(String cron,String keyword,String type,Integer threadNum) {
        this.cron = cron;
        if(Objects.isNull(threadNum) || threadNum < 0) {
            threadNum = 1;
        }
        for(int i=0;i<threadNum;i++) {
            threadPoolTaskScheduler = threadPoolTaskScheduler();
            threadPoolTaskScheduler.initialize();
            List<ThreadPoolTaskScheduler> threadPoolTaskSchedulers = threadPoolTaskSchedulerMap.get(keyword);
            if(CollectionUtils.isEmpty(threadPoolTaskSchedulers)) {
                threadPoolTaskSchedulers = new ArrayList<>();
            }
            threadPoolTaskSchedulers.add(threadPoolTaskScheduler);
            threadPoolTaskSchedulerMap.put(keyword,threadPoolTaskSchedulers);
            //stopCron();
            future = threadPoolTaskScheduler.schedule(new Runnable() {

                @Override
                public void run() {
                    try {
                        if("toutiao".equals(type)) {//今日头条
                            ToutiaoTask.run(keyword);
                        }else if("baidu".equals(type)){//百度新闻
                            BaiduTask.run(keyword);

                        }else if("tencent".equals(type)) {//腾讯新闻

                            TencentTask.run(type);
                        }else if("mm131".equals(type)){
                            MM131Task.getInstance().run(keyword);
                        }else {

                            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+",data:"+keyword);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    if ("".equals(cron) || cron == null) {
                        return null;
                    }
                    // 定时任务触发，可修改定时任务的执行周期
                    CronTrigger trigger = new CronTrigger(cron);
                    Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                    return nextExecDate;
                }
            });
        }
        return keyword;
    }
}