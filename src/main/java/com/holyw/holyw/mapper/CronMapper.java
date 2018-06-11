package com.holyw.holyw.mapper;


import com.holyw.holyw.model.Cron;
import org.springframework.data.repository.CrudRepository;

public interface CronMapper extends CrudRepository<Cron,Integer> {
    public Cron findByCronIdAndStatus(String cronId,String status);
}