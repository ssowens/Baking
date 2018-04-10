package com.ssowens.android.baking.utils;

import android.content.Context;

import com.ssowens.android.baking.R;

/**
 * Created by Sheila Owens on 4/9/18.
 */
public class DeviceUtils {

    private Context context;
    public static final int DEVICE_TABLET_THRESHOLD = 1;

    public DeviceUtils(Context context) {
        this.context = context;
    }

    public boolean isTablet() {
        return context.getResources().getInteger(R.integer.device_layout_type) >
                DEVICE_TABLET_THRESHOLD;
    }
}
