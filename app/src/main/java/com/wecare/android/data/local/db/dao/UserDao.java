package com.wecare.android.data.local.db.dao;

import androidx.room.*;
import com.wecare.android.data.model.db.User;

import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> loadAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<User> loadAllByIds(List<Integer> userIds);

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    User findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Delete
    void delete(User user);

}
