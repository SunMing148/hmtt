package com.heima.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.heima.apis.schedule.IScheduleClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.TaskTypeEnum;
import com.heima.model.schedule.dtos.Task;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.utils.common.JdkSerializeUtil;
import com.heima.utils.common.ProtostuffUtil;
import com.heima.wemedia.service.WmNewsTaskService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
public class WmNewsTaskServiceImpl  implements WmNewsTaskService {

    private final IScheduleClient scheduleClient;
    private final WmNewsAutoScanServiceImpl wmNewsAutoScanService;

    /**
     * 添加任务到延迟队列中
     * @param id          文章的id
     * @param publishTime 发布的时间  可以做为任务的执行时间
     */
    @Override
    @Async
    public void addNewsToTask(Integer id, Date publishTime) {

        log.info("添加任务到延迟服务中----begin");

        Task task = new Task();
        task.setExecuteTime(publishTime.getTime());
        task.setTaskType(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType());
        task.setPriority(TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        WmNews wmNews = new WmNews();
        wmNews.setId(id);
//        task.setParameters(ProtostuffUtil.serialize(wmNews));   //Protostuff序列话工具有问题，暂时用jdk序列化 TODO
        task.setParameters(JdkSerializeUtil.serialize(wmNews));
        scheduleClient.addTask(task);

        log.info("添加任务到延迟服务中----end");

    }

    @Scheduled(fixedRate = 5000)    //每5秒执行一次此方法
    @Override
//    @SneakyThrows
    public void scanNewsByTask() {
        log.info("文章审核---消费任务执行---begin---");
        ResponseResult responseResult = scheduleClient.poll(TaskTypeEnum.NEWS_SCAN_TIME.getTaskType(), TaskTypeEnum.NEWS_SCAN_TIME.getPriority());
        if(responseResult.getCode().equals(200) && responseResult.getData() != null){
            String json_str = JSON.toJSONString(responseResult.getData());
            Task task = JSON.parseObject(json_str, Task.class);
            byte[] parameters = task.getParameters();
//            WmNews wmNews = ProtostuffUtil.deserialize(parameters, WmNews.class); //Protostuff序列话工具有问题，暂时用jdk反序列化 TODO
            WmNews wmNews = JdkSerializeUtil.deserialize(parameters, WmNews.class);
            System.out.println(wmNews.getId()+"-----------");
            wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
        }
        log.info("文章审核---消费任务执行---end---");
    }
}