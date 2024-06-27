package online.dadazi.dadaziblog.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文件路径返回
 *
 * @author lqk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilePathVO {
    /**
     * 路径
     */
    private String path;
    /**
     * 全路径
     */
    private String fullPath;
    /**
     * 文件名称
     */
    private  String fileName;
}
