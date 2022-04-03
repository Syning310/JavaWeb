package domain;


import java.util.Date;
import java.util.List;

// 回复
public class Reply {
    private Integer id;
    private String content;  // 回复内容
    private Date replyDate;  // 回复时间
    private UserBasic author;  // 回复信息的主人
    private Topic topic;  // 对应的话题    m : 1   // 多条回复都对应一个话题
    
    
    private HostReply hostReply;  // 1 : n   // 一条回复可能会有多条主人回复 
    
    

    public Reply() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public HostReply getHostReply() {
        return hostReply;
    }

    public void setHostReply(HostReply hostReply) {
        this.hostReply = hostReply;
    }
}
