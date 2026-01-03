package ie.jianfei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ie.jianfei.pojo.Type;
import ie.jianfei.service.TypeService;
import ie.jianfei.mapper.TypeMapper;
import org.springframework.stereotype.Service;

/**
* @author bernstain
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2025-12-31 14:04:31
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




