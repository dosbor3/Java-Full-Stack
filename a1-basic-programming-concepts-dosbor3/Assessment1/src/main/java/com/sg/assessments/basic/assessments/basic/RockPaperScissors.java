package com.sg.assessments.basic.assessments.basic;

import java.util.*;

public class RockPaperScissors {
    static Scanner input = new Scanner(System.in);
    static int rounds = 0;
    static int userChoice;
    static int computerChoice;
    static int ties;
    static int userWins;
    static int computerWins;
    static String selection;

    public static void main(String[] args) {
        playRounds();
    }

    public static void playRounds() {
        do {
            System.out.println("Let's Play ROCK, PAPER, SCISSORS!!!\n");
            System.out.print("How many rounds do you wish to play (min rounds 1, max rounds 10): ");
            rounds = Integer.parseInt(input.next());

            if (rounds < 1 || rounds > 10) {
                System.out.println("Please enter a valid response, between 1 and 10 ONLY!!");
                System.out.print("How many rounds do you wish to play (minimum rounds: 1, maximum rounds: 10");
                rounds = Integer.parseInt(input.next());

            }

            System.out.println("Okay, Make a selection:  ");
            for (int i = 0; i < rounds; i++) {
                System.out.println("1 = ROCK, 2 = PAPER, 3 = SCISSORS");
                userChoice = Integer.parseInt(input.next());

                if (userChoice < 1 || userChoice > 3) {
                    System.out.println("You MUST enter 1 for ROCK, 2 for PAPER, or 3 for SCISSORS ");
                    System.out.print("Make a selection: 1 = ROCK, 2 = PAPER, 3 = SCISSORS ");
                    userChoice = Integer.parseInt(input.next());
                }

                computerChoice = (int) (Math.random() * (3 - 1) + 1);

                switch (userChoice) {
                    case 1: {
                        if (computerChoice == 1) {
                            System.out.println("Tie\n");
                            ties++;
                        } else if (computerChoice == 2) {
                            System.out.println("You Loose, Paper wraps rock!\n");
                            computerWins++;
                        } else {
                            System.out.println("You Win!! Rock breaks Scissors!\n");
                            userWins++;
                        }
                        break;
                    }
                    case 2: {
                        if (computerChoice == 1) {
                            System.out.println("You Win!! Paper wraps rock!\n");
                            userWins++;
                        } else if (computerChoice == 2) {
                            System.out.println("Tie\n");
                            ties++;
                        } else {
                            System.out.println("You Loose, Scissors cut paper!\n");
                            computerWins++;
                        }
                        break;
                    }
                    case 3: {
                        if (computerChoice == 1) {
                            System.out.println("You Loose, Rock breaks Scissors\n");
                            computerWins++;

                        } else if (computerChoice == 2) {
                            System.out.println("You Win!! Scissors cut paper!\n");
                            userWins++;
                        } else {
                            System.out.println("Tie\n");
                            ties++;
                        }
                        break;
                    }

                }

            }
            System.out.println("Ties: " + ties);
            System.out.println("Wins: " + userWins);
            System.out.println("Loses: " + computerWins);
            if (userWins == computerWins ) {
                System.out.println("This game ends in a tie!");
            } else if (userWins > computerWins) {
                System.out.println("Wow!  You WIN the game!");
            } else {
                System.out.println("Sorry... YOU LOOSE the game!");
            }
            System.out.println("Do you wish to play again: Y for yes, N for No");
            selection = input.next();

        } while (selection.equalsIgnoreCase("y"));
    }
}
