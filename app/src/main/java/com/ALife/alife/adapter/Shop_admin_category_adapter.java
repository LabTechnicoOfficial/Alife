package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.Category_response;

import java.util.List;

public class Shop_admin_category_adapter extends RecyclerView.Adapter<Shop_admin_category_adapter.AppViewholder> {
    LayoutInflater layoutInflater;
    List<Category_response> categoryList;
    OnItemCheckListener mListener;
    int check=0;

    public Shop_admin_category_adapter(List<Category_response> categoryList, int check) {
        this.categoryList = categoryList;
        this.check = check;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_admin_categories_card, parent, false);
        return new Shop_admin_category_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Category_response category = categoryList.get(position);
        holder.categoryName.setText(category.getCatagory01y_name());
        if (check == 1) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox c = (CheckBox) v;
                    c.setTag(category);
                    if (c.isChecked()) {
                        // Do your coding
                        //Toast.makeText(v.getContext(),"yess",Toast.LENGTH_SHORT).show();
                        String x = mListener.OnItemCheck(position, "yess");
                        //.categoryName.setText(x);

                        // showtypecount();
                    } else {
                        // Do your coding
                        //Toast.makeText(v.getContext(), "no", Toast.LENGTH_SHORT).show();
                        String x = mListener.OnItemCheck(position, "no");
                        //holder.categoryName.setText(x);

                    }
                }
            });

        }  else {
            holder.checkBox.setChecked(false);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox c = (CheckBox) v;
                    c.setTag(category);
                    if (c.isChecked()) {
                        // Do your coding
                        //Toast.makeText(v.getContext(),"yess",Toast.LENGTH_SHORT).show();
                        String x = mListener.OnItemCheck(position, "yess");
                   //    holder.categoryName.setText(x);

                        // showtypecount();
                    } else {
                        // Do your coding
                        //Toast.makeText(v.getContext(), "no", Toast.LENGTH_SHORT).show();
                        String x = mListener.OnItemCheck(position, "no");
                      //  holder.categoryName.setText(x);

                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface OnItemCheckListener {
        String OnItemCheck(int position, String status);
    }

    public void setOnCheckedChangeListener(OnItemCheckListener listener) {
        mListener = listener;

    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView categoryName;
        CheckBox checkBox;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxID);
            categoryName = (TextView) itemView.findViewById(R.id.categoryNameID);

        }
    }
}
