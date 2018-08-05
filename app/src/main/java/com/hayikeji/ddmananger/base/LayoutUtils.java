package com.hayikeji.ddmananger.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hayikeji.ddmananger.R;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBar;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ql
 * 邮箱 email:strive_bug@yeah.net
 * 创建时间 2017/11/25
 */

public class LayoutUtils {
    public static void bind(BaseActivity object) {
        Class<?> aClass = object.getClass();
        if (!aClass.isAnnotationPresent(BindLayout.class)) {
            return;
        }
        bindActivityLayout(object);
    }

    public static void bind(BaseFragment fragment){
       Class clazz =  fragment.getClass();
        if (!clazz.isAnnotationPresent(BindLayout.class)) {
            return;
        }
        BindLayout bindLayout = (BindLayout) clazz.getAnnotation(BindLayout.class);
        fragment.layoutRes = bindLayout.layoutRes();
    }



    private static void bindFragmentLayout(Object object) {
        Class aClass = object.getClass();
        BindLayout annotation = (BindLayout) aClass.getAnnotation(BindLayout.class);
        try {
            Field layoutRes = aClass.getDeclaredField("layoutRes");
            layoutRes.setAccessible(true);
            layoutRes.set(object, annotation.layoutRes());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void bindActivityLayout(Object object) {
        Class aClass = object.getClass();
        BindLayout annotation = (BindLayout) aClass.getAnnotation(BindLayout.class);
        int layoutRes = annotation.layoutRes();
        if (-1 != layoutRes) {
            try {
                if (annotation.bindTopBar()) {
                    bindTopBar(object, aClass, layoutRes);
                } else {
                    bindContentView(object, aClass, layoutRes);
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (!annotation.bindTopBar()) {
            return;
        }
        Field mTopBar = null;
        try {
            mTopBar = aClass.getField("mTopBar");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (mTopBar == null) {
            return;
        }
        QMUITopBar topBar = null;
        try {
            Method findViewById = aClass.getMethod("findViewById", int.class);
            if (findViewById == null) {
                return;
            }
            int tobarId = annotation.topbarId();
            Object obj = findViewById.invoke(object, tobarId);
            if (!(obj instanceof QMUITopBar)) {
                return;
            }
            topBar = (QMUITopBar) obj;

            mTopBar.set(object, obj);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (topBar == null) return;

        String title = annotation.title();
        if (!TextUtils.isEmpty(title)) {
            topBar.setTitle(title);
        } else if (-1 != annotation.titleRes()) {
            topBar.setTitle(annotation.titleRes());
        }


        int backRes = annotation.backRes();
        if (0 != backRes) {
            QMUIAlphaImageButton backImageButton = topBar.addLeftBackImageButton();
            backImageButton.setOnClickListener((View.OnClickListener) object);
            backImageButton.setImageResource(backRes);
            backImageButton.setChangeAlphaWhenPress(true);
        }
    }

    private static void bindContentView(Object object, Class<?> aClass, int layoutRes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method setLayoutRes = aClass.getMethod("setContentView", new Class[]{int.class});
        if (setLayoutRes != null) {
            setLayoutRes.invoke(object, layoutRes);
        }
    }


    private static void bindTopBar(Object object, Class<?> aClass, int layoutRes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Context ctx = (Context) object;
        View inflate = LayoutInflater.from(ctx).inflate(layoutRes, null, false);
        LinearLayout linearLayout = new LinearLayout(ctx);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        QMUITopBar topBar = new QMUITopBar(ctx);
        topBar.setId(R.id.topbar);
        float topbarHeight = ctx.getResources().getDimension(R.dimen.titleHeight);
        float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, topbarHeight, ctx.getResources().getDisplayMetrics());
        topBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) v));
        linearLayout.addView(topBar);
        linearLayout.addView(inflate, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Method setLayoutRes = aClass.getMethod("setContentView", new Class[]{View.class});
        if (setLayoutRes != null) {
            setLayoutRes.invoke(object, linearLayout);
        }
    }

    public static void bindFragmentTopbar(BaseFragment fragment, View view) {
        fragment.mTopbar = (QMUITopBar) view.findViewById(R.id.topbar);
        if (fragment.mTopbar == null) {
            return;
        }
        Class<? extends BaseFragment> aClass = fragment.getClass();
        if (!aClass.isAnnotationPresent(BindLayout.class)) {
            return;
        }
        BindLayout bindLayout = aClass.getAnnotation(BindLayout.class);
        String title = bindLayout.title();
        if (!TextUtils.isEmpty(title)) {
            fragment.mTopbar.setTitle(title);
        } else if (-1 != bindLayout.titleRes()) {
            fragment.mTopbar.setTitle(bindLayout.titleRes());
        }

        int backRes = bindLayout.backRes();
        if (0 != backRes) {
            QMUIAlphaImageButton backImageButton = fragment.mTopbar.addLeftBackImageButton();
            backImageButton.setOnClickListener(fragment);
            backImageButton.setImageResource(backRes);
            backImageButton.setChangeAlphaWhenPress(true);
        }
    }
}