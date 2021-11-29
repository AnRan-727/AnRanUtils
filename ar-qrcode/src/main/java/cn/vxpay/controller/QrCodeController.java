package cn.vxpay.controller;

import cn.vxpay.entity.ResultInfo;
import cn.vxpay.utils.QrCodeUtil;
import com.google.zxing.NotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 二维码模块
 */
@RequestMapping(value = "/qrcode")
@RestController
public class QrCodeController {

    /**
     * 上传二维码 并识别
     * @param uploadfile
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultInfo upload(@RequestParam("file") MultipartFile uploadfile){

        File file = null;
        try {
            String originalFilename = uploadfile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file=File.createTempFile(filename[0], filename[1]);
            uploadfile.transferTo(file);
            file.deleteOnExit();

            String data = QrCodeUtil.decodeQrCode(file);
            file.delete();

            return ResultInfo.ok(data);

        } catch (Exception e) {
            return ResultInfo.error("上传识别失败："+ e.getMessage());
        }
    }

    /**
     * 网络路径识别二维码
     * @param urlStr
     * @return
     */
    @RequestMapping(value = "/url", method = RequestMethod.POST)
    public ResultInfo url(@RequestParam("url") String urlStr){
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inputStream = conn.getInputStream();
            String data = QrCodeUtil.decodeQrCode(inputStream);
            return ResultInfo.ok(data);
        } catch (NotFoundException e) {
            return ResultInfo.error("获取图片失败："+ e.getMessage());
        } catch (IOException e) {
            return ResultInfo.error("上传识别失败："+ e.getMessage());
        }
    }

}
