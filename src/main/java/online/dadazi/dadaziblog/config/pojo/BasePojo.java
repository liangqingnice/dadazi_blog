package online.dadazi.dadaziblog.config.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.time.LocalDateTime;
/**
 * @author lqk
 * 基础父类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class BasePojo {
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更细时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 是否删除
     */
    @TableLogic
    @JsonIgnore
    private String delMark;
    /**
     * 页数
     */
    @TableField(exist=false)
    @JsonIgnore
    private Integer page;
    /**
     * 条数
     */
    @TableField(exist=false)
    @JsonIgnore
    private Integer size;
}
