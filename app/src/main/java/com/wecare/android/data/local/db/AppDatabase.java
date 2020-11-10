package com.wecare.android.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.wecare.android.data.local.db.dao.OptionDao;
import com.wecare.android.data.local.db.dao.QuestionDao;
import com.wecare.android.data.local.db.dao.UserDao;
import com.wecare.android.data.model.db.Option;
import com.wecare.android.data.model.db.Question;
import com.wecare.android.data.model.db.User;

@Database(entities = {User.class, Question.class, Option.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract QuestionDao questionDao();

    public abstract OptionDao optionDao();

}
