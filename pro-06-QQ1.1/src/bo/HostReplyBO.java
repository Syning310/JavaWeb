package bo;

import domain.HostReply;
import service.HostReplyService;

import java.util.List;

public class HostReplyBO {
    
    HostReplyService hostReplyService = null;
    
    // 通过 reply 的 id 取得 对应的主人回复 
    public HostReply getHostReplyListByReplyId(Integer id) {
        return hostReplyService.getHostReplyListByReplyId(id);
    }
    
    
    public Integer delHostReplyById(Integer id) {
        return hostReplyService.delHostReplyById(id);
    }
    
    
}
