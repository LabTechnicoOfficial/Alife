package com.alifew.bcopay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.bcopay.R;
import com.alifew.bcopay.model.get_local_business_title_response;

import java.util.List;

public class Shop_local_page_adapter extends RecyclerView.Adapter<Shop_local_page_adapter.AppViewholder> {
    private List<get_local_business_title_response> data;
    private onItemClickListener listener;
    public Shop_local_page_adapter(List<get_local_business_title_response> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Shop_local_page_adapter.AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_local_page_card, parent, false);
        return new Shop_local_page_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Shop_local_page_adapter.AppViewholder holder, int position) {
        get_local_business_title_response title = data.get(position);
        holder.titleText.setText(title.getTitle());
    }
    public void setOnClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView titleText;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
