package com.hayikeji.ddmanager.bean;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class EDetails {


    private DeviceBean devO;

    private TotalE totalE;
    private TotalE monthE;
    private TotalE yearE;
    private Double unit;
    private Long bindDate;
    private Double buyElectric;

    public DeviceBean getDevO() {
        return devO;
    }

    public void setDevO(DeviceBean devO) {
        this.devO = devO;
    }

    public TotalE getTotalE() {
        return totalE;
    }

    public void setTotalE(TotalE totalE) {
        this.totalE = totalE;
    }

    public TotalE getMonthE() {
        return monthE;
    }

    public void setMonthE(TotalE monthE) {
        this.monthE = monthE;
    }

    public TotalE getYearE() {
        return yearE;
    }

    public void setYearE(TotalE yearE) {
        this.yearE = yearE;
    }

    public Double getUnit() {
        return unit;
    }

    public void setUnit(Double unit) {
        this.unit = unit;
    }

    public Long getBindDate() {
        return bindDate;
    }

    public void setBindDate(Long bindDate) {
        this.bindDate = bindDate;
    }

    public Double getBuyElectric() {
        return buyElectric;
    }

    public void setBuyElectric(Double buyElectric) {
        this.buyElectric = buyElectric;
    }

    public static class TotalE {
        private Integer totalE;
        private Integer year;
        private Integer month;

        public Integer getTotalE() {
            return totalE;
        }

        public void setTotalE(Integer totalE) {
            this.totalE = totalE;
        }

        public Integer getYear() {
            if (year == null) {
                return 0;
            }
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getMonth() {
            if (month == null) {
                return 0;
            }
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }
    }
}
