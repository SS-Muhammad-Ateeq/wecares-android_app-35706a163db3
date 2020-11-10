package com.wecare.android.data.model.api.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.wecare.android.data.model.api.responses.BaseResponse;

public class AttachmentModel extends BaseResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("category")
    @Expose
    private String category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public AttachmentModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(this.id);
        dest.writeValue(this.userId);
        dest.writeString(this.title);
        dest.writeString(this.file);
        dest.writeString(this.fileType);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeValue(this.status);
        dest.writeValue(this.categoryId);
        dest.writeString(this.url);
        dest.writeString(this.category);
    }

    protected AttachmentModel(Parcel in) {
        super(in);
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.file = in.readString();
        this.fileType = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.url = in.readString();
        this.category = in.readString();
    }

    public static final Creator<AttachmentModel> CREATOR = new Creator<AttachmentModel>() {
        @Override
        public AttachmentModel createFromParcel(Parcel source) {
            return new AttachmentModel(source);
        }

        @Override
        public AttachmentModel[] newArray(int size) {
            return new AttachmentModel[size];
        }
    };
}