package com.wecare.android.data.model.api.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankInfoRequest {

@SerializedName("country")
@Expose
private String country;
@SerializedName("city")
@Expose
private String city;
@SerializedName("holder_name")
@Expose
private String holderName;
@SerializedName("bank_name")
@Expose
private String bankName;
@SerializedName("account_number")
@Expose
private String accountNumber;
@SerializedName("swift_code")
@Expose
private String swiftCode;
@SerializedName("iban_number")
@Expose
private String ibanNumber;
    @SerializedName("transit_no")
    @Expose
    private String transitno;
public String getCountry() {
return country;
}

public void setCountry(String country) {
this.country = country;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public String getHolderName() {
return holderName;
}

public void setHolderName(String holderName) {
this.holderName = holderName;
}

public String getBankName() {
return bankName;
}

public void setBankName(String bankName) {
this.bankName = bankName;
}

public String getAccountNumber() {
return accountNumber;
}

public void setAccountNumber(String accountNumber) {
this.accountNumber = accountNumber;
}

public String getSwiftCode() {
return swiftCode;
}

public void setSwiftCode(String swiftCode) {
this.swiftCode = swiftCode;
}

public String getIbanNumber() {
return ibanNumber;
}

public void setIbanNumber(String ibanNumber) {
this.ibanNumber = ibanNumber;
}

    public  String getTransitno(){
        return transitno;
    }

    public void  setTransitno(String transitno){
        this.transitno=transitno;
    }
}