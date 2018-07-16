package com.android.hmvertical.bean;

/**
 * Created by Administrator on 2018/7/4.
 */

public class WaitInfoCheckBean {


    /**
     * checkOrder : {"id":90,"number":"SUS20180627031056176","company":"东莞喜威液化石油气有限公司","filltype":1,"count":2,"createtime":1530069056000,"updatetime":1530069056000,"checkstatus":2,"remark":"sd","disabledState":1,"upset":null}
     * waitCount : 2
     */

    private CheckOrderBean checkOrder;
    private int waitCount;

    public CheckOrderBean getCheckOrder() {
        return checkOrder;
    }

    public void setCheckOrder(CheckOrderBean checkOrder) {
        this.checkOrder = checkOrder;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(int waitCount) {
        this.waitCount = waitCount;
    }

    public static class CheckOrderBean {
        /**
         * id : 90
         * number : SUS20180627031056176
         * company : 东莞喜威液化石油气有限公司
         * filltype : 1
         * count : 2
         * createtime : 1530069056000
         * updatetime : 1530069056000
         * checkstatus : 2
         * remark : sd
         * disabledState : 1
         * upset : null
         */

        private int id;
        private String number;
        private String company;
        private int filltype;
        private int count;
        private long createtime;
        private long updatetime;
        private int checkstatus;
        private String remark;
        private int disabledState;
        private Object upset;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getFilltype() {
            return filltype;
        }

        public void setFilltype(int filltype) {
            this.filltype = filltype;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public long getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(long updatetime) {
            this.updatetime = updatetime;
        }

        public int getCheckstatus() {
            return checkstatus;
        }

        public void setCheckstatus(int checkstatus) {
            this.checkstatus = checkstatus;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getDisabledState() {
            return disabledState;
        }

        public void setDisabledState(int disabledState) {
            this.disabledState = disabledState;
        }

        public Object getUpset() {
            return upset;
        }

        public void setUpset(Object upset) {
            this.upset = upset;
        }
    }
}
