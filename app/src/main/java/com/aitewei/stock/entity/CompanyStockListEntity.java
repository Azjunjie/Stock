package com.aitewei.stock.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description:    公司股票列表
 * Author:         张俊杰
 * CreateDate:     2020/3/16 下午8:14
 * Version:        1.0
 */
public class CompanyStockListEntity implements Serializable {

    private List<DataTableBean> DataTable;

    public List<DataTableBean> getDataTable() {
        return DataTable;
    }

    public void setDataTable(List<DataTableBean> DataTable) {
        this.DataTable = DataTable;
    }

    public static class DataTableBean implements Serializable {
        /**
         * 综合排名 : 1
         * 综合分数 : 10
         * 收益率排名 : 8
         * 成功率排名 : 2
         * 公司代码 : sh603306
         * 公式时间 : 2020-03-03
         * 公式代码 : FBB
         * 公式周期 : D2
         * 成功 : 4
         * 失败 : 0
         * 投入 : 13665.462890625
         * 收益 : 3360.533203125
         * 收益率 : 24.5914333822563
         * 成功率 : 100
         * 是否快速 : 是
         * 是否复权 : 是
         * 持股持币 : 持币
         * 最优参数 : N=8,M=10
         * 买卖条件 : 买入条件=BB/上穿/9,卖出条件=BB/下穿/91
         * 交易类型 : 成功优先
         * 参数状态 : BB=127.8319,UB=14.52689,LB=13.54562,BOLL=14.03626,Open=14.37,Close=14.8,High=14.88,Low=14.26,Amount=7.432837E+07,Vol=5068709,Avg=14.66416
         * 参数趋势 : BB=上涨,UB=上涨,LB=上涨,BOLL=上涨,Open=上涨,Close=上涨,High=上涨,Low=上涨,Amount=上涨,Vol=上涨,Avg=上涨
         * 方案明细id : 346581570
         * 末次交易时间 : /Date(1582560000000+0800)/
         * 末次交易价格 : 13.9
         * 测试开始时间 : /Date(1568822400000+0800)/
         * 测试结束时间 : /Date(1583164800000+0800)/
         */

        private int 综合排名;
        private int 综合分数;
        private int 收益率排名;
        private int 成功率排名;
        private String 公司代码;
        private String 公式时间;
        private String 公式代码;
        private String 公式周期;
        private double 成功;
        private double 失败;
        private double 投入;
        private double 收益;
        private double 收益率;
        private double 成功率;
        private String 是否快速;
        private String 是否复权;
        private String 持股持币;
        private String 最优参数;
        private String 买卖条件;
        private String 交易类型;
        private String 参数状态;
        private String 参数趋势;
        private String 方案明细id;
        private String 末次交易时间;
        private double 末次交易价格;
        private String 测试开始时间;
        private String 测试结束时间;

        public int get综合排名() {
            return 综合排名;
        }

        public void set综合排名(int 综合排名) {
            this.综合排名 = 综合排名;
        }

        public int get综合分数() {
            return 综合分数;
        }

        public void set综合分数(int 综合分数) {
            this.综合分数 = 综合分数;
        }

        public int get收益率排名() {
            return 收益率排名;
        }

        public void set收益率排名(int 收益率排名) {
            this.收益率排名 = 收益率排名;
        }

        public int get成功率排名() {
            return 成功率排名;
        }

        public void set成功率排名(int 成功率排名) {
            this.成功率排名 = 成功率排名;
        }

        public String get公司代码() {
            return 公司代码;
        }

        public void set公司代码(String 公司代码) {
            this.公司代码 = 公司代码;
        }

        public String get公式时间() {
            return 公式时间;
        }

        public void set公式时间(String 公式时间) {
            this.公式时间 = 公式时间;
        }

        public String get公式代码() {
            return 公式代码;
        }

        public void set公式代码(String 公式代码) {
            this.公式代码 = 公式代码;
        }

        public String get公式周期() {
            return 公式周期;
        }

        public void set公式周期(String 公式周期) {
            this.公式周期 = 公式周期;
        }

        public double get成功() {
            return 成功;
        }

        public void set成功(double 成功) {
            this.成功 = 成功;
        }

        public double get失败() {
            return 失败;
        }

        public void set失败(double 失败) {
            this.失败 = 失败;
        }

        public double get投入() {
            return 投入;
        }

        public void set投入(double 投入) {
            this.投入 = 投入;
        }

        public double get收益() {
            return 收益;
        }

        public void set收益(double 收益) {
            this.收益 = 收益;
        }

        public double get收益率() {
            return 收益率;
        }

        public void set收益率(double 收益率) {
            this.收益率 = 收益率;
        }

        public double get成功率() {
            return 成功率;
        }

        public void set成功率(double 成功率) {
            this.成功率 = 成功率;
        }

        public String get是否快速() {
            return 是否快速;
        }

        public void set是否快速(String 是否快速) {
            this.是否快速 = 是否快速;
        }

        public String get是否复权() {
            return 是否复权;
        }

        public void set是否复权(String 是否复权) {
            this.是否复权 = 是否复权;
        }

        public String get持股持币() {
            return 持股持币;
        }

        public void set持股持币(String 持股持币) {
            this.持股持币 = 持股持币;
        }

        public String get最优参数() {
            return 最优参数;
        }

        public void set最优参数(String 最优参数) {
            this.最优参数 = 最优参数;
        }

        public String get买卖条件() {
            return 买卖条件;
        }

        public void set买卖条件(String 买卖条件) {
            this.买卖条件 = 买卖条件;
        }

        public String get交易类型() {
            return 交易类型;
        }

        public void set交易类型(String 交易类型) {
            this.交易类型 = 交易类型;
        }

        public String get参数状态() {
            return 参数状态;
        }

        public void set参数状态(String 参数状态) {
            this.参数状态 = 参数状态;
        }

        public String get参数趋势() {
            return 参数趋势;
        }

        public void set参数趋势(String 参数趋势) {
            this.参数趋势 = 参数趋势;
        }

        public String get方案明细id() {
            return 方案明细id;
        }

        public void set方案明细id(String 方案明细id) {
            this.方案明细id = 方案明细id;
        }

        public String get末次交易时间() {
            return 末次交易时间;
        }

        public void set末次交易时间(String 末次交易时间) {
            this.末次交易时间 = 末次交易时间;
        }

        public double get末次交易价格() {
            return 末次交易价格;
        }

        public void set末次交易价格(double 末次交易价格) {
            this.末次交易价格 = 末次交易价格;
        }

        public String get测试开始时间() {
            return 测试开始时间;
        }

        public void set测试开始时间(String 测试开始时间) {
            this.测试开始时间 = 测试开始时间;
        }

        public String get测试结束时间() {
            return 测试结束时间;
        }

        public void set测试结束时间(String 测试结束时间) {
            this.测试结束时间 = 测试结束时间;
        }
    }
}
