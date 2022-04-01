package domain;


import java.util.Date;

// 话题主人的回复信息
public class HostReply {
    private Integer id;
    private String content; // 回复的内容
    private Date hostReply; // 回复的时间
    private UserBasic author; // 话题主人回复的信息，针对回复   // m : 1
    private Reply reply; // 对应的回复   // 可能有多条主人回复 向一条回复
    
    
    
    public HostReply() { }

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

    public Date getHostReply() {
        return hostReply;
    }

    public void setHostReply(Date hostReply) {
        this.hostReply = hostReply;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }
}
