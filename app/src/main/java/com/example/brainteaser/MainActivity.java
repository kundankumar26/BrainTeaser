package com.example.brainteaser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    /**
    * Declaring all the Buttons and Textviews
    */
    Button startButton;
    TextView timerTextview;
    TextView questionsTextview;
    TextView scoreTextview;
    TextView answerDisplayTextview;
    Button choiceButton1;
    Button choiceButton2;
    Button choiceButton3;
    Button choiceButton4;
    Button playAgainButton;
    GridLayout gridLayout;
    int correctAnswerButtonNumber;
    int correctAnswers;
    int totalQuestions;
    CountDownTimer timer;
    boolean isGameActive = true;

    ArrayList<Integer> answerArray = new ArrayList<Integer>();

    Random rand = new Random();

    public void startBrainTeaserButton(View view){
        startButton.setVisibility(View.INVISIBLE);
        generateQuestions();
        startTimer();
    }

    @SuppressLint("SetTextI18n")
    private void generateQuestions() {
        answerArray.clear();

        int a = rand.nextInt(21);
        int b = rand.nextInt(41);

        questionsTextview.setText(Integer.toString(a) + " + " + Integer.toString(b));
        correctAnswerButtonNumber = rand.nextInt(4);

        int incorrectAnswer;

        for(int i = 0; i < 4; i++){
            if(i == correctAnswerButtonNumber){
                answerArray.add(a + b);
            }
            else {
                incorrectAnswer = rand.nextInt(41);
                while(incorrectAnswer == a + b){
                    incorrectAnswer = rand.nextInt(41);
                }
                answerArray.add(incorrectAnswer);
            }
        }

        choiceButton1.setText(answerArray.get(0).toString());
        choiceButton2.setText(answerArray.get(1).toString());
        choiceButton3.setText(answerArray.get(2).toString());
        choiceButton4.setText(answerArray.get(3).toString());

        updateScores();

    }

    private void updateScores() {
        String score = correctAnswers + "/" + totalQuestions;
        scoreTextview.setText(score);
    }

    public void chooseAnswer(View view) {
        if(!isGameActive){
            return;
        }
        if(view.getTag().toString().equals(Integer.toString(correctAnswerButtonNumber))){
            correctAnswers++;
            //btn.setBackgroundColor(getResources().getColor(R.color.correct));
            //view.setBackgroundColor(getResources().getColor(R.color.correct));
            answerDisplayTextview.setText(R.string.correct_answer);
        }
        else {
            answerDisplayTextview.setText(R.string.wrong_answer);
        }
        totalQuestions++;
        //view.setBackgroundColor(getResources().getColor(R.color.default_button_color));
        generateQuestions();
    }

    @SuppressLint("SetTextI18n")
    public void startTimer() {
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTextview.setText(Integer.toString((int) (millisUntilFinished / 1000)) + "s");
            }

            public void onFinish() {
                timerTextview.setText("Time's Up");
                answerDisplayTextview.setText("Your Score: " + correctAnswers);
                questionsTextview.setText(" ");
                isGameActive = false;
                playAgainButton.setVisibility(View.VISIBLE);
                choiceButton1.setText("");
                choiceButton2.setText("");
                choiceButton3.setText("");
                choiceButton4.setText("");
            }
        }.start();
    }

    public void playAgain(View view) {
        isGameActive = true;
        totalQuestions = 0;
        correctAnswers = 0;
        answerDisplayTextview.setText(" ");
        scoreTextview.setText("0/0");
        playAgainButton.setVisibility(View.INVISIBLE);
        generateQuestions();
        startTimer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_button);
        timerTextview = findViewById(R.id.time_textview);
        questionsTextview = findViewById(R.id.questions_textview);
        scoreTextview = findViewById(R.id.score_textview);
        answerDisplayTextview = findViewById(R.id.answer_display_textview);
        choiceButton1 = findViewById(R.id.choice_button0);
        choiceButton2 = findViewById(R.id.choice_button1);
        choiceButton3 = findViewById(R.id.choice_button2);
        choiceButton4 = findViewById(R.id.choice_button3);
        playAgainButton = findViewById(R.id.play_again_button);
        gridLayout = findViewById(R.id.grid_layout);

        generateQuestions();
        startTimer();
    }

}