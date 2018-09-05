package com.hayikeji.ddmanager.ui.shopping;

import android.os.Bundle;
import android.ql.bindview.BindView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hayikeji.ddmanager.C;
import com.hayikeji.ddmanager.R;
import com.hayikeji.ddmanager.base.BaseActivity;
import com.hayikeji.ddmanager.base.BindLayout;
import com.hayikeji.ddmanager.bean.BaseResult;
import com.hayikeji.ddmanager.http.OkHttpHeader;
import com.hayikeji.ddmanager.http.ResultCallback2;
import com.hayikeji.ddmanager.info.UrlApi;
import com.hayikeji.ddmanager.utils.ImgUtils;
import com.hayikeji.ddmanager.utils.ViewUtils;
import com.youth.banner.Banner;

import java.util.HashMap;
import java.util.Map;

@BindLayout(layoutRes = R.layout.activity_shopping_goods, title = "商品详情")
public class ShoppingGoodsActivity extends BaseActivity {

    @BindView(R.id.activity_shopping_goods_tv_title)
    TextView tvTitle;
    @BindView(R.id.activity_shopping_goods_tv_dy)
    TextView tvDy;
    @BindView(R.id.activity_shopping_goods_tv_gg)
    TextView tvGG;
    @BindView(R.id.activity_shopping_goods_tv_jd)
    TextView tvJd;
    @BindView(R.id.activity_shopping_goods_tv_mc)
    TextView tvMc;
    @BindView(R.id.activity_shopping_goods_tv_pl)
    TextView tvPl;
    @BindView(R.id.activity_shopping_goods_tv_price)
    TextView tvPrice;
    @BindView(R.id.activity_shopping_goods_tv_type)
    TextView tvType;
    @BindView(R.id.activity_shopping_goods_tv_sbumit)
    TextView tvSubmit;
    @BindView(R.id.activity_shopping_goods_iv_banner)
    ImageView ivBanner;

    GoodsBean dataO;

    public static void put(int goodsId, Bundle bundle) {
        bundle.putInt("goodsId", goodsId);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        tvSubmit.setOnClickListener(this);
        int goodsId = getBundle().getInt("goodsId");
        displayLoadingDialog("加载中");

        ViewGroup.LayoutParams layoutParams = ivBanner.getLayoutParams();
        layoutParams.height = C.SCREEN_HEIGHT_3;
        ivBanner.setLayoutParams(layoutParams);
        loadGoods(goodsId);
    }

    @Override
    public void forbidClick(View v) {
        super.forbidClick(v);
        switch (v.getId()) {
            case R.id.activity_shopping_goods_tv_sbumit:
                Bundle bundle = new Bundle();
                PaymentActivity.put(dataO.getGuidePrice(), dataO.getId(), bundle);
                startActivity(PaymentActivity.class, bundle);
                break;
        }
    }

    private void loadGoods(int goodsId) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", goodsId);

        OkHttpHeader.post(UrlApi.goods_details, map, new ResultCallback2() {
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
                dataO = response.getDataO(GoodsBean.class);

                setTextView(dataO.getTitle(), tvTitle)
                        .setTextView(dataO.getAccClass(), tvJd)
                        .setTextView(dataO.getElecType(), tvType)
                        .setTextView(dataO.getVoltage() + "", tvDy)
                        .setTextView(dataO.getCurrentSpec(), tvGG)
                        .setTextView(dataO.getPulseCons(), tvMc)
                        .setTextView(dataO.getAccClass(), tvMc)
                        .setTextView(dataO.getFrequency() + "", tvPl)
                        .setTextView(dataO.getGuidePrice() + "", tvPrice);
                String firstImg = dataO.getFirstImg();
                if (TextUtils.isEmpty(firstImg)) {
                    return;
                }
                Glide.with(ShoppingGoodsActivity.this)
                        .load(firstImg)
                        .asBitmap()
                        .into(ivBanner);
            }
        });
    }
}
