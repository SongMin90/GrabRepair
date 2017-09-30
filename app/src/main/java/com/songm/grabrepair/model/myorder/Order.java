package com.songm.grabrepair.model.myorder;

import java.util.List;

/**
 * Created by SongM on 2017/9/22.
 */

public class Order {

    /**
     * find : success
     * orderList : [{"orderId":15,"stuId":"1","orderState":12,"orderStartTime":1505566809000,"orderOverTime":1505655668000,"hmrId":null,"orderRoom":"A1-101","orderSort":2,"adminId":null,"repairerId":5,"orderInfo":"报修是一个汉语词语，拼音是bào xiū，意思是设备等损坏或发生故障，告知有关部门前来修理。\r\nbào xiū\r\n设备等损坏或发生故障，告知有关部门前来修理：住房漏水，住户可向房管部门～。\r\n\r\n报修是一个汉语词语，拼音是bào xiū，意思是设备等损坏或发生故障，告知有关部门前来修理。\r\nbào xiū\r\n设备等损坏或发生故障，告知有关部门前来修理：住房漏水，住户可向房管部门～。","listImgUrl":"imgs/2017_09_16/21_00_10/9198790211652268678275262.jpg"}]
     */

    private String find;
    private List<OrderListBean> orderList;

    public String getFind() {
        return find;
    }

    public void setFind(String find) {
        this.find = find;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {
        /**
         * orderId : 15
         * stuId : 1
         * orderState : 12
         * orderStartTime : 1505566809000
         * orderOverTime : 1505655668000
         * hmrId : null
         * orderRoom : A1-101
         * orderSort : 2
         * adminId : null
         * repairerId : 5
         * orderInfo : 报修是一个汉语词语，拼音是bào xiū，意思是设备等损坏或发生故障，告知有关部门前来修理。
         bào xiū
         设备等损坏或发生故障，告知有关部门前来修理：住房漏水，住户可向房管部门～。

         报修是一个汉语词语，拼音是bào xiū，意思是设备等损坏或发生故障，告知有关部门前来修理。
         bào xiū
         设备等损坏或发生故障，告知有关部门前来修理：住房漏水，住户可向房管部门～。
         * listImgUrl : imgs/2017_09_16/21_00_10/9198790211652268678275262.jpg
         */

        private int orderId;
        private String stuId;
        private int orderState;
        private long orderStartTime;
        private long orderOverTime;
        private Object hmrId;
        private String orderRoom;
        private int orderSort;
        private Object adminId;
        private int repairerId;
        private String orderInfo;
        private String listImgUrl;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public long getOrderStartTime() {
            return orderStartTime;
        }

        public void setOrderStartTime(long orderStartTime) {
            this.orderStartTime = orderStartTime;
        }

        public long getOrderOverTime() {
            return orderOverTime;
        }

        public void setOrderOverTime(long orderOverTime) {
            this.orderOverTime = orderOverTime;
        }

        public Object getHmrId() {
            return hmrId;
        }

        public void setHmrId(Object hmrId) {
            this.hmrId = hmrId;
        }

        public String getOrderRoom() {
            return orderRoom;
        }

        public void setOrderRoom(String orderRoom) {
            this.orderRoom = orderRoom;
        }

        public int getOrderSort() {
            return orderSort;
        }

        public void setOrderSort(int orderSort) {
            this.orderSort = orderSort;
        }

        public Object getAdminId() {
            return adminId;
        }

        public void setAdminId(Object adminId) {
            this.adminId = adminId;
        }

        public int getRepairerId() {
            return repairerId;
        }

        public void setRepairerId(int repairerId) {
            this.repairerId = repairerId;
        }

        public String getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
        }

        public String getListImgUrl() {
            return listImgUrl;
        }

        public void setListImgUrl(String listImgUrl) {
            this.listImgUrl = listImgUrl;
        }
    }
}
