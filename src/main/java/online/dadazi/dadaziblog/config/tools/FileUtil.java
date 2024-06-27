package online.dadazi.dadaziblog.config.tools;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import online.dadazi.dadaziblog.config.pojo.ProjectConfig;
import online.dadazi.dadaziblog.pojo.SysFile;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * 文件工具类
 *
 * @author lqk
 */
@Slf4j
public class FileUtil {
    final static String dir = ProjectConfig.uploadDir;
    public final static String uploadDir = "space";
    public final static String  slash = "/";

    /**
     * 检查目录是否存在，如果不存在则创建目录。
     *
     * @param directoryPath 目录的路径
     * @throws IOException 如果创建目录时发生错误
     */
    public static void createDirectoryIfNotExists(String directoryPath) {
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                log.error("创建目录失败", e);
            }
        }
    }

    /**
     * 获取上传文件信息
     */
    public static SysFile getSysFile(MultipartFile file, String path) {
        SysFile sysFile = new SysFile();
        try {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String dateName = DateUtil.format(new Date(), "yyyyMMddhhmmssSSS");
            FileUtil.createDirectoryIfNotExists(ProjectConfig.uploadDir + File.separatorChar + path);
            String s = ProjectConfig.uploadDir + File.separatorChar + path + File.separatorChar + dateName + suffix;
            long size = file.getSize();
            String checksums = DigestUtils.md5DigestAsHex(file.getInputStream());
            sysFile.setChecksum(checksums);
            sysFile.setCreateUser(ServletUtil.getLoginId());
            sysFile.setFileName(dateName);
            sysFile.setFilePath(uploadDir + slash + path +slash + dateName + suffix);
            sysFile.setFileType(suffix);
            sysFile.setFileSize(size);
            sysFile.setSysFilePath(s);
            sysFile.setOriginalName(originalFilename);
            file.transferTo(new File(s));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取文件校验码失败");
            log.error("错误信息：", e.getMessage());
        }
        return sysFile;
    }

    /**
     * 生成头像
     * @param letter 文字
     * @param size 边长
     * @param outputPath 输出路径
     */
    public static void genAvatar(String letter, int size, String outputPath) {
        // 创建一个白色背景的图片
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        //设置背景色
        g2d.setColor(new Color(64, 158, 255) );
        g2d.fillRect(0, 0, size, size);
        g2d.setFont(new Font("Microsoft YaHei", Font.BOLD, size / 2));
        // 设置字体和颜色
        g2d.setColor(Color.WHITE);

        // 计算字母在图片中的位置，使其居中
        FontMetrics metrics = g2d.getFontMetrics();
        int x = (size - metrics.stringWidth(letter)) / 2;
        int y = ((size - metrics.getHeight()) / 2) + metrics.getAscent();
        // 在图片上绘制字母
        g2d.drawString(letter, x, y);
        // 释放图形上下文使用的资源
        g2d.dispose();
        // 输出图片到指定文件路径
        File outputFile = new File(outputPath);
        try {
            ImageIO.write(image, "PNG", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
