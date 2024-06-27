package online.dadazi.dadaziblog.service;

import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.pojo.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
* @author lqk
*/
public interface SysFileService extends IService<SysFile> {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 目录
     * @return {@link JsonResult }<{@link ? }>
     */
    JsonResult<?> uploadFile(MultipartFile file, String path);
}
