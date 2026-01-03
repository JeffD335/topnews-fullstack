package ie.jianfei.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ie.jianfei.pojo.Headline;
import ie.jianfei.pojo.PortalVo;
import ie.jianfei.service.HeadlineService;
import ie.jianfei.mapper.HeadlineMapper;
import ie.jianfei.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* @author bernstain
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2025-12-31 14:03:55
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{
    @Autowired
    private HeadlineMapper headlineMapper;
    @Override
    public Result findNewsPage(PortalVo portalVo) {
        //1.sql concat, and check if it's null
        LambdaQueryWrapper<Headline> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(portalVo.getKeyWords()),Headline::getTitle,portalVo.getKeyWords())
                .eq(portalVo.getType()!= null,Headline::getType,portalVo.getType());

        //2.paging
        IPage<Headline> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());

        // 3. Execute paginated query
        // The result contains a computed field "pastHours"
        // (hours elapsed since publication time).
        // A custom mapper method is used to return extended query results.
        headlineMapper.selectPageMap(page, portalVo);

        //4.encapsulate result

        Map<String,Object> pageInfo =new HashMap<>();
        pageInfo.put("pageData",page.getRecords());
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());

        Map<String,Object> pageInfoMap=new HashMap<>();
        pageInfoMap.put("pageInfo",pageInfo);
        // respond JSON
        return Result.ok(pageInfoMap);
    }

    /**
     * query detail
     * "headline":{
     * "hid":"1",
     * "title":"Musk ... ...",
     * "article":"... ..."
     * "type":"1",
     * "typeName":"tech",
     * "pageViews":"40",
     * "pastHours":"3" ,
     * "publisher":"1" ,
     * "author":"Tom"
     * }
     * Note: This is a multi-table query, and the view count needs to be updated by +1
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        //1.mulit-table query
        Map<String, Object> headLineDetail = headlineMapper.selectDetailMap(hid);
        //2. update pageViews and version
        LambdaUpdateWrapper<Headline> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Headline::getHid, hid)
                .eq(Headline::getVersion, (Integer) headLineDetail.get("version"))
                .setSql("page_views = page_views + 1");

        headlineMapper.update(null, updateWrapper);

        HashMap<String, Object> data = new HashMap<>();
        return Result.ok(headLineDetail);
    }

    @Override
    public Result publish(Headline headline) {
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());
        headline.setPageViews(0);
        headlineMapper.insert(headline);
        return  Result.ok(null);
    }

    /**
     * query headline detail by id
     * @param hid
     * @return
     */
    @Override
    public Result findHeadlineByHid(Integer hid) {
        Headline headline = headlineMapper.selectById(hid);
        HashMap<String, Object> data = new HashMap<>();
        data.put("headline", headline);
        return Result.ok(data);
    }

    @Override
    public Result updateHeadline(Headline headline) {
       Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
       headline.setVersion(version);
       headline.setUpdateTime(new Date());
       headlineMapper.updateById(headline);
       return Result.ok(null);
    }


}




