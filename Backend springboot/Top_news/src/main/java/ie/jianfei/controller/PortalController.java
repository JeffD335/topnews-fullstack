package ie.jianfei.controller;

import ie.jianfei.pojo.PortalVo;
import ie.jianfei.pojo.Type;
import ie.jianfei.service.HeadlineService;
import ie.jianfei.service.TypeService;
import ie.jianfei.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private HeadlineService headlineService;

    /**
     * query all type of news
     * @return
     */
    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        List<Type> list = typeService.list();
        return  Result.ok(list);
    }
    /*
     * The client sends query keywords, news categories, page number, and page size to the server
     * The server searches for paging information based on the conditions
     *  and returns information including page number, page size, total pages, total records, current page data, etc.
     *  sorted in descending order by time and descending order by view count
     */
    @PostMapping("findNewsPage")
    public Result findNewPage(@RequestBody PortalVo portalVo){
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}
