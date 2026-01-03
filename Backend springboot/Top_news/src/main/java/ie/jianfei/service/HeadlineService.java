package ie.jianfei.service;

import ie.jianfei.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import ie.jianfei.pojo.PortalVo;
import ie.jianfei.utils.Result;

/**
* @author bernstain
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2025-12-31 14:03:55
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline);

    Result findHeadlineByHid(Integer hid);

    Result updateHeadline(Headline headline);
}
