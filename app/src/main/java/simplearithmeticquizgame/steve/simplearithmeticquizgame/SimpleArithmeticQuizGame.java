package simplearithmeticquizgame.steve.simplearithmeticquizgame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;


import java.util.Random;


public class SimpleArithmeticQuizGame extends ActionBarActivity {

    public String firstNumber;
    public String secondNumber;
    public String operator;
    public String correctAnswer;
    private int totalQuestions;
    private int totalAnswers;
    private int correctAnswers;
    private int wrongAnswers;
    private int guessRows;
    int randomOpInt;
    boolean op0 = true;
    boolean op1 = true;
    boolean op2 = true;
    boolean op3 = true;
    private Random random;
    private Animation shakeAnimation;


    private TextView equationTextView;
    private TextView questionNumberTextView;
    private TextView answerTextView;
    private TableLayout buttonTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_arithmetic_quiz_game);

        guessRows = 1;
        random = new Random();
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);


        equationTextView = (TextView) findViewById(R.id.equationTextView);
        questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        buttonTableLayout = (TableLayout) findViewById(R.id.buttonTableLayout);
        resetQuiz();
    }

    private void resetQuiz() {
        totalQuestions = 1;
        totalAnswers = 0;
        correctAnswers = 0;
        wrongAnswers = 0;
        firstNumber = null;
        secondNumber = null;
        operator = null;
        correctAnswer = null;

        loadNextEquation();

    }

    private void loadNextEquation() {

        questionNumberTextView.setText(totalQuestions + " " + "of 10");
        createEquation();


        for (int row = 0; row < buttonTableLayout.getChildCount(); ++row)
            ((TableRow) buttonTableLayout.getChildAt(row)).removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        for (int row = 0; row < guessRows; row++)
        {
            TableRow currentTableRow = getTableRow(row);


            for (int column = 0; column < 3; column++)
            {

                Button newGuessButton =
                        (Button) inflater.inflate(R.layout.guess_button, null);

                if (operator == "+") {
                    newGuessButton.setText(Integer.toString(random.nextInt(200)));
                } else if (operator == "-") {
                    newGuessButton.setText(Integer.toString(random.nextInt(100)));
                }  else if (operator == "*" || operator == "/") {
                    newGuessButton.setText(Integer.toString(random.nextInt(625)));
                }


                newGuessButton.setOnClickListener(guessButtonListener);
                currentTableRow.addView(newGuessButton);
            }
        }
        int row = random.nextInt(guessRows);
        int column = random.nextInt(3);
        TableRow randomTableRow = getTableRow(row);
        ((Button)randomTableRow.getChildAt(column)).setText(correctAnswer);
        equationTextView.setText(firstNumber + " " + operator + " " + secondNumber + " " + "=");
    }

    private TableRow getTableRow(int row)
    {
        return (TableRow) buttonTableLayout.getChildAt(row);
    }

    private void createEquation() {


        int randomNumber1;
        int randomNumber2;
        int answer;
        int modulo;

        //generates random operator
        if (op0 && op1 && op2 && op3) {
            randomOpInt = random.nextInt(4);
            if (randomOpInt == 0) {
                operator = "+";
            } else if (randomOpInt == 1) {
                operator = "-";
            } else if (randomOpInt == 2) {
                operator = "*";
            } else if (randomOpInt == 3) {
                operator = "/";
            }

        //if certain operator is selected in menu
        } else if (op0 && !op1 && !op2 && !op3){
            randomOpInt = 0;
            operator = "+";
        } else if (!op0 && op1 && !op2 && !op3) {
            randomOpInt = 1;
            operator = "-";
        } else if (!op0 && !op1 && op2 && !op3) {
            randomOpInt = 2;
            operator = "*";
        } else if (!op0 && !op1 && !op2 && op3) {
            randomOpInt = 3;
            operator = "/";
        }

        //number generation for addition
        if (randomOpInt == 0) {
            randomNumber1 = random.nextInt(100);
            firstNumber = Integer.toString(randomNumber1);
            randomNumber2 = random.nextInt(100);
            secondNumber = Integer.toString(randomNumber2);
            answer = randomNumber1 + randomNumber2;
            correctAnswer = Integer.toString(answer);

        //subtraction
        } else if (randomOpInt == 1) {
            randomNumber1 = random.nextInt(100);
            firstNumber = Integer.toString(randomNumber1);
            do {
                randomNumber2 = random.nextInt(100);
            } while (randomNumber1 < randomNumber2);
            secondNumber = Integer.toString(randomNumber2);
            answer = randomNumber1 - randomNumber2;
            correctAnswer = Integer.toString(answer);

        //multiplication
        } else if (randomOpInt == 2) {
            randomNumber1 = random.nextInt(25);
            firstNumber = Integer.toString(randomNumber1);
            randomNumber2 = random.nextInt(25);
            secondNumber = Integer.toString(randomNumber2);
            answer = randomNumber1 * randomNumber2;
            correctAnswer = Integer.toString(answer);

        //division
        } else if (randomOpInt == 3) {
            do {
                randomNumber1 = random.nextInt(625);
                firstNumber = Integer.toString(randomNumber1);
                do {
                    randomNumber2 = random.nextInt(100);
                } while (randomNumber1 < randomNumber2);
                secondNumber = Integer.toString(randomNumber2);
                answer = randomNumber1 / randomNumber2;
                correctAnswer = Integer.toString(answer);
                modulo = randomNumber1 % randomNumber2;
            } while (modulo != 0);

        }
    }
    private final int CHOICES_MENU_ID = Menu.FIRST;
    private final int OPERATOR_MENU_ID = Menu.FIRST + 1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        // add two options to the menu
        menu.add(Menu.NONE, CHOICES_MENU_ID, Menu.NONE, R.string.choices);
        menu.add(Menu.NONE, OPERATOR_MENU_ID, Menu.NONE, R.string.operation);

        return true;
    }

    // called when the user selects an option from the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case CHOICES_MENU_ID:

                final String[] possibleChoices =
                        getResources().getStringArray(R.array.guesses_List);


                AlertDialog.Builder choicesBuilder =
                        new AlertDialog.Builder(this);
                choicesBuilder.setTitle(R.string.choices);


                choicesBuilder.setItems(R.array.guesses_List,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int item)
                            {

                                guessRows = Integer.parseInt(possibleChoices[item]) / 3;
                                resetQuiz();
                            }
                        }
                );


                AlertDialog choicesDialog = choicesBuilder.create();
                choicesDialog.show();
                return true;

            case OPERATOR_MENU_ID:


                final AlertDialog.Builder operatorBuilder =
                        new AlertDialog.Builder(this);
                operatorBuilder.setTitle(R.string.operation);

                operatorBuilder.setItems(R.array.operator_list,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int item) {



                                if (item == 0) {
                                    op0 = true; op1 = false; op2 = false; op3 = false;
                                } else if (item == 1){
                                    op0 = false; op1 = true; op2 = false; op3 = false;
                                } else if (item == 2) {
                                    op0 = false; op1 = false; op2 = true; op3 = false;
                                } else if (item == 3) {
                                    op0 = false; op1 = false; op2 = false; op3 = true;
                                } else {
                                    op0 = true; op1 = true; op2 = true; op3 = true;
                                }
                                resetQuiz();
                            }
                        }
                );


                operatorBuilder.setPositiveButton(R.string.reset_quiz,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int button)
                            {
                                resetQuiz();
                            }
                        }
                );


                AlertDialog regionsDialog = operatorBuilder.create();
                regionsDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void submitGuess(Button guessButton) {
        String guess = guessButton.getText().toString();
        if (guess == correctAnswer) {
            answerTextView.setTextColor(getResources().getColor(R.color.correct_answer));
            answerTextView.setText(guessButton.getText().toString() + "!");
            correctAnswers++;
            totalQuestions++;

            if (correctAnswers == 10) {
                totalAnswers = correctAnswers + wrongAnswers;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle(R.string.reset_quiz);


                builder.setMessage(String.format("%d %s, %.02f%% %s",
                        totalAnswers, getResources().getString(R.string.guesses),
                        (1000 / (double) totalAnswers),
                        getResources().getString(R.string.correct)));

                builder.setCancelable(true);
                builder.setPositiveButton(R.string.reset_quiz,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                resetQuiz();
                            }
                        }
                );
                builder.setNegativeButton(R.string.exit,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.exit(0);

                            }
                        }
                );

                AlertDialog resetDialog = builder.create();
                resetDialog.show();
            } else {
                loadNextEquation();
            }

        } else {
            answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer));
            answerTextView.setText(guessButton.getText().toString() + "!");
            equationTextView.startAnimation(shakeAnimation);
            guessButton.setEnabled(false);
            wrongAnswers++;
        }
        totalAnswers++;


    }

    private OnClickListener guessButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v)
        {
            submitGuess((Button) v);
        }
    };


}
