package com.example.activityquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mIsAnswered;
    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }
    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
    public boolean getIsAnswered(){return mIsAnswered;}
    public void setIsAnswered(boolean isAnswered){mIsAnswered=isAnswered;}
}
