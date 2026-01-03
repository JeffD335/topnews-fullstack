package ie.jianfei.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @TableName news_type
 */

@Data
public class Type {
    private Integer tid;

    private String tname;
    @Version
    private Integer version;

    private Integer isDeleted;
}