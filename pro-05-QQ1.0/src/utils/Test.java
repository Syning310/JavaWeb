package utils;


import domain.UserBasic;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import service.UserBasicService;

import java.sql.Connection;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {

//        Connection cons = JdbcUtilsByDruid.getConnection();
//
//        QueryRunner qr = new QueryRunner();
//
//        UserBasic user = qr.query(cons, "select * from user_basic", new BeanHandler<>(UserBasic.class));
//
//        System.out.println(user.getId());
//        System.out.println(user.getLoginid());
//        System.out.println(user.getNickName());
//        System.out.println(user.getPwd());

        UserBasicService service = new UserBasicService();
//        
//        UserBasic user = service.getUserBasic("1031887546", "syning0310");
//
//        System.out.println(user.getId());
//        System.out.println(user.getLoginid());
//        System.out.println(user.getNickName());
//        System.out.println(user.getPwd());

//        UserBasic u = new UserBasic();
//        u.setLoginid("1031887546");
//        List<UserBasic> lst = service.getUserBasicFriendList(u);
//        
//      
//        for (UserBasic user : lst) {
//            System.out.println(user.getId());
//            System.out.println(user.getLoginid());
//            System.out.println(user.getNickName());
//            System.out.println(user.getPwd());
//            System.out.println(user.getUserDetail());   // 没有设置用户信息，所以为空
//            System.out.println("------------------------------------");
//        }

        
        UserBasic user = service.getUserBasic("尚亦宁", "123456");
        System.out.println(user);
        
        
        


    }
}
