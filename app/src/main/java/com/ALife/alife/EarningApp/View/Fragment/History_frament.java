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


import com.ALife.alife.EarningApp.Adapter.History_adapter;
import com.ALife.alife.EarningApp.Model.Transaction.transactionHistory_response;
import com.ALife.alife.EarningApp.ViewModel.Transaction;
import com.ALife.alife.R;

import java.util.ArrayList;
import java.util.List;

public class History_frament extends Fragment {

    ImageView backButton;
    String userID;
    RecyclerView historyView;
    Transaction transaction;
    private List<transactionHistory_response> historyList;
    History_adapter adapter;

    public History_frament(String userID) {
        this.userID = userID;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        main();
    }

    private void main() {

        transaction.getHistory(userID, "abc**def").observe(getViewLifecycleOwner(), new Observer<List<transactionHistory_response>>() {
            @Override
            public void onChanged(List<transactionHistory_response> transactionHistory_responses) {
                historyList = new ArrayList<>();
                historyList = transactionHistory_responses;
                adapter = new History_adapter(historyList);
                historyView.setAdapter(adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.earning_history_frament, container, false);
        transaction = new ViewModelProvider(this).get(Transaction.class);

        backButton = (ImageView) view.findViewById(R.id.backButtonID);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.fade_in,  // enter
                R.anim.fade_out// popExit
        ).replace(R.id.frame_container, new Home_fragment(userID)).commit());

        historyView = (RecyclerView) view.findViewById(R.id.historyViewID);
        historyView.setHasFixedSize(true);
        historyView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}