package cn.vxpay.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.vxpay.entity.HeroDate;
import cn.vxpay.entity.HeroGrade;
import cn.vxpay.entity.ResultInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 王者荣耀模块
 */
@RestController
@RequestMapping(value = "/hero")
public class HeroController {

    List<HeroDate> heroDates = null;

    /**
     * 获取所有的英雄
     * @return
     */
    @RequestMapping(value = "/getHeroList", method = RequestMethod.GET)
    public ResultInfo getHeroList() {
        if(heroDates == null){
            this.getHeroDateList();
        }
        return ResultInfo.ok(heroDates);
    }

    /**
     * 按名称搜索英雄
     * @return
     */
    @RequestMapping(value = "/getHeroByName", method = RequestMethod.GET)
    public ResultInfo getHeroByName(String name) {
        if(heroDates == null){
            this.getHeroDateList();
        }
        if(name.equals("")){
            return ResultInfo.ok(heroDates);
        }
        Stream<HeroDate> heroDateStream = heroDates.stream().filter(heroDate -> {
            if(heroDate.getTitle() != null || heroDate.getTitle() != ""){
                return heroDate.getTitle().equals(name);
            }else{
                return true;
            }
        });
        return ResultInfo.ok(heroDateStream);
    }
    /**
     * 按类型搜索英雄
     * @return
     */
    @RequestMapping(value = "/getHeroByType", method = RequestMethod.GET)
    public ResultInfo getHeroByType(String type) {
        if(heroDates == null){
            this.getHeroDateList();
        }
        if(type.equals("")){
            return ResultInfo.ok(heroDates);
        }
        Stream<HeroDate> heroDateStream = heroDates.stream().filter(heroDate -> {
            if(heroDate.getOccupation() != null || heroDate.getOccupation() != "" ){
                return heroDate.getOccupation().equals(type);
            }else{
                return true;
            }
        });
        return ResultInfo.ok(heroDateStream);
    }

    /**
     * 获取英雄战力
     * @param name 英雄名称
     * @return
     */
    @RequestMapping(value = "/getHeroGrade", method = RequestMethod.GET)
    public ResultInfo getHeroGrade(String name){
        List<Grade> qq = this.getHeroGradeData(name, "qq");
        List<Grade> wx = this.getHeroGradeData(name, "wx");
        List<Grade> ios_qq = this.getHeroGradeData(name, "ios_qq");
        List<Grade> ios_wx = this.getHeroGradeData(name, "ios_wx");

        Map<String , Object> map = new HashMap<>();
        map.put("qq",qq);
        map.put("wx",wx);
        map.put("ios_qq",ios_qq);
        map.put("ios_wx",ios_wx);
        return ResultInfo.ok(map);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Grade {

        /** 级别 */
        private String grade;
        /** 地区 */
        private String region;
        /** 分数 */
        private Integer points;

    }


    public static void main(String[] args) {
        String name = "妲己";
        String type = "qq";
        try {
            String url = "https://www.jk.cxkf.cc/api_select.php";
            Map<String,Object> form = new HashMap<>();
            form.put("hero",name);
            form.put("type",type);
            String s = HttpUtil.get(url, form);
            JSONObject jsonObject = JSON.parseObject(s);
            HeroGrade heroGrade = JSON.parseObject(jsonObject.get("data").toString(), HeroGrade.class);
            System.out.println(heroGrade);
        }catch (Exception e){
            e.getMessage();
        }
    }

    private List<Grade> getHeroGradeData(String name, String type){
        try {
            //String url = "https://www.jk.cxkf.cc/api_select.php";
            String url = "https://www.somekey.cn/mini/hero/getHeroInfo.php";
            Map<String,Object> form = new HashMap<>();
            form.put("hero",name);
            form.put("type",type);
            String s = HttpUtil.get(url, form);
            JSONObject jsonObject = JSON.parseObject(s);
            HeroGrade heroGrade = JSON.parseObject(jsonObject.get("data").toString(), HeroGrade.class);
            List<Grade> list = new ArrayList<>();
            list.add(new Grade("区级", heroGrade.getArea(), Integer.parseInt(heroGrade.getAreaPower())));
            list.add(new Grade("市级", heroGrade.getCity(), Integer.parseInt(heroGrade.getCityPower())));
            list.add(new Grade("省级", heroGrade.getProvince(), Integer.parseInt(heroGrade.getProvincePower())));

            return list;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }



    private List<HeroDate> getHeroDateList() {
        do {
            try {
                String url = "https://pvp.qq.com/webplat/info/news_version3/15592/18024/23901/24397/24398/m17330/list_1.shtml?callback=createHeroList";
                String heroJson = HttpRequest.post(url)
                        .timeout(20000)//超时，毫秒
                        .execute().body();
                heroJson = heroJson.substring("createHeroList(".length(), heroJson.length() - 1);
                String s = JSON.parseObject(heroJson).get("data").toString().replace("[\"\",", "[");
                heroDates = JSON.parseArray(s, HeroDate.class);
                heroDates.sort(Comparator.comparing(HeroDate::getHeroid));
                for (HeroDate heroDate : heroDates) {
                    heroDate.setTitle(heroDate.getTitle().split("-")[2]);
                    heroDate.setInfourl(heroDate.getInfourl().replaceAll("https://", "//").replaceAll("//", "https://"));
                    heroDate.setHeroimg(heroDate.getHeroimg().replaceAll("https://", "//").replaceAll("//", "https://"));
                    heroDate.setFaceimg(heroDate.getFaceimg().replaceAll("https://", "//").replaceAll("//", "https://"));
                }
                return heroDates;
            }catch (Exception e){
                continue;
            }
        }while (true);
    }

}
