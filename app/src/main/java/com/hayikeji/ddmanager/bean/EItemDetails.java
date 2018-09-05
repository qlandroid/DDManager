package com.hayikeji.ddmanager.bean;

import com.hayikeji.ddmanager.ui.widget.dialog.EManagerDialog;

/**
 * 描述：
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2018/8/19
 *
 * @author ql
 */
public class EItemDetails implements EManagerDialog.IEDetails {

    private String label;
    private String value;
    private String unit;

    public EItemDetails(String label, String value, String unit) {
        this.label = label;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public CharSequence getLabel() {
        if (label == null) {
            return "";
        }
        return label;
    }

    @Override
    public CharSequence getValue() {
        if (value == null) {
            return "";
        }
        return value;
    }

    @Override
    public CharSequence getUnit() {
        if (unit == null) {
            return "";
        }
        return unit;
    }
}
