package com.ALife.alife.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.R;
import com.ALife.alife.model.user_instruction_response;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Instruction_adapter extends RecyclerView.Adapter<Instruction_adapter.AppViewholder> {
    private Instruction_adapter.OnItemClickListener mListener;
    List<user_instruction_response> instructionList;

    public Instruction_adapter(List<user_instruction_response> instructionList) {
        this.instructionList = instructionList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.instruction_card, parent, false);
        return new Instruction_adapter.AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        user_instruction_response response = instructionList.get(position);
        holder.instructionText.setText(response.getTitle());
        try{
            Picasso.get().load(response.getImage()).into(holder.instructionImage);
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnClickListener(Instruction_adapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {

        TextView instructionText;
        ImageView instructionImage;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            instructionText = itemView.findViewById(R.id.instructionTextID);
            instructionImage = itemView.findViewById(R.id.instructionImageID);
            itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.OnItemClick(position);
                    }
                }
            });
        }
    }
}
