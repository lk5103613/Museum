package com.shmuseum.customeview;

import android.app.AlertDialog;
import android.content.Context;

import com.shmuseum.musesum.R;

/**
 * Created by Administrator on 2015/8/10.
 */
public class FullScreenDialog extends AlertDialog {

    public FullScreenDialog(Context context) {
        super(context, R.style.full_screen);
    }

}
