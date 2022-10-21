package com.example.activityquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private ImageButton previousIcon;
    private ImageButton nextIcon;
    private int correct_questions=0;
    private int answered_questions=0;


    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_brasil, false),
            new Question(R.string.question_croatia, true),
            new Question(R.string.question_france, false),
            new Question(R.string.question_nigeria, true),
            new Question(R.string.question_japan, false),
            new Question(R.string.question_uruguay, true),
    };
    private int mCurrentIndex = 0;

    private static final String KEY_INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);


        // True button
        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        // False button
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });


        // Previous question Icon
        previousIcon = findViewById(R.id.previous);
        previousIcon.setVisibility(View.INVISIBLE); // make the icon invisible at the first question
        previousIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Next question Icon
        nextIcon = findViewById(R.id.next);
        nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        checkIfAnswered(mCurrentIndex);
        updateVisibility();

    }

    private void checkAnswer(boolean userPressed) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (userPressed == answerIsTrue) {
            messageResId = R.string.correct_toast;
            correct_questions++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        mQuestionBank[mCurrentIndex].setIsAnswered(true);

        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);

        answered_questions++;

        checkIfLast();
    }


    // Check if all the questions are answered,if are,ends the quiz
    private void checkIfLast(){
        if(answered_questions==mQuestionBank.length){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GAME OVER");
            builder.setMessage("CORRECTS: " + correct_questions);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    // Check if the question has already been answered
    private void checkIfAnswered(int mCurrentIndex){
        boolean isAnswered = mQuestionBank[mCurrentIndex].getIsAnswered();
        if (isAnswered==true){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
        else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }


    // Depending the index of the question make visible or not the icons
    private void updateVisibility(){
        if(mCurrentIndex==0){
            previousIcon.setVisibility(View.INVISIBLE);
        }else if(mCurrentIndex==mQuestionBank.length - 1){
            nextIcon.setVisibility(View.INVISIBLE);
        }
        else {
            previousIcon.setVisibility(View.VISIBLE);
            nextIcon.setVisibility(View.VISIBLE);
        }
    }
}