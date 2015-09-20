package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by dalexiv on 20.09.15.
 */
public class Question {
    private int numberOfAnswers;
    private String questionText = "";
    private String[] answersText;
    private int correctAnswer;
    private int[] percentage;
    private String[] hint;

    // 0 - no hint
    // 1 - one hint
    // 3 - three hints
    private int numberOfHints;

    public Question(String filepath, int numberOfAnswers)
    {
        this.numberOfAnswers = numberOfAnswers;
        File myFile = new File("res/" + filepath);

        initArrays(numberOfAnswers);

        try {
            Scanner sc = new Scanner(myFile);

            String cur_line = "";


            // Reading question
            while (sc.hasNextLine() && !(cur_line = sc.nextLine()).startsWith("#"))
            {
                questionText += cur_line + "\n";
            }

            // Reading answers
            for (int i = 0; i < numberOfAnswers; ++i)
                answersText[i] = sc.nextLine();

            // Reading correct answer
            correctAnswer = Integer.parseInt(sc.nextLine());

            // Percentage of answers
            for (int i = 0; i < numberOfAnswers; ++i)
                percentage[i] = Integer.parseInt(sc.nextLine());

            // Reading flag
            numberOfHints = Integer.parseInt(sc.nextLine());

            // Nulling hints
            for (int i = 0; i < numberOfAnswers; ++i)
                hint[i] = "";

            // Reading hints
            switch (numberOfHints)
            {
                case 1:
                    while (sc.hasNextLine() && !(cur_line = sc.nextLine()).startsWith("#"))
                        hint[0] += cur_line + "\n";
                    break;
                case 3:
                    for (int i = 0; i < numberOfAnswers; ++i)
                        while (sc.hasNextLine() && !(cur_line = sc.nextLine()).startsWith("#"))
                            hint[i] += cur_line + "\n";
                    break;
                default:
                    break;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File " + filepath + " not found");
        }

    }

    private void initArrays(int numberOfAnswers) {
        answersText = new String[numberOfAnswers];
        percentage = new int[numberOfAnswers];
        hint = Collections.nCopies(numberOfAnswers, "").toArray(new String[0]);
    }

    public String getQuestionText()
    {
        return questionText;
    }

    public String getPossibleAnswers(boolean isPecNeeded) {
        String answers = "";
        for (int i = 0; i < numberOfAnswers; ++i)
            if (isPecNeeded) {
                answers += (i+1) + ") " + answersText[i] + " " + percentage[i] + "%";
                if (correctAnswer == i + 1)
                    answers += " RIGHT";
                answers += "\n";
            }
            else
                answers += (i+1) + ") " + answersText[i] +"\n";

        return answers;
    }

    public boolean checkIfAnsIsCorrect(int n)
    {
        return (n == correctAnswer);
    }

    public String getAnswerRespond(int n)
    {
        String stats = "Answer stats" + "\n" + getPossibleAnswers(true);
        if (n == correctAnswer)
            return "Well done!" + "\n" + stats;
        else
        {
            String mess = "Nope, the right answer is " + correctAnswer;

            switch (numberOfHints) {
                case 1:
                    return mess + "\n" + hint[0] + "\n" + stats;
                case 3:
                    return mess + "\n" + hint[n-1] + "\n" + stats;
                default:
                    return mess + "\n" + stats;
            }
        }
    }
}
