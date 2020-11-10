package com.wecare.android.data.model.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.utils.AppConstants;

public class NotificationModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("owner_id")
@Expose
private Integer ownerId;
@SerializedName("sender_id")
@Expose
private Integer senderId;
@SerializedName("image")
@Expose
private String image;
@SerializedName("title_en")
@Expose
private String titleEn;
@SerializedName("title_ar")
@Expose
private String titleAr;
@SerializedName("message_en")
@Expose
private String messageEn;
@SerializedName("message_ar")
@Expose
private String messageAr;
@SerializedName("seen")
@Expose
private Integer seen;
@SerializedName("seen_at")
@Expose
private String seenAt;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;

int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Integer getOwnerId() {
return ownerId;
}

public void setOwnerId(Integer ownerId) {
this.ownerId = ownerId;
}

public Integer getSenderId() {
return senderId;
}

public void setSenderId(Integer senderId) {
this.senderId = senderId;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getTitleEn() {
    return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? titleEn : titleAr;
}

public void setTitleEn(String titleEn) {
this.titleEn = titleEn;
}

public String getTitleAr() {
return titleAr;
}

public void setTitleAr(String titleAr) {
this.titleAr = titleAr;
}

public String getMessageEn() {
    return AppConstants.CURRENT_LOCALE.equals(AppConstants.LANGUAGE_LOCALE_ENGLISH) ? messageEn : messageAr;
}

public void setMessageEn(String messageEn) {
this.messageEn = messageEn;
}

public String getMessageAr() {
return messageAr;
}

public void setMessageAr(String messageAr) {
this.messageAr = messageAr;
}

public Integer getSeen() {
return seen;
}

public void setSeen(Integer seen) {
this.seen = seen;
}

public String getSeenAt() {
return seenAt;
}

public void setSeenAt(String seenAt) {
this.seenAt = seenAt;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getUpdatedAt() {
return updatedAt;
}

public void setUpdatedAt(String updatedAt) {
this.updatedAt = updatedAt;
}

}