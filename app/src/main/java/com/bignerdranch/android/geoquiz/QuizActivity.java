package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEAT_STATUS = "cheatStatus";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;
    private boolean cheatArray[] = {false,false,false,false,false,false} ;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true, cheatArray[0]),
            new Question(R.string.question_oceans, true, cheatArray[1]),
            new Question(R.string.question_mideast, false, cheatArray[2]),
            new Question(R.string.question_africa, false, cheatArray[3]),
            new Question(R.string.question_americas, true, cheatArray[4]),
            new Question(R.string.question_asia, true, cheatArray[5]),
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstance state");
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            boolean temp[] = savedInstanceState.getBooleanArray(CHEAT_STATUS);
            for(int i=0;i<temp.length;i++){

                mQuestionBank[i].setmCheatStatus(temp[i]);
            }
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });


        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                updateQuestion();
                ButtonVisibility();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
                ButtonVisibility();
            }
        });

        ButtonVisibility();

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG,"activityresult");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }


            cheatArray[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setmCheatStatus(cheatArray[mCurrentIndex]);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        for(int i=0;i<mQuestionBank.length;i++){
            cheatArray[i] = mQuestionBank[i].ismCheatStatus();
        }
        savedInstanceState.putBooleanArray(CHEAT_STATUS, cheatArray);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        boolean isCheated = mQuestionBank[mCurrentIndex].ismCheatStatus();
        int messageResId = 0;

        if (isCheated) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    private void ButtonVisibility(){
        if(mCurrentIndex == 0)
            mPrevButton.setVisibility(View.GONE);
        else if(mCurrentIndex == mQuestionBank.length-1)
            mNextButton.setVisibility(View.GONE);
        else{
            mPrevButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
        }
    }
}
