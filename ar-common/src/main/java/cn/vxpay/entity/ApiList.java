package cn.vxpay.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Api列表展示信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiList {

    /** 唯一ID */
    private Integer id;
    /** 说明 */
    private String name;
    /** 请求地址 */
    private String path;
    /** 前端路径 */
    private String view;

}
