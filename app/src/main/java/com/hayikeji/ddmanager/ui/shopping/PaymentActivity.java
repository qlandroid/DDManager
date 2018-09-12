package com.hayikeji.ddmanager.ui.shopping;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hayikeji.ddmanager.C;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.bean.PaymentParams;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;

@BindLayout(layoutRes = R.layout.activity_payment, title = "购买填写信息")
public class PaymentActivity extends BaseActivity {
    @BindView(R.id.activity_payment_tv_submit)
    View vSubmit;
    @BindView(R.id.activity_payment_iv_add)
    View vAdd;
    @BindView(R.id.activity_payment_iv_del)
    View vDel;
    @BindView(R.id.activity_payment_et_buy_count)
    EditText etBuyCount;
    @BindView(R.id.activity_payment_tv_buy_price)
    TextView tvBuyPrice;
    @BindView(R.id.activity_payment_et_bank)
    EditText etBank;
    @BindView(R.id.activity_payment_et_bank_account)
    EditText etBankAccount;
    @BindView(R.id.activity_payment_et_user_name)
    EditText etUserName;
    @BindView(R.id.activity_payment_et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.activity_payment_et_address)
    EditText etAddress;
    @BindView(R.id.activity_payment_et_address_2)
    EditText etAddress2;
    @BindView(R.id.activity_payment_et_phone_2)
    EditText etPhone2;
    @BindView(R.id.activity_payment_et_name_2)
    EditText etName2;
    @BindView(R.id.activity_payment_et_bank_contact)
    EditText etBankContact;
    @BindView(R.id.activity_payment_et_server_name)
    EditText etServerName;//服务商名称
    @BindView(R.id.activity_payment_et_ele_price)
    EditText etElePrice;


    private int goodsId;
    private int price;

    public static void put(int price, int goodsId, Bundle bundle) {
        bundle.putInt("price", price);
        bundle.putInt("goodsId", goodsId);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getBundle();
        price = bundle.getInt("price");
        goodsId = bundle.getInt("goodsId");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        vSubmit.setOnClickListener(this);
        vAdd.setOnClickListener(this);
        vDel.setOnClickListener(this);
        etBuyCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resetPrice();
            }
        });
        resetPrice();
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_payment_tv_submit:
                PaymentParams p = new PaymentParams();
                p.setDeviceNum(getNowCount());
                p.setGoodsId(goodsId);
                p.setBankName(etBankContact.getText().toString());
                p.setOpenBank(etBank.getText().toString());
                p.setContacts(etBankAccount.getText().toString());
                p.setAccNum(etUserName.getText().toString());
                p.setContactsNum(etUserPhone.getText().toString());
                p.setPostalAddress(etAddress.getText().toString());
                p.setReceAddress(etAddress2.getText().toString());
                p.setConsignee(etName2.getText().toString());
                p.setPhone(etPhone2.getText().toString());

                p.setCategory(etServerName.getText().toString());
                String s = etElePrice.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    toast("请输入电费单价");
                    return;
                }
                double v1 = 0;
                try {
                    v1 = Double.parseDouble(s);
                } catch (Exception e) {
                    toast("请输入电费单价");
                    return;
                }
                int price = (int) (v1 * 100);
                p.setPrice(price);

                displayLoadingDialog("提交中");
                OkHttpHeader.post(UrlApi.payment, p, new ResultCallback2() {
                    @Override
                    protected void onFailed(String error, int code) {
                        cancelLoadingDialog();
                        displayMessageDialog(error);
                    }

                    @Override
                    protected void onSuccess(BaseResult response, int id) {
                        cancelLoadingDialog();
                        if (!response.isSuccess()) {
                            displayMessageDialog(response.getMessage());
                            return;
                        }
                        displayTipDialogSuccess("提交成功", 3_000);
                        C.sHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 3_100);
                    }
                });

                break;
            case R.id.activity_payment_iv_add:
                int nowCount = getNowCount();
                if (nowCount < 1) {
                    etBuyCount.setText(1 + "");
                    return;
                }
                nowCount++;
                etBuyCount.setText(nowCount + "");
                break;
            case R.id.activity_payment_iv_del:
                int nowCount1 = getNowCount();

                if (nowCount1 <= 1) {
                    etBuyCount.setText(1 + "");
                    return;
                }
                nowCount1--;
                etBuyCount.setText(nowCount1 + "");
                break;
        }
    }

    private void resetPrice() {
        int nowCount = getNowCount();
        int i = nowCount * price;
        tvBuyPrice.setText(i + "");
    }

    private int getNowCount() {
        String s = etBuyCount.getText().toString();
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }
}
