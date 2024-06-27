package online.dadazi.dadaziblog.controller.pc;

import jakarta.annotation.Resource;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.service.SysFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * @author lqk
 */
@RestController
public class CommonController {
    @Resource
    private SysFileService sysFileService;
    @PostMapping("/upload")
    public JsonResult<?> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("path") String path) {
        return sysFileService.uploadFile(file,path);
    }
}
