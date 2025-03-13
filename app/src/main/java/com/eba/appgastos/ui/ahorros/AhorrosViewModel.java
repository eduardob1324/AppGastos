package com.eba.appgastos.ui.ahorros;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AhorrosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AhorrosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}