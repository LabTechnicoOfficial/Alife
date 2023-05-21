package com.ALife.alife.EarningApp.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ALife.alife.EarningApp.Adapter.Team_adapter;
import com.ALife.alife.EarningApp.Model.Team.Team_response;
import com.ALife.alife.EarningApp.ViewModel.TeamViewModel;
import com.ALife.alife.R;

import java.util.ArrayList;
import java.util.List;


public class My_team_fragment extends Fragment {

    String userID;
    ImageView backButton;
    RecyclerView teamView;
    TeamViewModel teamViewModel;
    private List<Team_response> teamList;
    Team_adapter adapter;

    public My_team_fragment(String userID) {
        this.userID = userID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main();
    }

    private void main() {
        teamViewModel.getData(userID).observe(getViewLifecycleOwner(), new Observer<List<Team_response>>() {
            @Override
            public void onChanged(List<Team_response> team_responses) {
                teamList = new ArrayList<>();
                teamList = team_responses;
                adapter = new Team_adapter(teamList);
                //noteAdapter.setOnClickListener(Home_fragment.this::OnItemClick, Home_fragment.this::OnItemDelete);
                teamView.setAdapter(adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_my_team_fragment, container, false);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.fade_out// popExit
        ).replace(R.id.frame_container, new Home_fragment(userID)).commit());

        teamView = (RecyclerView) view.findViewById(R.id.teamViewId);
        teamView.setHasFixedSize(true);
        teamView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}