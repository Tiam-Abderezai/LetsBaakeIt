package com.example.letsbakeit.fragments;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.letsbakeit.model.Step;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Step> selected = new MutableLiveData<>();
    public void setSelected(Step step) {
        selected.setValue(step);
    }

    public MutableLiveData<Step> getSelected() {
        return selected;
    }

}
