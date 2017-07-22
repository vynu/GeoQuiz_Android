package com.bignerdranch.android.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mCheatStatus;

    public Question(int textResId, boolean answerTrue, boolean cheatStatus) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mCheatStatus = cheatStatus;
    }

    public boolean ismCheatStatus() {
        return mCheatStatus;
    }

    public void setmCheatStatus(boolean mCheatStatus) {
        this.mCheatStatus = mCheatStatus;
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
}
