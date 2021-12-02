package cn.vxpay.controller;

import cn.vxpay.entity.ApiList;
import cn.vxpay.entity.ResultInfo;
import cn.vxpay.utils.QrCodeUtil;
import com.google.zxing.NotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.Oneway;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 二维码模块
 */
@RequestMapping(value = "/qrcode")
@RestController
public class QrCodeController {

    /**
     * 获取接口列表
     * @return
     */
    @RequestMapping(value = "/getApiList", method = RequestMethod.GET)
    public ResultInfo getList(){
        List<ApiList> list = new ArrayList<>();
        list.add(new ApiList(1,"上传二维码识别","/qrcode/upload","/apiInfo"));
        list.add(new ApiList(2,"网络二维码识别","/qrcode/url","/apiInfo"));
        list.add(new ApiList(3,"网络二维码识别","/qrcode/url","/apiInfo"));
        list.add(new ApiList(4,"网络二维码识别","/qrcode/url","/apiInfo"));
        list.add(new ApiList(5,"网络二维码识别","/qrcode/url","/apiInfo"));
        list.add(new ApiList(6,"网络二维码识别","/qrcode/url","/apiInfo"));
        return ResultInfo.ok(list);
    }

    /**
     * 获取单个接口信息
     * @return
     */
    @RequestMapping(value = "/getApiDetail", method = RequestMethod.GET)
    public ResultInfo getApiDetail(Integer id){
        Map<String,Object> map = new HashMap<>();
        List<Object> paramList = new ArrayList<>();
        if(id == 1){
            map.put("id",1);
            map.put("name","上传二维码识别");
            map.put("path","/qrcode/upload");
            map.put("view","/apiInfo");
            map.put("format","jpg,png,jpeg");
            map.put("request","POST");

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("param","file");
            paramMap.put("check","true");
            paramMap.put("type","file");
            paramMap.put("name","文件");

            paramList.add(paramMap);

            map.put("param",paramList);

            Map<String,Object> resultMap1 = new HashMap<>();
            resultMap1.put("col","code");
            resultMap1.put("type","int");
            resultMap1.put("dest","返回请求结果状态。200：正常；400：错误。");
            Map<String,Object> resultMap2 = new HashMap<>();
            resultMap2.put("col","msg");
            resultMap2.put("type","string");
            resultMap2.put("dest","错误提示，正常时为Null");
            Map<String,Object> resultMap3 = new HashMap<>();
            resultMap3.put("col","data");
            resultMap3.put("type","object");
            resultMap3.put("dest","返回数据，错误时为Null");

            List<Object> resultList = new ArrayList<>();
            resultList.add(resultMap1);
            resultList.add(resultMap2);
            resultList.add(resultMap3);

            map.put("result",resultList);



        }else{
            map.put("id",2);
            map.put("name","网络二维码识别");
            map.put("path","/qrcode/url");
            map.put("view","/apiInfo");
            map.put("format","jpg,png,jpeg");
            map.put("request","POST");

            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("param","url");
            paramMap.put("check","true");
            paramMap.put("type","string");
            paramMap.put("name","图片网络地址");

            paramList.add(paramMap);

            map.put("param",paramList);

            Map<String,Object> resultMap1 = new HashMap<>();
            resultMap1.put("col","code");
            resultMap1.put("type","int");
            resultMap1.put("dest","返回请求结果状态。200：正常；400：错误。");
            Map<String,Object> resultMap2 = new HashMap<>();
            resultMap2.put("col","msg");
            resultMap2.put("type","string");
            resultMap2.put("dest","错误提示，正常时为Null");
            Map<String,Object> resultMap3 = new HashMap<>();
            resultMap3.put("col","data");
            resultMap3.put("type","object");
            resultMap3.put("dest","返回数据，错误时为Null");

            List<Object> resultList = new ArrayList<>();
            resultList.add(resultMap1);
            resultList.add(resultMap2);
            resultList.add(resultMap3);

            map.put("result",resultList);

        }
        return ResultInfo.ok(map);
    }



    /**
     * 上传二维码 并识别
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultInfo upload(MultipartFile file){

        File f = null;
        try {
            String originalFilename = file.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            f=File.createTempFile(filename[0], filename[1]);
            file.transferTo(f);
            f.deleteOnExit();

            String data = QrCodeUtil.decodeQrCode(f);
            f.delete();

            return ResultInfo.ok(data);

        } catch (Exception e) {
            return ResultInfo.error("上传识别失败："+ e.getMessage());
        }
    }

    /**
     * 网络路径识别二维码
     * @param url
     * @return
     */
    @RequestMapping(value = "/url", method = RequestMethod.POST)
    public ResultInfo url(String url){
        try {
            URL uRL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uRL.openConnection();
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
