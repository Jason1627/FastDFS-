package com.fjx.service;/*
 @author 天赋吉运-Jason
 @DESCRIPTION 
 @create 2019-12-24
*/
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class UploadService {
    public static  String FILE_FORMAT = "WEBP、BMP、PCX、TIF、GIF、JPEG、TGA、EXIF、FPX、" +
            "SVG、PSD、CDR、PCD、DXF、UFO、EPS、AI、PNG、HDRI、RAW、WMF、FLIC、EMF、ICO、JPG、JPEG、PNG、GIF";
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private FastFileStorageClient storageClient;

    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        // 校验文件的类型,file.getContentType()表示获取文件类型
        String contentType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1).toUpperCase();
        if (!FILE_FORMAT.contains(contentType)) {
            // 文件类型不合法，直接返回null
            LOGGER.info("文件类型不合法：{}", originalFilename);
            return null;
        }
        try {
            // 校验文件的内容，ImageIO是一个工具类
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法：{}", originalFilename);
                return null;
            }
            // 保存到服务器
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
            // 生成url地址，进行回显
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            LOGGER.info("服务器内部错误：{}", originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
