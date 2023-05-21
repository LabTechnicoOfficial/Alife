package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.fetch_shop_admin_response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Shop_add_assistant_adapter extends RecyclerView.Adapter<Shop_add_assistant_adapter.AppViewholder>{
    LayoutInflater layoutInflater;
    private List<fetch_shop_admin_response> more_assistantList;
    int check=0;
    private OnItemCheckListener mListener;

    public Shop_add_assistant_adapter(List<fetch_shop_admin_response> more_assistantList,int check) {
        this.more_assistantList = more_assistantList;
        this.check=check;
    }

    @NonNull

    @Override
    public Shop_add_assistant_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_add_assistant_card, parent, false);
        return new Shop_add_assistant_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_add_assistant_adapter.AppViewholder holder, int position) {
        fetch_shop_admin_response assistant=more_assistantList.get(position);
        Picasso.get().load(assistant.getAgent_image()).into(holder.assistantImage);
        holder.assistantName.setText(assistant.getAgent_name());
        if (check == 1) {
            holder.checkBox.setChecked(true);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox c = (CheckBox) v;
                    c.setTag(assistant);
                    if (c.isChecked()) {
                        // Do your coding
                        //Toast.makeText(v.getContext(),"yess",Toast.LENGTH_SHORT).show();
                        //String x =
                        mListener.OnItemCheck(position, "yess");
                        //.categoryName.setText(x);

                        // showtypecount();
                    } else {
                        // Do your coding
                        //Toast.makeText(v.getContext(), "no", Toast.LENGTH_SHORT).show();
                       // String x =
                        mListener.OnItemCheck(position, "no");
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
                    c.setTag(assistant);
                    if (c.isChecked()) {
                        // Do your coding
                        //Toast.makeText(v.getContext(),"yess",Toast.LENGTH_SHORT).show();
                       // String x =
                                mListener.OnItemCheck(position, "yess");
                        //    holder.categoryName.setText(x);

                        // showtypecount();
                    } else {
                        // Do your coding
                        //Toast.makeText(v.getContext(), "no", Toast.LENGTH_SHORT).show();
                       // String x =
                        mListener.OnItemCheck(position, "no");
                        //  holder.categoryName.setText(x);

                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return more_assistantList.size();
    }

    public interface OnItemCheckListener {
        void OnItemCheck(int position, String status);
    }
    public void setOnItemClickListener(OnItemCheckListener listener) {
        mListener = listener;

    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView assistantName;
        CheckBox checkBox;
        de.hdodenhof.circleimageview.CircleImageView assistantImage;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            assistantImage = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.assistantImageID);
            assistantName = (TextView) itemView.findViewById(R.id.assistantNameID);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxID);

        }
    }
}
