package com.holyw.holyw.service;

import com.holyw.holyw.mapper.CronMapper;
import com.holyw.holyw.model.Cron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronService {
    @Autowired
    private CronMapper cronMapper;


    public Cron findByCronIdAndStatus(String cronId, String status) {
        return cronMapper.findByCronIdAndStatus(cronId,status);
    }
}