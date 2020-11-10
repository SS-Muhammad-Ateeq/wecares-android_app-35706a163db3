package com.wecare.android.data.model.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.models.CountryModel;

public class GiverWalletResponse extends BaseResponse {

@SerializedName("country_id")
@Expose
private Integer countryId;
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("grand_total")
@Expose
private String grandTotal;
@SerializedName("grand_total_caregiver_earned")
@Expose
private String grandTotalCaregiverEarned;
@SerializedName("grand_total_caregiver_cash")
@Expose
private String grandTotalCaregiverCash;
@SerializedName("grand_total_caregiver_online")
@Expose
private String grandTotalCaregiverOnline;
@SerializedName("grand_total_agent_earned")
@Expose
private String grandTotalAgentEarned;
@SerializedName("grand_total_agent_earned_cash")
@Expose
private String grandTotalAgentEarnedCash;
@SerializedName("grand_total_agent_earned_online")
@Expose
private String grandTotalAgentEarnedOnline;
@SerializedName("period_total")
@Expose
private String periodTotal;
@SerializedName("period_total_cash")
@Expose
private String periodTotalCash;
@SerializedName("period_total_online")
@Expose
private String periodTotalOnline;
@SerializedName("period_caregiver_total")
@Expose
private String periodCaregiverTotal;
@SerializedName("period_caregiver_total_cash")
@Expose
private String periodCaregiverTotalCash;
@SerializedName("period_caregiver_total_online")
@Expose
private String periodCaregiverTotalOnline;
@SerializedName("period_tax_total")
@Expose
private String periodTaxTotal;
@SerializedName("period_tax_total_cash")
@Expose
private String periodTaxTotalCash;
@SerializedName("period_tax_total_online")
@Expose
private String periodTaxTotalOnline;
@SerializedName("period_agent_total")
@Expose
private String periodAgentTotal;
@SerializedName("period_agent_total_cash")
@Expose
private String periodAgentTotalCash;
@SerializedName("period_agent_total_online")
@Expose
private String periodAgentTotalOnline;
@SerializedName("period_agent_grand_total")
@Expose
private String periodAgentGrandTotal;
@SerializedName("period_agent_grand_total_cash")
@Expose
private String periodAgentGrandTotalCash;
@SerializedName("period_agent_grand_total_online")
@Expose
private String periodAgentGrandTotalOnline;
@SerializedName("period_settlement_balance")
@Expose
private String periodSettlementBalance;
@SerializedName("country")
@Expose
private CountryModel country;
@SerializedName("old_balance")
@Expose
private String oldBalance;
@SerializedName("caregiver_balance_limit")
@Expose
private String caregiverBalanceLimit;

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

public String getGrandTotal() {
return grandTotal;
}

public void setGrandTotal(String grandTotal) {
this.grandTotal = grandTotal;
}

public String getGrandTotalCaregiverEarned() {
return grandTotalCaregiverEarned;
}

public void setGrandTotalCaregiverEarned(String grandTotalCaregiverEarned) {
this.grandTotalCaregiverEarned = grandTotalCaregiverEarned;
}

public String getGrandTotalCaregiverCash() {
return grandTotalCaregiverCash;
}

public void setGrandTotalCaregiverCash(String grandTotalCaregiverCash) {
this.grandTotalCaregiverCash = grandTotalCaregiverCash;
}

public String getGrandTotalCaregiverOnline() {
return grandTotalCaregiverOnline;
}

public void setGrandTotalCaregiverOnline(String grandTotalCaregiverOnline) {
this.grandTotalCaregiverOnline = grandTotalCaregiverOnline;
}

public String getGrandTotalAgentEarned() {
return grandTotalAgentEarned;
}

public void setGrandTotalAgentEarned(String grandTotalAgentEarned) {
this.grandTotalAgentEarned = grandTotalAgentEarned;
}

public String getGrandTotalAgentEarnedCash() {
return grandTotalAgentEarnedCash;
}

public void setGrandTotalAgentEarnedCash(String grandTotalAgentEarnedCash) {
this.grandTotalAgentEarnedCash = grandTotalAgentEarnedCash;
}

public String getGrandTotalAgentEarnedOnline() {
return grandTotalAgentEarnedOnline;
}

public void setGrandTotalAgentEarnedOnline(String grandTotalAgentEarnedOnline) {
this.grandTotalAgentEarnedOnline = grandTotalAgentEarnedOnline;
}

public String getPeriodTotal() {
return periodTotal;
}

public void setPeriodTotal(String periodTotal) {
this.periodTotal = periodTotal;
}

public String getPeriodTotalCash() {
return periodTotalCash;
}

public void setPeriodTotalCash(String periodTotalCash) {
this.periodTotalCash = periodTotalCash;
}

public String getPeriodTotalOnline() {
return periodTotalOnline;
}

public void setPeriodTotalOnline(String periodTotalOnline) {
this.periodTotalOnline = periodTotalOnline;
}

public String getPeriodCaregiverTotal() {
return periodCaregiverTotal;
}

public void setPeriodCaregiverTotal(String periodCaregiverTotal) {
this.periodCaregiverTotal = periodCaregiverTotal;
}

public String getPeriodCaregiverTotalCash() {
return periodCaregiverTotalCash;
}

public void setPeriodCaregiverTotalCash(String periodCaregiverTotalCash) {
this.periodCaregiverTotalCash = periodCaregiverTotalCash;
}

public String getPeriodCaregiverTotalOnline() {
return periodCaregiverTotalOnline;
}

public void setPeriodCaregiverTotalOnline(String periodCaregiverTotalOnline) {
this.periodCaregiverTotalOnline = periodCaregiverTotalOnline;
}

public String getPeriodTaxTotal() {
return periodTaxTotal;
}

public void setPeriodTaxTotal(String periodTaxTotal) {
this.periodTaxTotal = periodTaxTotal;
}

public String getPeriodTaxTotalCash() {
return periodTaxTotalCash;
}

public void setPeriodTaxTotalCash(String periodTaxTotalCash) {
this.periodTaxTotalCash = periodTaxTotalCash;
}

public String getPeriodTaxTotalOnline() {
return periodTaxTotalOnline;
}

public void setPeriodTaxTotalOnline(String periodTaxTotalOnline) {
this.periodTaxTotalOnline = periodTaxTotalOnline;
}

public String getPeriodAgentTotal() {
return periodAgentTotal;
}

public void setPeriodAgentTotal(String periodAgentTotal) {
this.periodAgentTotal = periodAgentTotal;
}

public String getPeriodAgentTotalCash() {
return periodAgentTotalCash;
}

public void setPeriodAgentTotalCash(String periodAgentTotalCash) {
this.periodAgentTotalCash = periodAgentTotalCash;
}

public String getPeriodAgentTotalOnline() {
return periodAgentTotalOnline;
}

public void setPeriodAgentTotalOnline(String periodAgentTotalOnline) {
this.periodAgentTotalOnline = periodAgentTotalOnline;
}

public String getPeriodAgentGrandTotal() {
return periodAgentGrandTotal;
}

public void setPeriodAgentGrandTotal(String periodAgentGrandTotal) {
this.periodAgentGrandTotal = periodAgentGrandTotal;
}

public String getPeriodAgentGrandTotalCash() {
return periodAgentGrandTotalCash;
}

public void setPeriodAgentGrandTotalCash(String periodAgentGrandTotalCash) {
this.periodAgentGrandTotalCash = periodAgentGrandTotalCash;
}

public String getPeriodAgentGrandTotalOnline() {
return periodAgentGrandTotalOnline;
}

public void setPeriodAgentGrandTotalOnline(String periodAgentGrandTotalOnline) {
this.periodAgentGrandTotalOnline = periodAgentGrandTotalOnline;
}

public String getPeriodSettlementBalance() {
return periodSettlementBalance;
}

public void setPeriodSettlementBalance(String periodSettlementBalance) {
this.periodSettlementBalance = periodSettlementBalance;
}

public CountryModel getCountry() {
return country;
}

public void setCountry(CountryModel country) {
this.country = country;
}

public String getOldBalance() {
return oldBalance;
}

public void setOldBalance(String oldBalance) {
this.oldBalance = oldBalance;
}

public String getCaregiverBalanceLimit() {
return caregiverBalanceLimit;
}

public void setCaregiverBalanceLimit(String caregiverBalanceLimit) {
this.caregiverBalanceLimit = caregiverBalanceLimit;
}

}