package com.wecare.android.data.local.db;

import com.wecare.android.data.model.db.Option;
import com.wecare.android.data.model.db.Question;
import com.wecare.android.data.model.db.User;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {

    Observable<Boolean> insertUser(final User user);

    Observable<List<User>> getAllUsers();

    Observable<List<Question>> getAllQuestions();

    Observable<List<Option>> getOptionsForQuestionId(Long questionId);

    Observable<Boolean> isQuestionEmpty();

    Observable<Boolean> isOptionEmpty();

    Observable<Boolean> saveQuestion(Question question);

    Observable<Boolean> saveOption(Option option);

    Observable<Boolean> saveQuestionList(List<Question> questionList);

    Observable<Boolean> saveOptionList(List<Option> optionList);

}
