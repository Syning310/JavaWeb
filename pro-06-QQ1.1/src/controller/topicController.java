package controller;

import bo.HostReplyBO;
import bo.ReplyBO;
import bo.TopicBO;
import bo.UserBasicBO;

import domain.HostReply;
import domain.Reply;
import domain.Topic;
import domain.UserBasic;


import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;

public class topicController {

    private TopicBO topicBO = null;
    private ReplyBO replyBO = null; 
    private UserBasicBO userBasicBO = null;
    private HostReplyBO hostReplyBO = null;
    
    // 传入的是 topic 的 id
    public String topicDetail(Integer id, HttpSession session) {
        
        // 1、获取当前所在 谁的 的空间，也就是 session 作用域中 key(friend) 映射的对象
        UserBasic userBasic = (UserBasic)session.getAttribute("friend");
        
        // 2、从这个用户中取得它的会话列表与 id 相符合的话题，并设置到 session 作用域中
        List<Topic> topicList = userBasic.getTopicList();
        Topic topic = null;
        for (Topic t : topicList) {
            if (t.getId() == id) {
                topic = t;  // 指向符合条件的话题
                session.setAttribute("topic", t);
                break;
            }
        }
        
        // 3、给这个 topic 的 replyList 赋值，replyList 取出来的 reply 对象，如果有对应的 HostReply 也要给赋值
        
        // 3.1、通过这个 topic 的 id 取得 List<reply> 集合，因为 reply 表中的 topic 就是针对于 topic 的 id
        List<Reply> replyList = replyBO.getReplyListByTopicId(topic.getId());
        
        // 但是这个集合中的 author 和 topic 还没有赋值，通过以下循环赋值
       for (int i = 0; i < replyList.size(); ++i) {
           Reply reply = replyList.get(i);  // 取出回复对象
           
           // 3.2、从数据库中取得这个 reply(回复) 的 author(作者 id)，然后通过这个 author_id 取得整个 UserBasic
           Integer author_id = replyBO.getAuthorByReplyId(reply.getId());
           
           // 3.3、通过 author_id 取得 UserBasic 对象，然后设置进 reply 中
           UserBasic reply_author = userBasicBO.getUserBasicByUserId(author_id);
           reply.setAuthor(reply_author);
           
           // 3.4、因为这个 replyList 本就是用 topic 的 id 取出来的，也就是说，这些回复本就是属于这个 topic 的
           reply.setTopic(topic);  // 所以直接设置到话题的字段上即可
           
           
           // 3.5、传入 reply 的 id 返回这个回复对象的主人回复 HostReply(主人回复)  : (如果为空则没有)
           // 这个主人回复只返回了 id content hostReplyDate  
           // 对其余未赋值的  author 和 reply 进行设置
           HostReply hostReply = hostReplyBO.getHostReplyListByReplyId(reply.getId());
           
           // 如果 hostReply 不为空，则说明这条留言有主人回复
           if (hostReply != null) {
               // 主人回复，回复的就是这条留言，因为主人回复就是通过 reply.id 得到的
               hostReply.setReply(reply);
               // author 就是主人，那么就是这个空间的主人，也就是 key(friend) 映射的对象
               hostReply.setAuthor(userBasic);
           }

               
           
           // 给主人回复设置完整信息之后，将主人回复设置到 reply 的 hostReplyList 中
           reply.setHostReply(hostReply);
           
           
       }
       
       // 最后，将 List<reply> 设置入 话题中
       topic.setReplyList(replyList);
       
       
            return "frames/detail";    
    }

    
    
    
    // 删除话题
    public String delTopic(Integer topicId, HttpSession session) {
        
        // 1、因为只能删除自己空间的话题，所以取出当前登录用户的UserBasic
        UserBasic user = (UserBasic)session.getAttribute("userBasic");

        // 2、取出当前用户的话题列表 topicList
        List<Topic> topicList = user.getTopicList();

        // 3、遍历 topicList 取出这个对象出来
        Topic topic = null;
        int index = -1;
        for (int i = 0; i < topicList.size(); ++i) {
            Topic t = topicList.get(i);  // 取出单个 Topic 对象 
            if (t.getId() == topicId) {
                topic = t;
                index = i;
                break;
            }
        }
        
        // 4、将要删除的topic从集合中删除
        topicList.remove(index);
        
        // 将topic从数据库中删除
        int update = topicBO.delTopic(topic);

        System.out.println(update > 0 ? "删除话题成功" : "删除话题失败");
        
        return "redirect:topic.do?operate=getTopicList";
        
    }
    
    
    
    // 刷新删除话题后的话题列表
    public String getTopicList(HttpSession session) {
        
        // 取出当前登录的用户
        UserBasic user = (UserBasic)session.getAttribute("userBasic");
        
        // friend 为当前访问的用户的空间，friend 若和 登录者一致，说明查看自己的空间
        //session.setAttribute("friend", user);  // 此动作是覆盖一下 friend 中的数据，以确保数据正确
        
        // 查询当前用户的话题列表
        List<Topic> topicList = topicBO.getUserTopicList(user.getId());
        
        for (Topic topic : topicList) {
            topic.setAuthor(user);
        }
        
        // 设置给当前用户
        user.setTopicList(topicList);
        
        return "frames/main";
    }
    
    
    
    
    // 添加话题
    public String addTopic(String title, String content, HttpSession session) {
        
        Topic topic = new Topic();
        
        topic.setTitle(title);  // 设置标题
        topic.setContent(content);  // 设置内容
        
        // 设置可以在添加时在sql语句中用 now() 添加时间
        
        // 只有在自己空间才能看见新建话题的按钮，所以从 session 作用域中的 userBasic 或 friend 中拿作者 id 都是一样的
        UserBasic user = (UserBasic)session.getAttribute("friend");
        topic.setAuthor(user);
        
        List<Topic> topicList = user.getTopicList();
        topicList.add(topic);
        
        Integer update = topicBO.addTopic(topic);
        
        
        
        return "redirect:topic.do?operate=getTopicList";
    }
    
    
    
    
    
}
