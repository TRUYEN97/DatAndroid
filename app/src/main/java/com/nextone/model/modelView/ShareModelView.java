package com.nextone.model.modelView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.nextone.model.input.CarModel;
import com.nextone.model.input.yard.YardModel;
import com.nextone.model.modelTest.process.TestDataViewModel;

import lombok.Getter;


@Getter
public class ShareModelView {

    private static volatile ShareModelView instance;

    private final MutableLiveData<Fragment> subScreenFragment;

    private final MutableLiveData<CarModel> carModelMutableLiveData;

    private final MutableLiveData<YardModel> yardModelMutableLiveData;

    private final MutableLiveData<TestDataViewModel> testDataViewModel;

    private final MutableLiveData<UserInfo> userInfo;

    private ShareModelView(){
        this.subScreenFragment = new MutableLiveData<>();
        this.carModelMutableLiveData = new MutableLiveData<>();
        this.yardModelMutableLiveData = new MutableLiveData<>();
        this.testDataViewModel = new MutableLiveData<>();
        this.userInfo = new MutableLiveData<>();
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

    public void setCarModelMutableLiveData(CarModel model){
        carModelMutableLiveData.setValue(model);
    }

    public void postCarModel(CarModel model){
        carModelMutableLiveData.postValue(model);
    }

    public void setSubScreenFragment(Fragment fragment){
        subScreenFragment.setValue(fragment);
    }

    public void setYardModelMutableLiveData(YardModel model){
        yardModelMutableLiveData.setValue(model);
    }

    public void portYardModelMutableLiveData(YardModel model){
        yardModelMutableLiveData.postValue(model);
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
    public void postUserInfoLiveData(UserInfo userInfo){
        this.userInfo.postValue(userInfo);
    }

    public void setUserInfoLiveData(UserInfo userInfo){
        this.userInfo.setValue(userInfo);
    }

    public void postSubScreenFragment(Fragment fragment){
        subScreenFragment.postValue(fragment);
    }

}
