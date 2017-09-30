package com.songm.grabrepair.utils;

/**
 * Created by SongM on 2017/9/22.
 * URL列表
 */

public class UrlUtils {

    /**
     * HOST
     */
    public static final String HOST = "http://118.89.101.23:8080/";
    /**
     * 维修员登陆
     */
    public static final String repairerLogin = HOST + "repairer/login";
    /**
     * 通过维修员ID查询维修员信息
     * @param repairerId 维修员ID
     * @return
     */
    public static String repairerFindByRepairerId(String repairerId) {
        return HOST + "repairer/findFullInfo?idOrPhone=" + repairerId;
    }
    /**
     * 用户头像
     */
    public static String userIcon(String userId, String profession) {
        return HOST + "img/findIconUrl?userId=" + userId + "&profession=" + profession;
    }
    /**
     * 更新用户头像
     */
    public static String updateUserIcon = HOST + "img/updateUserIcon";
    /**
     * 抢修端最新版本号
     */
    public static String appBaoxiu = HOST + "webset/getQiangxiuInfo";
    /**
     * 维修员重置密码
     */
    public static final String repairerUpdatePwd = HOST + "repairer/updatePwd";
    /**
     * 我的维修订单
     */
    public static String orderByRepairer(int orderState, String repairerId) {
        return HOST + "order/getRepairOrders?orderState=" + orderState + "&repairerId=" + repairerId;
    }
    /**
     * 通过订单ID查询订单信息
     * @param orderId 订单ID
     * @return
     */
    public static String orderFindByOrderId(int orderId) {
        return HOST + "order/findById?orderId=" + orderId;
    }
    /**
     * 通过订单ID查询图片信息
     * @param orderId 订单ID
     * @return
     */
    public static String imgFindByOrderId(int orderId) {
        return HOST + "img/findByOrderId?orderId=" + orderId;
    }
    /**
     * 通过订单ID查询评价
     * @param orderId 订单ID
     * @return
     */
    public static String evalFindByOrderId(int orderId) {
        return HOST + "eval/getEvalByOrderId?orderId=" + orderId;
    }
    /**
     * 通过订单创建人ID查询创建人信息
     * @param isStu 是否为学生
     * @param authorId 创建人ID
     * @return
     */
    public static String authorFindByAuthorId(boolean isStu, String authorId) {
        String str = HOST + "stu/findFullInfo?idOrPhone=" + authorId;
        if(!isStu) {
            str = HOST + "hmr/findFullInfo?idOrPhone=" + authorId;
        }
        return str;
    }
    /**
     * 添加评价
     */
    public static final String addEval = HOST + "eval/addEval";
    /**
     * 需要抢修的订单
     */
    public static final String orderByPass = HOST + "order/findOrderByPass";
    /**
     * 维修员抢单
     */
    public static final String grabOrder = HOST + "order/repairerGetOrder";
    /**
     * 维修员提交维修
     */
    public static final String orderIsRepair = HOST + "order/orderIsRepair";

}
