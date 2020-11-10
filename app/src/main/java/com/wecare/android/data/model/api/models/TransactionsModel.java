package com.wecare.android.data.model.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionsModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("caregiver_id")
@Expose
private Integer caregiverId;
@SerializedName("model_id")
@Expose
private Integer modelId;
@SerializedName("model_ref")
@Expose
private Integer modelRef;
@SerializedName("payment_method")
@Expose
private Integer paymentMethod;
@SerializedName("debit_amount")
@Expose
private String debitAmount;
@SerializedName("credit_amount")
@Expose
private String creditAmount;
@SerializedName("total_earned")
@Expose
private String totalEarned;
@SerializedName("total_agent_earned")
@Expose
private String totalAgentEarned;
@SerializedName("total_cash_paid")
@Expose
private String totalCashPaid;
@SerializedName("total_creditcard_paid")
@Expose
private String totalCreditcardPaid;
@SerializedName("settlement_caregiver_balance")
@Expose
private String settlementCaregiverBalance;
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
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("country_id")
@Expose
private Integer countryId;
@SerializedName("date")
@Expose
private String date;
@SerializedName("description")
@Expose
private String description;
@SerializedName("comment")
@Expose
private String comment;
@SerializedName("param")
@Expose
private String param;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getCaregiverId() {
return caregiverId;
}

public void setCaregiverId(Integer caregiverId) {
this.caregiverId = caregiverId;
}

public Integer getModelId() {
return modelId;
}

public void setModelId(Integer modelId) {
this.modelId = modelId;
}

public Integer getModelRef() {
return modelRef;
}

public void setModelRef(Integer modelRef) {
this.modelRef = modelRef;
}

public Integer getPaymentMethod() {
return paymentMethod;
}

public void setPaymentMethod(Integer paymentMethod) {
this.paymentMethod = paymentMethod;
}

public String getDebitAmount() {
return debitAmount;
}

public void setDebitAmount(String debitAmount) {
this.debitAmount = debitAmount;
}

public String getCreditAmount() {
return creditAmount;
}

public void setCreditAmount(String creditAmount) {
this.creditAmount = creditAmount;
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

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public Integer getCountryId() {
return countryId;
}

public void setCountryId(Integer countryId) {
this.countryId = countryId;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getComment() {
return comment;
}

public void setComment(String comment) {
this.comment = comment;
}

public String getParam() {
return param;
}

public void setParam(String param) {
this.param = param;
}

}