package com.company;

import java.util.Scanner;

/**
 * Created by dalexiv on 20.09.15.
 */
public class Questionary {

    // Some hardcode needs to be extracted to another txt
    private final int questionNumber = 9;
    private String startText = "Hi, there is a programming test translated from Meduza." + "\n"
            + "Here is the original link - https://meduza.io/quiz/true-or-false?game=quiz" + "\n";

    String[] paths = {"q1.txt", "q2.txt", "q3.txt", "q4.txt",
            "q5.txt", "q6.txt", "q7.txt", "q8.txt", "q9.txt"};
    int[] numberOfAnswers = {4, 4, 4, 4, 2, 4, 3, 4, 4};

    int score = 0;


    Question[] questions = new Question[questionNumber];

    public Questionary() {
        for (int i = 0; i < paths.length; ++i)
            questions[i] = new Question(paths[i], numberOfAnswers[i]);
    }

    public void startSample() {
        clearConsole();
        System.out.println(startText);
        Scanner sc = new Scanner(System.in);
        System.out.println("Press any key to continue...");
        sc.nextLine();
    }

    public void startTesting() {
        for (int i = 0; i < questionNumber; ++i) {
            System.out.println(questions[i].getQuestionText());
            System.out.println(questions[i].getPossibleAnswers(false));
            if (i == 0)
                System.out.println("Type number to answer this question");
            Scanner sc = new Scanner(System.in);
            String cur_line;

            while (!tryParseInt(cur_line = sc.nextLine())
                    || Integer.parseInt(cur_line) <= 0
                    || Integer.parseInt(cur_line) > numberOfAnswers[i])
                System.out.println("Write a number from one to " + numberOfAnswers[i]);

            int answer_received = Integer.parseInt(cur_line);
            if (questions[i].checkIfAnsIsCorrect(answer_received))
                ++score;

            System.out.println(questions[i].getAnswerRespond(answer_received));

            System.out.println("Press any key to continue...");
            sc.nextLine();
            clearConsole();
        }
    }

    public void finishTest() {
        if (score <= 3)
            System.out.print("Not bad for the beginner!");
        if (score >= 4 && score <= 5)
            System.out.print("Average programmer score this");
        if (score >= 6 && score <= 7)
            System.out.print("Great job, go and find a $100K programmer job");
        if (score >= 8 && score <= 9)
            System.out.print("Are you Linus Torvalds or Bjarne Stroustrup?");
        System.out.println(" " + score + "/9");

        System.out.println("Press any key to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        System.exit(0);
    }

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Looks dirty
    public static void clearConsole() {
        String blank = "";
        for (int i = 0; i < 100; ++i)
            blank += "\n";
        System.out.print(blank);
    }
}
