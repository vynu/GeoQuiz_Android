package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "saveIndex";

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private boolean ismAnswerShown;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        if(savedInstanceState != null){
            Log.d(TAG,"this is saveInstanceState in onCreate of cheat activity");
            ismAnswerShown = savedInstanceState.getBoolean(KEY_INDEX,false);
            if(ismAnswerShown)
                displayAnswer();
            else
                mAnswerTextView.setText("");
        }

        Log.d(TAG,"this is onCreate from cheatactivity");



        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAnswer();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedIntanceState){
        super.onSaveInstanceState(savedIntanceState);
        Log.i(TAG,"onSaveInstance from cheat activity class");
        savedIntanceState.putBoolean(KEY_INDEX,ismAnswerShown);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    private void displayAnswer(){
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        if (mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        ismAnswerShown = true;
        setAnswerShownResult(ismAnswerShown);
    }
}
