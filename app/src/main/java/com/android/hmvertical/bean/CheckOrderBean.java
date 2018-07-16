package com.android.hmvertical.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */

public class CheckOrderBean {


    /**
     * total : 2
     * page : 1
     * list : [{"id":71,"number":"SED20180605011822304",
     * "company":"东莞东莞市富民燃气有限公司",
     * "filltype":1,"count":4,
     * "createtime":1528161502000,
     * "updatetime":1528161502000,
     * "checkstatus":2,"remark":"d11",
     * "disabledState":1,"upset":1},
     * {"id":72,"number":"SED20180605012052626","
     * company":"东莞东莞市富民燃气有限公司",
     * "filltype":1,"count":37,
     * "createtime":1528161652000,
     * "updatetime":1528161502000,
     * "checkstatus":2,"remark":"212","
     * disabledState":1,"upset":1}]
     */

    private int total;
    private int page;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 71
         * number : SED20180605011822304
         * company : 东莞东莞市富民燃气有限公司
         * filltype : 1
         * count : 4
         * createtime : 1528161502000
         * updatetime : 1528161502000
         * checkstatus : 2
         * remark : d11
         * disabledState : 1
         * upset : 1
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
        private int upset;

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

        public int getUpset() {
            return upset;
        }

        public void setUpset(int upset) {
            this.upset = upset;
        }
    }
}
