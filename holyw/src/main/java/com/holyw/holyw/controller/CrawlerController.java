package com.holyw.holyw.controller;

import com.holyw.holyw.common.HttpMessage;
import com.holyw.holyw.common.ResultEntity;
import com.holyw.holyw.config.ScheduleConfig;
import com.holyw.holyw.service.CronService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/crawler")
public class CrawlerController {
    @Autowired
    private CronService cronService;
    @Autowired
    private ScheduleConfig scheduleConfig;

    private static Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    /**
     * start crawler by keyword
     * @param keyword
     * @return
     */
    @PostMapping("/start")
    public ResultEntity startCrawler(String keyword,String type,Integer threadNum) {
        try {
            //String cron = cronService.findByCronIdAndStatus("1","1").getCron();
            String cron = "0/2 * * * * *";
            String CrawlerKeyword = scheduleConfig.setCron(cron, keyword,type,threadNum);
            return ResultEntity.result(HttpMessage.SUCCESS,CrawlerKeyword, HttpStatus.OK);
        }catch (Exception e) {
            logger.error("start crawler error..."+e);
            return ResultEntity.result(HttpMessage.ERROR,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * （关闭定时任务）
     */
    @PostMapping("/close")
    public ResultEntity closeCrawler(String keyword) {
        try {
            boolean flag = scheduleConfig.stopCron(keyword);
            if(flag) {
                logger.info("close keyword:"+keyword+" success");
                return ResultEntity.result(HttpMessage.SUCCESS,keyword, HttpStatus.OK);
            }else {
                return ResultEntity.result("keyword:"+keyword+" don't exist",keyword, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (Exception e){
            return ResultEntity.result(HttpMessage.ERROR,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * （关闭定时任务）
     */
    @PostMapping("/getCrawlers")
    public ResultEntity getCrawlers() {
        try {

            return ResultEntity.result(HttpMessage.SUCCESS,ScheduleConfig.threadPoolTaskSchedulerMap, HttpStatus.OK);


        }catch (Exception e){
            return ResultEntity.result(HttpMessage.ERROR,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
