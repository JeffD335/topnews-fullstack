package ie.jianfei.controller;

import ie.jianfei.pojo.User;
import ie.jianfei.service.UserService;
import ie.jianfei.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController     //Marks this class as a RESTful controller。Combines @Controller and @ResponseBody.
 //Indicates that all methods return data (e.g. JSON)
@RequestMapping("user")     //Maps HTTP requests to this controller with a base URL path.
@CrossOrigin        //Enables Cross-Origin Resource Sharing (CORS).
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * Login requirements
     * url: /user/login
     * method: post
     * requesBody(JSON):
     *    {
     *     "username":"zhangsan",
     *     "userPwd":"123456"
     *    }
     * return:
     *   {
     *    "code":"200",         // Success
     *    "message":"success"
     *    "data":{
     *         "token":"... ..." // token of user's id
     *     }
     *  }
     *
     *
     *  Rough process:
     *  1.Perform a database query on the account and return the user object
     *  2.Compare user password (md5 encrypted)
     *  3.Upon success, generate a token based on the userId -> map key=token value=token value - result encapsulation
     *  4.Failed. The account or password is incorrect. Simply encapsulate the corresponding enumeration error
     */
    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        System.out.println("result= "+ result);
        return result;
    }

    /**
     * url: user/getUserInfo
     * method: get
     * requestHead: token = token content
     * return:
     *    {
     *     "code": 200,
     *     "message": "success",
     *     "data": {
     *         "loginUser": {
     *             "uid": 1,
     *             "username": "tom123",
     *             "userPwd": "",
     *             "nickName": "Tom"
     *         }
     *      }
     *   }
     *
     * Process:
     *    1.receive token,parse token to userId
     *    2.query user info by userId
     *    3.set userPwd enmty,and encapsulate user info into result
     *    4.return code 405 if fail
     */
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return  result;
    }
    /**
     * url：user/checkUserName
     * method：POST
     * param：param
     * username="tom"
     * respond :
     * {
     *    "code":"200",
     *    "message":"success"
     *    "data":{}
     * }
     *
     * Process:
     *   1. get username
     *   2. query by username
     *   3. encapsulate result
     */
    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }
    /**
     * url：user/regist
     * method：POST
     * requestBody(JSON)：
     * {
     *     "username":"tom123",
     *     "userPwd":"123456",
     *     "nickName":"Tom"
     * }
     * respond：
     * {
     *    "code":"200",
     *    "message":"success"
     *    "data":{}
     * }
     *
     * process:
     *   1. encrypt password
     *   2. insert into database
     *   3. return 200 if success, otherwise 505
     */
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return  result;
    }


}
