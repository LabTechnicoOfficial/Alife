package com.alifew.alife.EarningApp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alifew.alife.EarningApp.Model.Notice.Notice_repositories;
import com.alifew.alife.EarningApp.Model.Notice.Notice_response;

import java.util.List;

public class NoticeViewModel extends ViewModel {
   /* public NoticeViewModel(@NonNull Application application) {
        super(application);
    }*/

    public LiveData<List<Notice_response>> getData() {

        return Notice_repositories.getInstance().getMessage();

    }
}
