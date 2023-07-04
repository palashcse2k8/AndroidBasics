package com.example.androidbasics.psrupload.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbasics.psrupload.models.CurrentUser;

//public class BitMapResource extends ViewModel {
//
//    private MutableLiveData<Bitmap> bitmap1 = new MutableLiveData();
//    private MutableLiveData<Bitmap> bitmap2 = new MutableLiveData();
//
//    public LiveData<Bitmap> getBitMap1 () {
//        return bitmap1;
//    }
//
//    public LiveData<Bitmap> getBitMap2 () {
//        return bitmap2;
//    }
//
//    public void setBitmap1(Bitmap bitmap) {
//        bitmap1.setValue(bitmap);
//    }
//    public void setBitmap2(Bitmap bitmap) {
//        bitmap2.setValue(bitmap);
//    }
//}

public class PSRViewModel extends ViewModel {

    private MutableLiveData<CurrentUser> user = new MutableLiveData<>(new CurrentUser());

    private MutableLiveData<Bitmap> bitmap1 = new MutableLiveData<>();

    public LiveData<Bitmap> getBitmap1 (){
        return bitmap1;
    }
    public void setBitMap1(Bitmap bitmap){
        bitmap1.setValue(bitmap);
    }

    private MutableLiveData<Bitmap> bitmap2 = new MutableLiveData<>();

    public LiveData<Bitmap> getBitmap2 (){
        return bitmap2;
    }
    public void setBitMap2(Bitmap bitmap){
        bitmap2.setValue(bitmap);
    }

    public LiveData<CurrentUser> getUser () {
        return user;
    }

    public void setUser(MutableLiveData<CurrentUser> user) {
        this.user = user;
    }

    public void setBitMap(Bitmap bitmap){
        user.getValue().setPsr(bitmap);
    }
}