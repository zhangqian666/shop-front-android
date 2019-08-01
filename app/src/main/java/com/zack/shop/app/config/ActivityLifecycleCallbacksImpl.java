/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zack.shop.app.config;


import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.zack.shop.R;
import com.zack.shop.app.base.BaseSupportActivity;

import java.util.Objects;

import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.w("%s - onActivityCreated", activity);

    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.w("%s - onActivityStarted", activity);
        if (activity.findViewById(R.id.toolbar) != null) { //找到 Toolbar 并且替换 Actionbar
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).setSupportActionBar(activity.findViewById(R.id.toolbar));
                Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).setDisplayShowTitleEnabled(false);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.setActionBar(activity.findViewById(R.id.toolbar));
                    Objects.requireNonNull(activity.getActionBar()).setDisplayShowTitleEnabled(false);
                }
            }
        }
        if (activity.findViewById(R.id.toolbar_back) != null) { //找到 Toolbar 的标题栏并设置标题名
            activity.findViewById(R.id.toolbar_back)
                    .setOnClickListener(v -> ((BaseSupportActivity) activity).onBackPressedSupport()
                    );
        }

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.w("%s - onActivityResumed", activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.w("%s - onActivityPaused", activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.w("%s - onActivityStopped", activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.w("%s - onActivitySaveInstanceState", activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.w("%s - onActivityDestroyed", activity);
    }
}
