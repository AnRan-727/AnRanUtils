package cn.vxpay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResultInfo {

    private Integer code;
    private String msg;
    private Object data;



    public static ResultInfo ok(Object data){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(200);
        resultInfo.setMsg(null);
        resultInfo.setData(data);
        return resultInfo;
    }

    public static ResultInfo error(String msg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(400);
        resultInfo.setMsg(msg);
        resultInfo.setData(null);
        return resultInfo;
    }

}
