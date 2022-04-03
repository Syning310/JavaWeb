package domain;



import java.util.Date;
import java.util.List;

// 话题，日志
public class Topic {
    private Integer id;
    private String title; // 话题标题
    private String content;  // 话题内容
    private Date topicDate;  // 话题发布时间
    private UserBasic author; // 话题作者的id    m : 1
    
   
    // 一个话题会对应多个回复
    private List<Reply> replyList;  // 1 : n 
    
    
    public Topic() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(Date topicDate) {
        this.topicDate = topicDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

   
}
