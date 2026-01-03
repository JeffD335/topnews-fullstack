package ie.jianfei.service;

import ie.jianfei.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import ie.jianfei.utils.Result;

/**
* @author bernstain
* @description 针对表【news_user】的数据库操作Service
* @createDate 2025-12-31 14:04:37
*/
public interface UserService extends IService<User> {

     Result login(User user);

     Result getUserInfo(String token);

     Result checkUserName(String username);

     Result regist(User user);


}
