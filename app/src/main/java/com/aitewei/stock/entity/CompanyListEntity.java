package com.aitewei.stock.entity;

import java.util.List;

/**
 * Description:    公司列表
 * Author:         张俊杰
 * CreateDate:     2020/3/15 下午7:23
 * Version:        1.0
 */
public class CompanyListEntity {

    private List<DataTableBean> DataTable;

    public List<DataTableBean> getDataTable() {
        return DataTable;
    }

    public void setDataTable(List<DataTableBean> DataTable) {
        this.DataTable = DataTable;
    }

    public static class DataTableBean {
        /**
         * 名称代码 : 上证指数(sh000001)
         * 公司代码 : sh000001
         * 公司名称 : 上证指数
         * 公司缩写 :
         */

        private String 名称代码;
        private String 公司代码;
        private String 公司名称;
        private String 公司缩写;

        public String get名称代码() {
            return 名称代码;
        }

        public void set名称代码(String 名称代码) {
            this.名称代码 = 名称代码;
        }

        public String get公司代码() {
            return 公司代码;
        }

        public void set公司代码(String 公司代码) {
            this.公司代码 = 公司代码;
        }

        public String get公司名称() {
            return 公司名称;
        }

        public void set公司名称(String 公司名称) {
            this.公司名称 = 公司名称;
        }

        public String get公司缩写() {
            return 公司缩写;
        }

        public void set公司缩写(String 公司缩写) {
            this.公司缩写 = 公司缩写;
        }
    }
}
