package com.alifew.alifeworld.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alifew.alifeworld.R;
import com.alifew.alifeworld.Utils.ImageHelper;
import com.alifew.alifeworld.model.fetch_shop_admin_response;

import java.util.List;

public class Shop_assistant_adapter extends RecyclerView.Adapter<Shop_assistant_adapter.AppViewholder>{
    private LayoutInflater layoutInflater;
    private List<fetch_shop_admin_response> assistantList;
    private OnItemRemoveListener mListener;

    public Shop_assistant_adapter(List<fetch_shop_admin_response> assistantList) {
        this.assistantList = assistantList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_assistants_card, parent, false);
        return new Shop_assistant_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        fetch_shop_admin_response assistant=assistantList.get(position);

        ImageHelper.imageLoader(holder.itemView.getContext(),  holder.adminImage, assistant.getAgent_image());
        holder.adminName.setText(assistant.getAgent_name());

    }

    @Override
    public int getItemCount() {
        return assistantList.size();
    }

    public interface OnItemRemoveListener {
        void OnItemRemoveAssistant(int position);
    }
    public void setOnClickListener(OnItemRemoveListener listener) {
        mListener = listener;


    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView adminImage;
        TextView adminName;
        ImageView deleteButton;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            adminImage = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.adminImageID);
            adminName = (TextView) itemView.findViewById(R.id.adminNameID);
            deleteButton = (ImageView) itemView.findViewById(R.id.deleteID);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.OnItemRemoveAssistant(position);
                        }
                    }
                }
            });
        }
    }
}
