package com.example.androidbasics.FormC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FormCViewModel extends ViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    private MutableLiveData<String> countryName = new MutableLiveData<>();
    private MutableLiveData<String> currencyName = new MutableLiveData<>();
    private MutableLiveData<String> purpose = new MutableLiveData<>();
    private MutableLiveData<String> bankName = new MutableLiveData<>();
    private MutableLiveData<String> amount = new MutableLiveData<>();


    public LiveData<String> getName () {
        return name;
    }
    public LiveData<String> getAmount () {
        return amount;
    }

    public LiveData<String> getPhoneNumber () {
        return phoneNumber;
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

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setAmount(String amount) {
        this.amount.setValue(amount);
    }
    public void setAddress(String address) {
        this.address.setValue(address);
    }
    public void setCountryName(String countryName) {
        this.countryName.setValue(countryName);
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName.setValue(currencyName);
    }
    public void setPurpose(String purpose) {
        this.purpose.setValue(purpose);
    }
    public void setBankName(String bankName) {
        this.bankName.setValue(bankName);
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.setValue(phoneNumber);
    }
}
