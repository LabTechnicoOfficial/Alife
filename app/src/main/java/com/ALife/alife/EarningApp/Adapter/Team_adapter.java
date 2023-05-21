package com.ALife.alife.EarningApp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ALife.alife.EarningApp.Model.Team.Team_response;
import com.ALife.alife.R;

import java.util.List;

public class Team_adapter extends RecyclerView.Adapter<Team_adapter.AppViewholder>{
    private List<Team_response> teamList;

    public Team_adapter(List<Team_response> teamList) {
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public AppViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.earning_card_team, parent, false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewholder holder, int position) {
        Team_response response = teamList.get(position);
        holder.nameText.setText(response.getName());
      //  Log.d("name: ",response.getName());
        holder.phoneText.setText(response.getPhone());
        holder.mailText.setText(response.getMail());

    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        TextView nameText, phoneText, mailText;
        public AppViewholder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameTextID);
            phoneText = itemView.findViewById(R.id.phoneTextID);
            mailText = itemView.findViewById(R.id.mailTextID);
        }
    }
}
