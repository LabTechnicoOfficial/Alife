package com.alifew.alifeworld.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alifeworld.model.user_instruction_response;
import com.alifew.alifeworld.model.user_instraction_repositories;

import java.util.List;

public class User_instruction extends ViewModel {
    public LiveData<List<user_instruction_response>> getInstruction(String token) {
        return user_instraction_repositories.getInstance().getData(token);

    }
}
