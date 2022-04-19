package com.emeka.rabbit_spring_scheduler.scheduler;

import com.emeka.rabbit_spring_scheduler.client.RabbitClient;
import com.emeka.rabbit_spring_scheduler.entity.RabbitmqQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RabbitmqScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqScheduler.class);

    @Autowired
    private RabbitClient client;

    @Scheduled(fixedDelay = 90000)
    public void sweepDirtyQueue(){
        try {
            List<RabbitmqQueue> dirtyQueues = client.getAllQueues().stream().filter(p -> p.isDirty())
                    .collect(Collectors.toList());
            dirtyQueues.forEach(q -> LOGGER.info("Queue {} has {} unprocessed messages ",
                    q.getName(),q.getMessages()));
        }catch (Exception e){
            LOGGER.warn("Cannot sweep queue: {}",e.getMessage());
        }

    }
}
