package BO;

import domain.Topic;

import service.TopicService;


import java.util.List;

public class TopicBO {
    TopicService topicService = null;
    
    // BO 逻辑层
    
    // 1、根据用户Id查询，用户的所有日志
    public List<Topic> getUserTopicList(Integer userId) {
        // 取得的 Topic 集合中的 author 是 null 的
        return topicService.getTopicListByUserId(userId);
    }
    
    
    
    
    
    
}
