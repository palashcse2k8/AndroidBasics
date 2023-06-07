package com.example.androidbasics.FormC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FormCViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> countryName = new MutableLiveData<>();
    private MutableLiveData<String> currencyName = new MutableLiveData<>();
    private MutableLiveData<String> purpose = new MutableLiveData<>();
    private MutableLiveData<String> bankName = new MutableLiveData<>();

    public LiveData<String> getName () {
        return name;
    }

    public LiveData<String> getAddress () {
        return address;
    }

    public LiveData<String> getCountry() {
        return countryName;
    }

    public LiveData<String> getCurrency () {
        return currencyName;
    }

    public LiveData<String> getBankName () {
        return bankName;
    }

    public LiveData<String> getPurpose () {
        return purpose;
    }

    public void setName(MutableLiveData<String> name) {
        this.name = name;
    }
    public void setAddress(MutableLiveData<String> address) {
        this.address = address;
    }
    public void setCountryName(MutableLiveData<String> countryName) {
        this.countryName = countryName;
    }
    public void setCurrencyName(MutableLiveData<String> currencyName) {
        this.currencyName = currencyName;
    }
    public void setPurpose(MutableLiveData<String> purpose) {
        this.purpose = purpose;
    }
    public void setBankName(MutableLiveData<String> bankName) {
        this.bankName = bankName;
    }
}
