package com.wecare.android.data.model.api.responses;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.CountryModel;

import ir.mirrajabi.searchdialog.core.Searchable;

public class WalletResponse extends BaseResponse implements Searchable {

@SerializedName("country_id")
@Expose
private Integer countryId;
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("total_earned")
@Expose
private String totalEarned="0";
@SerializedName("total_agent_earned")
@Expose
private String totalAgentEarned="0";
@SerializedName("total_cash_paid")
@Expose
private String totalCashPaid="0";
@SerializedName("total_creditcard_paid")
@Expose
private String totalCreditcardPaid="0";
@SerializedName("settlement_caregiver_balance")
@Expose
private String settlementCaregiverBalance="0";
@SerializedName("settlement_agent_balance")
@Expose
private String settlementAgentBalance;
@SerializedName("settlement_balance")
@Expose
private String settlementBalance;
@SerializedName("settlement_cash_paid")
@Expose
private String settlementCashPaid;
@SerializedName("settlement_creditcard_paid")
@Expose
private String settlementCreditcardPaid;
@SerializedName("country")
@Expose
private CountryModel country;
@SerializedName("settlement_total_amount")
@Expose
private Integer settlementTotalAmount;
@SerializedName("caregiver_balance_limit")
@Expose
private String caregiverBalanceLimit;

    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("total_paid")
    @Expose
    private String totalPaid="0";
    @SerializedName("total_paid_creditcard")
    @Expose
    private String totalPaidCreditcard="0";
    @SerializedName("total_paid_cash")
    @Expose
    private String totalPaidCash="0";
    @SerializedName("total_paid_wallet")
    @Expose
    private String totalPaidWallet="0";

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        this.totalPaid = totalPaid;
    }

    public String getTotalPaidCreditcard() {
        return totalPaidCreditcard;
    }

    public void setTotalPaidCreditcard(String totalPaidCreditcard) {
        this.totalPaidCreditcard = totalPaidCreditcard;
    }

    public String getTotalPaidCash() {
        return totalPaidCash;
    }

    public void setTotalPaidCash(String totalPaidCash) {
        this.totalPaidCash = totalPaidCash;
    }

    public String getTotalPaidWallet() {
        return totalPaidWallet;
    }

    public void setTotalPaidWallet(String totalPaidWallet) {
        this.totalPaidWallet = totalPaidWallet;
    }




public String getTotalEarned() {
return totalEarned;
}

public void setTotalEarned(String totalEarned) {
this.totalEarned = totalEarned;
}

public String getTotalAgentEarned() {
return totalAgentEarned;
}

public void setTotalAgentEarned(String totalAgentEarned) {
this.totalAgentEarned = totalAgentEarned;
}

public String getTotalCashPaid() {
return totalCashPaid;
}

public void setTotalCashPaid(String totalCashPaid) {
this.totalCashPaid = totalCashPaid;
}

public String getTotalCreditcardPaid() {
return totalCreditcardPaid;
}

public void setTotalCreditcardPaid(String totalCreditcardPaid) {
this.totalCreditcardPaid = totalCreditcardPaid;
}

public String getSettlementCaregiverBalance() {
return settlementCaregiverBalance;
}

public void setSettlementCaregiverBalance(String settlementCaregiverBalance) {
this.settlementCaregiverBalance = settlementCaregiverBalance;
}

public String getSettlementAgentBalance() {
return settlementAgentBalance;
}

public void setSettlementAgentBalance(String settlementAgentBalance) {
this.settlementAgentBalance = settlementAgentBalance;
}

public String getSettlementBalance() {
return settlementBalance;
}

public void setSettlementBalance(String settlementBalance) {
this.settlementBalance = settlementBalance;
}

public String getSettlementCashPaid() {
return settlementCashPaid;
}

public void setSettlementCashPaid(String settlementCashPaid) {
this.settlementCashPaid = settlementCashPaid;
}

public String getSettlementCreditcardPaid() {
return settlementCreditcardPaid;
}

public void setSettlementCreditcardPaid(String settlementCreditcardPaid) {
this.settlementCreditcardPaid = settlementCreditcardPaid;
}

public CountryModel getCountry() {
return country;
}

public void setCountry(CountryModel country) {
this.country = country;
}

public Integer getSettlementTotalAmount() {
return settlementTotalAmount;
}

public void setSettlementTotalAmount(Integer settlementTotalAmount) {
this.settlementTotalAmount = settlementTotalAmount;
}

public String getCaregiverBalanceLimit() {
return caregiverBalanceLimit;
}

public void setCaregiverBalanceLimit(String caregiverBalanceLimit) {
this.caregiverBalanceLimit = caregiverBalanceLimit;
}

    public WalletResponse() {
    }

    @Override
    public String getTitle() {
        return country.getTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.countryId);
        dest.writeString(this.currency);
        dest.writeString(this.totalEarned);
        dest.writeString(this.totalAgentEarned);
        dest.writeString(this.totalCashPaid);
        dest.writeString(this.totalCreditcardPaid);
        dest.writeString(this.settlementCaregiverBalance);
        dest.writeString(this.settlementAgentBalance);
        dest.writeString(this.settlementBalance);
        dest.writeString(this.settlementCashPaid);
        dest.writeString(this.settlementCreditcardPaid);
        dest.writeParcelable(this.country, flags);
        dest.writeValue(this.settlementTotalAmount);
        dest.writeString(this.caregiverBalanceLimit);
    }

    protected WalletResponse(Parcel in) {
        super(in);
        this.countryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.currency = in.readString();
        this.totalEarned = in.readString();
        this.totalAgentEarned = in.readString();
        this.totalCashPaid = in.readString();
        this.totalCreditcardPaid = in.readString();
        this.settlementCaregiverBalance = in.readString();
        this.settlementAgentBalance = in.readString();
        this.settlementBalance = in.readString();
        this.settlementCashPaid = in.readString();
        this.settlementCreditcardPaid = in.readString();
        this.country = in.readParcelable(CountryModel.class.getClassLoader());
        this.settlementTotalAmount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.caregiverBalanceLimit = in.readString();
    }

    public static final Creator<WalletResponse> CREATOR = new Creator<WalletResponse>() {
        @Override
        public WalletResponse createFromParcel(Parcel source) {
            return new WalletResponse(source);
        }

        @Override
        public WalletResponse[] newArray(int size) {
            return new WalletResponse[size];
        }
    };
}