package domain;


import java.util.List;

// 用户登录信息
public class UserBasic {
    
    private Integer id;
    private String loginid; // 账号
    private String nickName;  // 昵称
    private String pwd; // 密码
    private String headImg; // 头像
    
    // 用户登录信息 -> 用户详细信息
    private UserDetail userDetail;  // 1 : 1
    // 一个用户发布有多个话题
    private List<Topic> topicList;  // 1 : n
    // 一个用户会对应多个好友
    private List<UserBasic> friendList;  // 
    
    
    public UserBasic() { }

    public Integer getId() {
        return id;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<UserBasic> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<UserBasic> friendList) {
        this.friendList = friendList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
