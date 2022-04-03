package controller;

import bo.ReplyBO;
import domain.Reply;
import domain.Topic;
import domain.UserBasic;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class replyController {
    ReplyBO replyBO = null;
    
    public String addReply(String content, HttpSession session) {
        
        Reply reply = new Reply();
        
        // 1、设置内容，从表单中传来的内容
        reply.setContent(content);
        // 2、设置当前日期
        reply.setReplyDate(new Date());
        // 3、设置回复信息的作者，也就是当前登录用户
        UserBasic userBasic = (UserBasic) session.getAttribute("userBasic");  // 当前登录的用户
        reply.setAuthor(userBasic);
        // 4、设置对应的话题(给哪个话题留言)
        Topic topic = (Topic)session.getAttribute("topic");  // 当前回复的话题
        reply.setTopic(topic);
        
        
        // 5、添加进数据库
        int update = replyBO.addReply(reply.getContent(), reply.getReplyDate(), reply.getAuthor().getId(), reply.getTopic().getId());
        
        // 6、将添加进入的这条回复，添加到当前话题的，ReplyList中
        if (update > 0) {
            List<Reply> topic_replyList = topic.getReplyList();
            topic_replyList.add(reply);
        }
        
        return "redirect:topic.do?operate=topicDetail&id=" + topic.getId();
        
    }
    
    
    
    public String delReply(Integer replyId, HttpSession session) {
        
        // 1、取出当前点入的话题
        Topic topic = (Topic)session.getAttribute("topic");
        
        // 2、取出这个话题的回复List表，然后和 replyId 对比，找到对应的回复
        List<Reply> replyList = topic.getReplyList();
        Reply needDelReply = null;
        for (Reply reply : replyList) {
            if (reply.getId() == replyId) {
                // 如果 id 相等，则说明是需要删除的 reply 
                needDelReply = reply;
                break;
            }
        }
        
        // 传入需要删除的 reply ，这个方法会判断这个 reply 中，是否有 HostReply 如果有会删除它
        int update = replyBO.delReplyByReply(needDelReply);
        System.out.println(update > 0 ? "回复删除成功" : "回复删除失败");
        
        
        return "redirect:topic.do?operate=topicDetail&id=" + topic.getId();
    }
    
    
    
    
    
    
}
