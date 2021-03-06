package com.shmuseum.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmuseum.entity.GoodInfo;
import com.shmuseum.musesum.R;

public class IntroAdapter extends SimpleAdapter<GoodInfo> {

    private List<GoodInfo> data;

    public IntroAdapter(Context context, List<GoodInfo> data) {
        super(context,data);
        this.data = data;
    }

    @Override
    public int getItemResourceId() {
        return R.layout.item_intro;
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder holder) {
        GoodInfo goodInfo = data.get(position);
        ImageView goodIV = holder.findView(R.id.imageview1);
        TextView introContentTV = holder.findView(R.id.textview1);
        TextView sizeTV = holder.findView(R.id.textview2);
        TextView priceTV = holder.findView(R.id.textview3);

        goodIV.setImageResource(goodInfo.getImageId());
        introContentTV.setText(goodInfo.getIntro());
        sizeTV.setText(goodInfo.getSize());
        priceTV.setText(goodInfo.getPrice());

    }
}
