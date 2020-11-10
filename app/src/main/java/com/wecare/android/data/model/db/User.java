package com.wecare.android.data.model.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by amitshekhar on 07/07/17.
 */
@Entity(tableName = "users")
public class User {

    @PrimaryKey
    public Long id;

    public String name;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "updated_at")
    public String updatedAt;

}
