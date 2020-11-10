package com.wecare.android.data.model.api.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageContentResponse {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("country_id")
@Expose
private String countryId;
@SerializedName("category")
@Expose
private String category;
@SerializedName("slug")
@Expose
private String slug;
@SerializedName("title")
@Expose
private String title;
@SerializedName("active")
@Expose
private Integer active;
@SerializedName("language")
@Expose
private String language;
@SerializedName("url")
@Expose
private String url;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getCountryId() {
return countryId;
}

public void setCountryId(String countryId) {
this.countryId = countryId;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
}

public String getSlug() {
return slug;
}

public void setSlug(String slug) {
this.slug = slug;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public Integer getActive() {
return active;
}

public void setActive(Integer active) {
this.active = active;
}

public String getLanguage() {
return language;
}

public void setLanguage(String language) {
this.language = language;
}

public String getUrl() {
return url;
}

public void setUrl(String url) {
this.url = url;
}

}