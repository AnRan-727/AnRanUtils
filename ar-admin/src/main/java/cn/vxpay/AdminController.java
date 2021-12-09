package cn.vxpay;

import cn.vxpay.entity.ApiList;
import cn.vxpay.entity.ResultInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class AdminController {

    /**
     * 获取接口列表
     * @param page 当前页
     * @param pageSize 每页数量
     * @return
     */
    @RequestMapping(value = "/getApiList", method = RequestMethod.GET)
    public ResultInfo getList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize) {
        List<ApiList> list = this.getApiList();
        //创建Page类
        Page page1 = new Page(page, pageSize);
        //为Page类中的total属性赋值
        int total = list.size();
        page1.setTotal(total);
        //计算当前需要显示的数据下标起始值
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        //从链表中截取需要显示的子链表，并加入到Page
        page1.addAll(list.subList(startIndex, endIndex));
        //以Page创建PageInfo
        PageInfo pageInfo = new PageInfo<>(page1);
        return ResultInfo.ok(pageInfo);
    }

    private List<ApiList> getApiList() {
        List<ApiList> list = new ArrayList<>();
        list.add(new ApiList(1, "上传二维码识别", "/qrcode/upload", "/apiInfo"));
        list.add(new ApiList(1, "王者荣耀英雄", "/hero/getHeroList", "/heroList"));
        for (int i = 3; i < 20; i++) {
            list.add(new ApiList(i, "网络二维码识别", "/qrcode/url", "/apiInfo"));
        }
        return list;
    }

}
