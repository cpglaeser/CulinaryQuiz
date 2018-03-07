package com.example.android.culinaryquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    QuestionBank allQuestions = new QuestionBank();
    int questionNumber = -1;
    double score = 0;
    double scorePercentage = 0;
    String answerSelection = "";
    final int numberOfQuestions = allQuestions.list.size();

    LinearLayout buttonLayout, checkBoxLayout, radioButtonLayout;
    RelativeLayout editTextLayout, endText;
    Button submitButton;
    TextView questionText;
    EditText textEntry;
    RadioButton radioButtonA, radioButtonB, radioButtonC, radioButtonD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Views
        buttonLayout = findViewById(R.id.button_layout);
        checkBoxLayout = findViewById(R.id.check_box_layout);
        radioButtonLayout = findViewById(R.id.radio_button_layout);
        editTextLayout = findViewById(R.id.edit_text_layout);
        submitButton = findViewById(R.id.submit_button);
        questionText = findViewById(R.id.question_text);
        endText = findViewById(R.id.end_text);
        radioButtonA = findViewById(R.id.radio_button_a);
        radioButtonB = findViewById(R.id.radio_button_b);
        radioButtonC = findViewById(R.id.radio_button_c);
        radioButtonD = findViewById(R.id.radio_button_d);
        textEntry = findViewById(R.id.textEntry);
        buttonLayout.setVisibility(View.INVISIBLE);
        radioButtonLayout.setVisibility(View.GONE);
        checkBoxLayout.setVisibility(View.GONE);
        editTextLayout.setVisibility(View.GONE);
        endText.setVisibility(View.GONE);

        // Save the score and question number if the screen is rotated
        if (savedInstanceState != null) {
            score = savedInstanceState.getDouble("score");
            questionNumber = savedInstanceState.getInt("questionNumber");
            changeView(questionNumber);
        }
    }
        @Override
        protected void onSaveInstanceState (Bundle outState){
            super.onSaveInstanceState(outState);
            outState.putDouble("score", score);
            outState.putInt("questionNumber", questionNumber);
        }
    /**
     * Handles a click on the Submit/Start button
     */
    public void submitButtonClicked(View view) {

        if (questionNumber == 1){
            checkBoxCheck();
        }
        if (questionNumber == 2){
            radioButtonCheck();
        }
        if (questionNumber == 3){
            textEntryCheck();
            textEntry.setText("");
        }
        if (questionNumber == 4){
            shareScore();
        }
        if (questionNumber == 5){
            resetQuiz();
        }
        questionNumber += 1;
        changeView(questionNumber);
    }

    /**
     * Work around functions to turn off other radio buttons when one is clicked
     * Used so that the radio buttons could each lay in their own LinearLayout
     */
    public void radioAChecked (View view) {
        radioButtonA.setChecked(true);
        radioButtonB.setChecked(false);
        radioButtonC.setChecked(false);
        radioButtonD.setChecked(false);
    }
    public void radioBChecked (View view) {
        radioButtonA.setChecked(false);
        radioButtonB.setChecked(true);
        radioButtonC.setChecked(false);
        radioButtonD.setChecked(false);
    }
    public void radioCChecked (View view) {
        radioButtonA.setChecked(false);
        radioButtonB.setChecked(false);
        radioButtonC.setChecked(true);
        radioButtonD.setChecked(false);
    }
    public void radioDChecked (View view) {
        radioButtonA.setChecked(false);
        radioButtonB.setChecked(false);
        radioButtonC.setChecked(false);
        radioButtonD.setChecked(true);
    }

    /**
     * Handles a quiz reset
     *
     */
    public void resetQuiz () {
        questionNumber = -1;
        score = 0;
        scorePercentage = 0;
        answerSelection = "";
        endText.setVisibility(View.GONE);
    }

    /**
     * Handles the logic for sharing the quiz score
     *
     */
    public void shareScore () {
        String message = "Check it out, I just scored a " + scorePercentage + "on the culinary quiz!";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Share your score with your frineds"));
        submitButton.setText("Play Again");
    }

    /**
     * Handles the logic for EditText type questions
     *
     */
    public void textEntryCheck () {

        if (textEntry.getText().toString().equals(allQuestions.list.get(questionNumber).questionSet.get("answer"))) {
            correctAnswer();
        } else {
            incorrectAnswer();
        }
    }

    /**
     * Handles the logic for Radiobutton type questions
     */
    public void radioButtonCheck () {

        String radioAnswer = "";
        if (radioButtonA.isChecked()) {
           radioAnswer = "a";
        }else if (radioButtonB.isChecked()) {
            radioAnswer = "b";
        }else if (radioButtonC.isChecked()) {
            radioAnswer = "c";
        }else if (radioButtonD.isChecked()) {
            radioAnswer = "d";
        }

        if (radioAnswer.equals(allQuestions.list.get(questionNumber).questionSet.get("answer"))){
            correctAnswer();
        } else {
            incorrectAnswer();
        }
    }

    /**
     * Handles the logic for Checkbox type questions
     */
    public void checkBoxCheck () {

        int checkBoxTotal = 0;
        CheckBox aCheckBox = findViewById(R.id.check_box_a);
        CheckBox bCheckBox = findViewById(R.id.check_box_b);
        CheckBox cCheckBox = findViewById(R.id.check_box_c);
        CheckBox dCheckBox = findViewById(R.id.check_box_d);

        //Check which boxes are checked and add to checkBoxTotal when selected
        if (aCheckBox.isChecked()){
            checkBoxTotal += 2;
            aCheckBox.setChecked(false);
        }
        if (bCheckBox.isChecked()){
            checkBoxTotal += 4;
            bCheckBox.setChecked(false);
        }
        if (cCheckBox.isChecked()){
            checkBoxTotal += 8;
            cCheckBox.setChecked(false);
        }
        if (dCheckBox.isChecked()){
            checkBoxTotal += 16;
            dCheckBox.setChecked(false);
        }

        //If checkBoxTotal = answer call correctAnswer, else call incorrectAnswer
        if (String.valueOf(checkBoxTotal).equals(allQuestions.list.get(questionNumber).questionSet.get("answer"))){
            correctAnswer();
        } else {
            incorrectAnswer();
        }
    }

    /**
     * Handles the logic for a click on an answer button
     */
    public void answerButtonClicked(View view) {

        //Set answerSelection to the value of the button clicked
        switch(view.getId()) {
            case R.id.a_button:
                answerSelection = "a";
                break;
            case R.id.b_button:
                answerSelection = "b";
                break;
            case R.id.c_button:
                answerSelection = "c";
                break;
            case R.id.d_button:
                answerSelection = "d";
                break;
            default:
                break;
        }
        //If the answer choice is correct call correctAnswer, else call incorrectAnswer
        if (answerSelection.equals(allQuestions.list.get(questionNumber).questionSet.get("answer"))){
            correctAnswer();
        } else {
            incorrectAnswer();
        }

        questionNumber += 1;
        changeView(questionNumber);
    }

    /**
     * Changes the View based on the question number
     * Currently functions based upon the question number and a set question order.
     * Future update will operate based upon type of question received and set up the
     * view based upon a random type of next question
     * @param whichQuestion
     */
    public void changeView(int whichQuestion) {

        if (whichQuestion == 0) {
            submitButton.setVisibility(View.INVISIBLE);
            buttonLayout.setVisibility(View.VISIBLE);
            questionText.setText(R.string.question1);
        }
        if (whichQuestion == 1) {
            buttonLayout.setVisibility((View.GONE));
            checkBoxLayout.setVisibility(View.VISIBLE);
            submitButton.setText(R.string.submit);
            submitButton.setVisibility(View.VISIBLE);
            questionText.setText(allQuestions.list.get(questionNumber).questionSet.get("question").toString());
        }
        if (whichQuestion == 2) {
            checkBoxLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
            radioButtonLayout.setVisibility(View.VISIBLE);
            questionText.setText(allQuestions.list.get(questionNumber).questionSet.get("question").toString());
        }
        if (whichQuestion == 3) {
            radioButtonLayout.setVisibility(View.GONE);
            checkBoxLayout.setVisibility(View.GONE);
            buttonLayout.setVisibility(View.GONE);
            radioButtonA.setChecked(false);
            radioButtonB.setChecked(false);
            radioButtonC.setChecked(false);
            radioButtonD.setChecked(false);
            editTextLayout.setVisibility(View.VISIBLE);
            questionText.setText(allQuestions.list.get(questionNumber).questionSet.get("question").toString());
        }
        if (whichQuestion == 4) {
            editTextLayout.setVisibility(View.GONE);
            endText.setVisibility(View.VISIBLE);
            scorePercentage = score/whichQuestion * 100;
            questionText.setText("Quiz Results\n\n" + scorePercentage + "%");
        }
    }

    public void correctAnswer () {
        Toast toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
        toast.show();
        score += 1;
    }

    public void incorrectAnswer () {
        Toast toast = Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT);
        toast.show();
    }

}
