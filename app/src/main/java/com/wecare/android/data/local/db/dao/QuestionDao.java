package com.wecare.android.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.wecare.android.data.model.db.Question;

import java.util.List;

/**
 * Created by amitshekhar on 08/07/17.
 */
@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions")
    List<Question> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> questions);

}
