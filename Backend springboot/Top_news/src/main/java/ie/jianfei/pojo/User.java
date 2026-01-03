package ie.jianfei.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName news_user
 */

@Data
public class User implements Serializable {
    private Integer uid;

    private String username;

    private String userPwd;

    private String nickName;
    @Version
    private Integer version;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}