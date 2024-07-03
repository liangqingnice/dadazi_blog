package online.dadazi.dadaziblog.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.config.tools.FileUtil;
import online.dadazi.dadaziblog.config.tools.ServletUtil;
import online.dadazi.dadaziblog.pojo.SysFile;
import online.dadazi.dadaziblog.pojo.vo.FilePathVO;
import online.dadazi.dadaziblog.service.SysFileService;
import online.dadazi.dadaziblog.mapper.SysFileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lqk
 */
@Slf4j
@Service
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    /**
     * 上传文件
     * @param file 文件
     * @param path 目录
     * @return {@link JsonResult }<{@link FilePathVO }>
     */
    @Override
    public JsonResult<?> uploadFile(MultipartFile file, String path) {
        SysFile sysFile = FileUtil.getSysFile(file, path);
        FilePathVO filePathVO = new FilePathVO();
        filePathVO.setPath(sysFile.getFilePath());
        filePathVO.setFileName(sysFile.getOriginalName());
        filePathVO.setFullPath(ServletUtil.getDoMainName() +FileUtil.SLASH + sysFile.getFilePath());
        return baseMapper.insert(sysFile) > 0 ? JsonResult.success("上传成功",filePathVO) : JsonResult.error("上传失败");
    }
}




