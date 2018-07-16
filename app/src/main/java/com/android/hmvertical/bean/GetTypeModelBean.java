package com.android.hmvertical.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class GetTypeModelBean {


    /**
     * models : [{"modelId":7,"modelName":"5型F8"},{"modelId":9,"modelName":"5型F9"},{"modelId":10,"modelName":"5型F10"}]
     * typeName : 液化石油气5型
     * typeId : 3
     */

    private String typeName;
    private int typeId;
    private List<ModelsBean> models;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean {
        /**
         * modelId : 7
         * modelName : 5型F8
         */

        private int modelId;
        private String modelName;

        public int getModelId() {
            return modelId;
        }

        public void setModelId(int modelId) {
            this.modelId = modelId;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }
    }
}
