package ie.jianfei.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ie.jianfei.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ie.jianfei.pojo.PortalVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Objects;

/**
* @author bernstain
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2025-12-31 14:03:55
* @Entity ie.jianfei.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    IPage<Map> selectPageMap(IPage<Headline> page,
                                              @Param("portalVo") PortalVo portalVo);

    Map<String, Object> selectDetailMap(Integer hid);
}




