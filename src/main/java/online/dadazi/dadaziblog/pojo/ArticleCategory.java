package online.dadazi.dadaziblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import online.dadazi.dadaziblog.config.pojo.BasePojo;

/**
 * 文章分类
 * @author lqk
 */
@TableName(value ="article_category")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ArticleCategory extends BasePojo implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    /**
     * 分类别名
     */
    @NotBlank(message = "分类别名不能为空")
    private String categoryAlias;

    /**
     * 创建人ID
     */
    private Long createUser;


    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
}