/**
 * Created by coryglaeser on 2/20/18.
 */

package com.example.android.culinaryquiz;
import java.util.ArrayList;

//Array of questions

public class QuestionBank {

    ArrayList<Question> list = new ArrayList<>();

    public QuestionBank() {

        list.add(new Question("How many tablespoons are in one cup?",
                "4",
                "8",
                "16",
                "32",
                "c"));
        list.add(new Question("Which of the following are mirepoix ingredients?",
                "Carrots",
                "Celery",
                "Garlic",
                "Onion",
                "22"));
        list.add(new Question("Which of the following is NOT a classic French Mother Sauce?",
                "Hollandaise",
                "Mayonnaise",
                "Espagnole",
                "Bechamel",
                "c"));
        list.add(new Question("In Celcius, what temperature does water boil at?",
                "",
                "",
                "",
                "",
                "100"));
    }
}

