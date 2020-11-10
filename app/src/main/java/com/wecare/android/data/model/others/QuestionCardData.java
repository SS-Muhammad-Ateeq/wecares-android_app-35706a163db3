package com.wecare.android.data.model.others;

import com.wecare.android.data.model.db.Option;
import com.wecare.android.data.model.db.Question;

import java.util.List;

/**
 * Created by amitshekhar on 09/07/17.
 */

public class QuestionCardData {

    public Question question;
    public List<Option> options;
    public boolean mShowCorrectOptions;

    public QuestionCardData(Question question, List<Option> options) {
        this.question = question;
        this.options = options;
    }
}
