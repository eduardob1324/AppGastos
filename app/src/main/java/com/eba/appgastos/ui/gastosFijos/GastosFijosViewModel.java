package com.eba.appgastos.ui.gastosFijos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GastosFijosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GastosFijosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}