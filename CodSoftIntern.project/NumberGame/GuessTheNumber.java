package NumberGame;
import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int min = 1, max = 100, maxAttempts = 5;
        int score = 0, round = 1;

        System.out.println("Welcome to the 'Guess the Number' Game!");

        boolean playAgain = true;

        while (playAgain) {
            int numberToGuess = random.nextInt(max - min + 1) + min;
            int attemptsLeft = maxAttempts;
            boolean guessedCorrectly = false;

            System.out.println("\nRound " + round + ": Guess a number between " + min + " and " + max);

            while (attemptsLeft > 0) {
                System.out.print("Enter your guess (" + attemptsLeft + " attempts left): ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input! Please enter an integer.");
                    scanner.next();
                    continue;
                }

                int guess = scanner.nextInt();

                if (guess < min || guess > max) {
                    System.out.println("Your guess must be between " + min + " and " + max);
                    continue;
                }

                if (guess == numberToGuess) {
                    System.out.println("Correct! You guessed the number!");
                    guessedCorrectly = true;
                    score++;
                    break;
                } else if (guess < numberToGuess) {
                    System.out.println("Too low!");
                } else {
                    System.out.println("Too high!");
                }

                attemptsLeft--;
            }

            if (!guessedCorrectly) {
                System.out.println("Out of attempts! The number was: " + numberToGuess);
            }

            System.out.println("Current Score: " + score);

            System.out.print("Do you want to play another round? (yes/no): ");
            scanner.nextLine(); 
            String response = scanner.nextLine().trim().toLowerCase();

            if (!response.equals("yes") && !response.equals("y")) {
                playAgain = false;
            } else {
                round++;
            }
        }

        System.out.println("\nGame Over! You played " + round + " round(s).");
        System.out.println("FINAL SCORE: " + score);

        scanner.close();
    }
}
