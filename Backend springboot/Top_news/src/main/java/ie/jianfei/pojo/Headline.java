package ie.jianfei.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * @TableName news_headline
 */

@Data
public class Headline {
    private Integer hid;

    private String title;

    private String article;

    private Integer type;

    private Integer publisher;

    private Integer pageViews;

    private Date createTime;

    private Date updateTime;
    @Version
    private Integer version;

    private Integer isDeleted;
}