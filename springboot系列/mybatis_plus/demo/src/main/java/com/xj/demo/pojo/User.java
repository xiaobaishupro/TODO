package com.xj.demo.pojo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User extends Model<User> {

    @TableId(value="user_id", type= IdType.AUTO)
    private Integer userId;

    @TableField(value="user_name")
    private String userName;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}
