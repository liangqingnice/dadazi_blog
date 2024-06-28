package online.dadazi.dadaziblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import online.dadazi.dadaziblog.config.pojo.BasePojo;

/**
 * 系统文件类
 * @author lqk
 */
@TableName(value ="sys_file")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFile extends BasePojo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件唯一名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 上传时名称
     */
    private String originalName;

    /**
     * 创建人ID
     */
    private Long createUser;

    /**
     * 校验码
     */
    private String checksum;
    /**
     * 系统文件路径
     */
    private String sysFilePath;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;
}