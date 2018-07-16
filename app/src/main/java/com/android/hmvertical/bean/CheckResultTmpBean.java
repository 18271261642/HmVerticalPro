package com.android.hmvertical.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/2.
 */

/**
 * 检测结果详情bean
 * 回填的bean
 */
public class CheckResultTmpBean {


    private List<CheckItemResultModelsBean> checkItemResultModels;
    private List<CheckInfoResultsBean> checkInfoResults;

    public List<CheckItemResultModelsBean> getCheckItemResultModels() {
        return checkItemResultModels;
    }

    public void setCheckItemResultModels(List<CheckItemResultModelsBean> checkItemResultModels) {
        this.checkItemResultModels = checkItemResultModels;
    }

    public List<CheckInfoResultsBean> getCheckInfoResults() {
        return checkInfoResults;
    }

    public void setCheckInfoResults(List<CheckInfoResultsBean> checkInfoResults) {
        this.checkInfoResults = checkInfoResults;
    }

    public static class CheckItemResultModelsBean {
        /**
         * modelId : 5
         * name : 50型F11
         */

        private int modelId;
        private String name;

        public int getModelId() {
            return modelId;
        }

        public void setModelId(int modelId) {
            this.modelId = modelId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CheckInfoResultsBean {
        /**
         * id : 492
         * checkResult : {"id":1,"result":"合格","remark":"","disabledState":1}
         * checkItem : {"id":8,"name":"点腐蚀","content":"dfsd","remark":"","appShow":1,"disabledState":1,"content_id":12,"content_name":"1212"}
         * checkOrderInfo : 201806050118226000002
         */

        private int id;
        private CheckResultBean checkResult;
        private CheckItemBean checkItem;
        private String checkOrderInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public CheckResultBean getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(CheckResultBean checkResult) {
            this.checkResult = checkResult;
        }

        public CheckItemBean getCheckItem() {
            return checkItem;
        }

        public void setCheckItem(CheckItemBean checkItem) {
            this.checkItem = checkItem;
        }

        public String getCheckOrderInfo() {
            return checkOrderInfo;
        }

        public void setCheckOrderInfo(String checkOrderInfo) {
            this.checkOrderInfo = checkOrderInfo;
        }

        public static class CheckResultBean {
            /**
             * id : 1
             * result : 合格
             * remark :
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

        public static class CheckItemBean {
            /**
             * id : 8
             * name : 点腐蚀
             * content : dfsd
             * remark :
             * appShow : 1
             * disabledState : 1
             * content_id : 12
             * content_name : 1212
             */

            private int id;
            private String name;
            private String content;
            private String remark;
            private int appShow;
            private int disabledState;
            private int content_id;
            private String content_name;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getAppShow() {
                return appShow;
            }

            public void setAppShow(int appShow) {
                this.appShow = appShow;
            }

            public int getDisabledState() {
                return disabledState;
            }

            public void setDisabledState(int disabledState) {
                this.disabledState = disabledState;
            }

            public int getContent_id() {
                return content_id;
            }

            public void setContent_id(int content_id) {
                this.content_id = content_id;
            }

            public String getContent_name() {
                return content_name;
            }

            public void setContent_name(String content_name) {
                this.content_name = content_name;
            }
        }
    }
}
