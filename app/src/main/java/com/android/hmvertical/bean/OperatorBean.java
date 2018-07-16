package com.android.hmvertical.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */

/**
 * 项目和检测结果
 */
public class OperatorBean {


    /**
     * results : [{"id":1,"result":"合格","remark":"dfad","disabledState":1},{"id":6,"result":"局部凹陷","remark":"3222","disabledState":1}]
     * item : {"id":1,"name":"凹陷","content":null,"remark":"明显的凹陷胜多负少发斯蒂芬克里斯多夫","disabledState":1,"content_id":"fds","content_name":"fdsfd"}
     */

    private ItemBean item;
    private List<ResultsBean> results;

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ItemBean {
        /**
         * id : 1
         * name : 凹陷
         * content : null
         * remark : 明显的凹陷胜多负少发斯蒂芬克里斯多夫
         * disabledState : 1
         * content_id : fds
         * content_name : fdsfd
         */

        private int id;
        private String name;
        private Object content;
        private String remark;
        private int disabledState;
        private String content_id;
        private int appShow;
        private String content_name;

        public int getAppShow() {
            return appShow;
        }

        public void setAppShow(int appShow) {
            this.appShow = appShow;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
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

        public String getContent_id() {
            return content_id;
        }

        public void setContent_id(String content_id) {
            this.content_id = content_id;
        }

        public String getContent_name() {
            return content_name;
        }

        public void setContent_name(String content_name) {
            this.content_name = content_name;
        }
    }

    public static class ResultsBean {
        /**
         * id : 1
         * result : 合格
         * remark : dfad
         * disabledState : 1
         */

        private int id;
        private String result;
        private String remark;
        private int disabledState;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
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
    }
}
