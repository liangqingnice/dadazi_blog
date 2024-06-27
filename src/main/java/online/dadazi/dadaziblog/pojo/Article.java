package online.dadazi.dadaziblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import online.dadazi.dadaziblog.config.pojo.BasePojo;

/**
 * 文章表
 * @author lqk
 */
@TableName(value ="article")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Article extends BasePojo implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    @NotBlank(message = "请填写标题")
    private String title;

    /**
     * 文章内容
     */
    @NotBlank(message = "请填写文章内容")
    private String content;

    /**
     * 文章封面
     */
    @NotBlank(message = "请上传图片")
    private String coverImg;

    /**
     * 文章状态: 只能是[已发布] 或者 [草稿]
     */
    private String state;

    /**
     * 文章分类ID
     */
    @NotNull(message = "请选择分类")
    private Long categoryId;

    /**
     * 创建人ID
     */
    private Long createUser;

    /**
     * 分类名称
     */
    @TableField(exist = false)
    private String categoryName;
    /**
     * 创建者名称
     */
    @TableField(exist = false)
    private String createUserName;
    /**
     * 分类别名
     */
    @TableField(exist = false)
    private String categoryAlias;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
}