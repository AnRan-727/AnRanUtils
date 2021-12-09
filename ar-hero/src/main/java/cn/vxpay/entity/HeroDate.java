package cn.vxpay.entity;

import lombok.Data;

/**
 * 英雄基本信息
 */
@Data
public class HeroDate {

    /** 英雄id */
    private String heroid;

    /** 英雄名称 */
    private String title;

    /** 详细网页 */
    private String infourl;

    /** 支付类型 */
    private String camptype;

    /** App的英雄id */
    private String appointHeroid;

    /** 删除的英雄id */
    private String removeHeroid;

    /** 类型 */
    private String occupation;

    /** 排序 */
    private String num;

    /** 英雄图片 */
    private String heroimg;

    /** 英雄头像 */
    private String faceimg;

    /** 拼音 */
    private String pinyin;
}
