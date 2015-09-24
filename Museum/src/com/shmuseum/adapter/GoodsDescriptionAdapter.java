package com.shmuseum.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shmuseum.entity.GoodInfo;
import com.shmuseum.musesum.R;

import java.util.List;

public class GoodsDescriptionAdapter extends SimpleAdapter<GoodInfo> {

    private List<GoodInfo> data;

    public GoodsDescriptionAdapter(Context context, List<GoodInfo> data) {
        super(context,data);
        this.data = data;
    }

    @Override
    public int getItemResourceId() {
        return R.layout.goods_description;
    }

    @Override
    public void bindData(int position, View convertView, ViewHolder holder) {
        GoodInfo goodInfo = data.get(position);
        ImageView goodIV = holder.findView(R.id.imageview1);
        TextView introContentTV = holder.findView(R.id.textview1);

        goodIV.setImageResource(goodInfo.getImageId());
        introContentTV.setText(goodInfo.getIntro());

    }
}
