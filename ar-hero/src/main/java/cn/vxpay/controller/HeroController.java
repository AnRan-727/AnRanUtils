package cn.vxpay.controller;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.vxpay.entity.HeroDate;
import cn.vxpay.entity.ResultInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 王者荣耀模块
 */
@RestController
@RequestMapping(value = "/hero")
public class HeroController {

    List<HeroDate> heroDates = null;

    @RequestMapping(value = "/getHeroList", method = RequestMethod.GET)
    public ResultInfo getHeroList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize) {
        if(heroDates == null){
            this.getHeroDateList();
        }
        //创建Page类
        Page page1 = new Page(page, pageSize);
        //为Page类中的total属性赋值
        int total = heroDates.size();
        page1.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        //从链表中截取需要显示的子链表，并加入到Page
        page1.addAll(heroDates.subList(startIndex, endIndex));
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page1);

        return ResultInfo.ok(pageInfo);
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
