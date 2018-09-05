package com.hayikeji.ddmanager.ui.shopping;

import android.text.TextUtils;

import com.hayikeji.ddmanager.ui.shopping.adapter.IEGoods;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/9/3
 *
 * @author ql
 */
public class GoodsBean implements IEGoods {


    /**
     * id : 1
     * tableName : test1
     * commodityName : commodity_1
     * elecType : elec_type_1
     * voltage : 0
     * currentSpec :
     * pulseCons :
     * accClass :
     * frequency : 0
     * guidePrice : 0
     * bannerImg :
     */

    private int id;
    private String tableName;//参数及报价表
    private String commodityName;//商品名称
    private String elecType;//电表型号
    private int voltage;//电　压
    private String currentSpec;//电流规格
    private String pulseCons;//脉冲常数
    private String accClass;//精度等级
    private int frequency;//频率
    private int guidePrice;//指导价格（台/元）
    private String bannerImg;

    public ArrayList<String> getImgs() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(bannerImg)) {
            return arrayList;
        }
        String[] split = bannerImg.split(",");
        Collections.addAll(arrayList, split);
        return arrayList;
    }

    public String getFirstImg() {
        ArrayList<String> imgs = getImgs();
        if (imgs.size() > 0) {
            return imgs.get(0);
        }
        return null;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getElecType() {
        return elecType;
    }

    public void setElecType(String elecType) {
        this.elecType = elecType;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public String getCurrentSpec() {
        return currentSpec;
    }

    public void setCurrentSpec(String currentSpec) {
        this.currentSpec = currentSpec;
    }

    public String getPulseCons() {
        return pulseCons;
    }

    public void setPulseCons(String pulseCons) {
        this.pulseCons = pulseCons;
    }

    public String getAccClass() {
        return accClass;
    }

    public void setAccClass(String accClass) {
        this.accClass = accClass;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(int guidePrice) {
        this.guidePrice = guidePrice;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    @Override
    public CharSequence getPrice() {
        return guidePrice + "";
    }

    @Override
    public CharSequence getTitle() {
        if (commodityName == null) {
            return "智能电表";
        }
        return commodityName;
    }

    @Override
    public String getImg() {
        return getFirstImg();
    }
}
