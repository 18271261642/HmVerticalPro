package com.android.hmvertical.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */

public class CheckDetailBean implements Serializable{


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

    public static class ListBean implements Serializable{
        /**
         * id : 113
         * checkNumber : 201806050118225800001
         * filltype : null
         * company : 东莞东莞市富民燃气有限公司
         * cylinderType : 液化石油气50型
         * fillingMedium : 液化石油气
         * checkOrder : SED20180605011822304
         * remark :
         * gasNumber : 5555
         * newGsNumber : null
         * createcode :
         * producer : 百福
         * createtype : null
         * createdate : 2018-06-05
         * checkdate : null
         * twobarcodecipher : 00100433205
         * twobarcode : 00100433205
         * result : 待检测
         * isscrap : 0
         */

        private int id;
        private String checkNumber;
        private Object filltype;
        private String company;
        private String cylinderType;
        private String fillingMedium;
        private String checkOrder;
        private String remark;
        private String gasNumber;
        private Object newGsNumber;
        private String createcode;
        private String producer;
        private String createtype;
        private String createdate;
        private String twobarcodecipher;
        private long checkdate;
        private String twobarcode;
        private String result;
        private int isscrap;
        private long nextCheckDate;
        private long lastUseDate;
        private long endUseDate;
        private String scrapText;

        public String getScrapText() {
            return scrapText;
        }

        public void setScrapText(String scrapText) {
            this.scrapText = scrapText;
        }

        public void setCheckdate(long checkdate) {
            this.checkdate = checkdate;
        }

        public long getCheckdate() {
            return checkdate;
        }

        public long getNextCheckDate() {
            return nextCheckDate;
        }

        public void setNextCheckDate(long nextCheckDate) {
            this.nextCheckDate = nextCheckDate;
        }

        public long getLastUseDate() {
            return lastUseDate;
        }

        public void setLastUseDate(long lastUseDate) {
            this.lastUseDate = lastUseDate;
        }

        public long getEndUseDate() {
            return endUseDate;
        }

        public void setEndUseDate(long endUseDate) {
            this.endUseDate = endUseDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCheckNumber() {
            return checkNumber;
        }

        public void setCheckNumber(String checkNumber) {
            this.checkNumber = checkNumber;
        }

        public Object getFilltype() {
            return filltype;
        }

        public void setFilltype(Object filltype) {
            this.filltype = filltype;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCylinderType() {
            return cylinderType;
        }

        public void setCylinderType(String cylinderType) {
            this.cylinderType = cylinderType;
        }

        public String getFillingMedium() {
            return fillingMedium;
        }

        public void setFillingMedium(String fillingMedium) {
            this.fillingMedium = fillingMedium;
        }

        public String getCheckOrder() {
            return checkOrder;
        }

        public void setCheckOrder(String checkOrder) {
            this.checkOrder = checkOrder;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getGasNumber() {
            return gasNumber;
        }

        public void setGasNumber(String gasNumber) {
            this.gasNumber = gasNumber;
        }

        public Object getNewGsNumber() {
            return newGsNumber;
        }

        public void setNewGsNumber(Object newGsNumber) {
            this.newGsNumber = newGsNumber;
        }

        public String getCreatecode() {
            return createcode;
        }

        public void setCreatecode(String createcode) {
            this.createcode = createcode;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public String getCreatetype() {
            return createtype;
        }

        public void setCreatetype(String createtype) {
            this.createtype = createtype;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }


        public String getTwobarcodecipher() {
            return twobarcodecipher;
        }

        public void setTwobarcodecipher(String twobarcodecipher) {
            this.twobarcodecipher = twobarcodecipher;
        }

        public String getTwobarcode() {
            return twobarcode;
        }

        public void setTwobarcode(String twobarcode) {
            this.twobarcode = twobarcode;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getIsscrap() {
            return isscrap;
        }

        public void setIsscrap(int isscrap) {
            this.isscrap = isscrap;
        }


    }
}
