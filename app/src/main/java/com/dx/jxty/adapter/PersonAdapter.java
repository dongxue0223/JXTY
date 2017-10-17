package com.dx.jxty.adapter;

import android.content.Context;

import com.dx.jxty.R;
import com.dx.jxty.bean.Person;
import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;

import java.util.List;

/**
 * Created by dongxue on 2017/8/28.
 */

public class PersonAdapter extends SuperBaseAdapter<Person> {
    public PersonAdapter(Context context, List<Person> data) {
        super(context, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Person item, int position) {
        holder.setText(R.id.tv_item_name, item.getName())
                .setText(R.id.tv_item_age, item.getAge())
                .setText(R.id.tv_item_gender, item.getGender());
    }

    @Override
    protected int getItemViewLayoutId(int position, Person item) {
        return R.layout.item_person;
    }
}
