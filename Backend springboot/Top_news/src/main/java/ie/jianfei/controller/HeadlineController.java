package ie.jianfei.controller;

import ie.jianfei.pojo.Headline;
import ie.jianfei.service.HeadlineService;
import ie.jianfei.utils.JwtHelper;
import ie.jianfei.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("headline")
public class HeadlineController {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private HeadlineService headlineService;
    /**
     * Process:
     *   1. token parse to userId [no need to validate, interceptor will do it]
     *   2. encapsulate headline data
     *   3. insert into database
     */
    @PostMapping("publish")
    public Result publish(@RequestHeader String token, @RequestBody Headline headline){
            int userId = jwtHelper.getUserId(token).intValue();
            headline.setPublisher(userId);
            Result result = headlineService.publish(headline);
            return result;
    }
    @PostMapping("findHeadlineByHid")
    public Result findHeadlineByHid(Integer hid){
        Result result = headlineService.findHeadlineByHid(hid);
        return  result;
    }

    @PostMapping("update")
    public Result update(@RequestBody Headline headline){
        Result result = headlineService.updateHeadline(headline);
        return  result;
    }

    @PostMapping("removeByHid")
    public Result removeByHid(Integer hid){
       headlineService.removeById(hid);
       return Result.ok(null);
    }
}
