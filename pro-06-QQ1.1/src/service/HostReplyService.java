package service;

import dao.HostReplyDAO;
import domain.HostReply;

import java.util.List;

public class HostReplyService {
    HostReplyDAO hostReplyDAO = null;
    
    
    // 通过 reply 的 id 返回 HostReply 
    public HostReply getHostReplyListByReplyId(Integer id) {
        String sql = "select id, content, hostReplyDate from host_reply where reply=?";
        return hostReplyDAO.querySingle(sql, HostReply.class, id);
    }
    
    
    // 通过 id 删除 HostReply
    public Integer delHostReplyById(Integer id) {
        String sql = "delete from host_reply where id=?";
        return hostReplyDAO.update(sql, id);
    }
    
    
}
