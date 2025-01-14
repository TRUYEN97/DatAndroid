package com.nextone.model.modelView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.nextone.model.input.CarModel;
import com.nextone.model.modelTest.process.TestDataViewModel;

import lombok.Getter;


public class ShareModelView {

    private static volatile ShareModelView instance;

    @Getter
    private final MutableLiveData<Fragment> subScreenFragment;

    @Getter
    private final MutableLiveData<CarModel> carModel;

    @Getter
    private final MutableLiveData<TestDataViewModel> testDataViewModel;

    private ShareModelView(){
        this.subScreenFragment = new MutableLiveData<>();
        this.carModel = new MutableLiveData<>();
        this.testDataViewModel = new MutableLiveData<>();
        this.subScreenFragment.setValue(null);
    }

    public static ShareModelView getInstance(){
        if(instance == null){
            synchronized (ShareModelView.class) {
                if (instance == null) {
                    instance = new ShareModelView();
                }
            }
        }
        return instance;
    }

    public void setCarModel(CarModel model){
        carModel.setValue(model);
    }

    public void postCarModel(CarModel model){
        carModel.postValue(model);
    }

    public void setSubScreenFragment(Fragment fragment){
        subScreenFragment.setValue(fragment);
    }

    public Fragment getSubScreenFragmentValue(){
        return subScreenFragment.getValue();
    }

    public void setTestDataViewModel(TestDataViewModel model){
        testDataViewModel.setValue(model);
    }

    public void postTestDataViewModel(TestDataViewModel model){
        testDataViewModel.postValue(model);
    }

    public void postSubScreenFragment(Fragment fragment){
        subScreenFragment.postValue(fragment);
    }

}
