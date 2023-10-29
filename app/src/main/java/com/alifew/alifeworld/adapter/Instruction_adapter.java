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
import com.alifew.alifeworld.model.user_instruction_response;

import java.util.List;

public class Instruction_adapter extends RecyclerView.Adapter<Instruction_adapter.AppViewholder> {

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

        ImageHelper.imageLoader(holder.instructionImage.getContext(), holder.instructionImage, response.getImage());
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnInstructorItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class AppViewholder extends RecyclerView.ViewHolder {

        TextView instructionText;
        ImageView instructionImage;

        public AppViewholder(@NonNull View itemView) {
            super(itemView);

            instructionText = itemView.findViewById(R.id.instructionText);
            instructionImage = itemView.findViewById(R.id.instructionImageID);
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.OnInstructorItemClick(position);
                    }
                }
            });
        }
    }
}
