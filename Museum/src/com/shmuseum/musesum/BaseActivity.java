package com.shmuseum.musesum;

import android.os.Build;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	
	public static final boolean IS_JBMR2 = Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2;

}
