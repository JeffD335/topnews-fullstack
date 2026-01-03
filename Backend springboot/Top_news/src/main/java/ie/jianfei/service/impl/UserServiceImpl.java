package ie.jianfei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ie.jianfei.pojo.User;
import ie.jianfei.service.UserService;
import ie.jianfei.mapper.UserMapper;
import ie.jianfei.utils.JwtHelper;
import ie.jianfei.utils.MD5Util;
import ie.jianfei.utils.Result;
import ie.jianfei.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author bernstain
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2025-12-31 14:04:37
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result login(User user) {
        // query by userName
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(queryWrapper);
        // validate user
        if(loginUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        // validate password
        // compare encrypted password between loginUser in database and user passed from frontend
        if(!StringUtils.isEmpty(loginUser.getUserPwd())
                && loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))){
            // password correct, generate token by userId
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map data = new HashMap();
            data.put("token",token);

            return Result.ok(data);
        }
        // password incorrect
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result getUserInfo(String token) {
        if(jwtHelper.isExpiration(token)){
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
       Integer userId = jwtHelper.getUserId(token).intValue();
       User user = userMapper.selectById(userId);
       if(user != null){
           user.setUserPwd("");
           HashMap<String, User> data = new HashMap<>();
           data.put("loginUser", user);
           Result.ok(data);
       }
        return Result.build(null, ResultCodeEnum.NOTLOGIN);
    }

    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User registerUser = userMapper.selectOne(queryWrapper);
        if (registerUser == null){
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User registerUser = userMapper.selectOne(queryWrapper);
        if (registerUser != null){
           return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
        int rows = userMapper.insert(user);
        if(rows > 0){
            return Result.ok(null);
        }else {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
    }


}




