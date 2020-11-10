package com.wecare.android.data.local;

import android.os.Parcel;
import com.wecare.android.data.model.api.responses.BaseResponse;

/**
 * Created by Mihyar on 3/15/2019
 */

public class InformationAttachmentObj extends BaseResponse {


    /**
     * user_id : 46
     * image : Dlfp27BA.png
     * file_type : image/png
     * id : 65
     * url : http://stg.wecare-app.org:88/order-images/get-image-by-name?s=46&i=65&g=Dlfp27BA.png
     */

    private int user_id;
    private String image;
    private String file_type;
    private int id;
    private String url;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InformationAttachmentObj() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.user_id);
        dest.writeString(this.image);
        dest.writeString(this.file_type);
        dest.writeInt(this.id);
        dest.writeString(this.url);
    }

    protected InformationAttachmentObj(Parcel in) {
        super(in);
        this.user_id = in.readInt();
        this.image = in.readString();
        this.file_type = in.readString();
        this.id = in.readInt();
        this.url = in.readString();
    }

    public static final Creator<InformationAttachmentObj> CREATOR = new Creator<InformationAttachmentObj>() {
        @Override
        public InformationAttachmentObj createFromParcel(Parcel source) {
            return new InformationAttachmentObj(source);
        }

        @Override
        public InformationAttachmentObj[] newArray(int size) {
            return new InformationAttachmentObj[size];
        }
    };
}
