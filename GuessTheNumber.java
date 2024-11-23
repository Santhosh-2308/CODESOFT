import java.util.Scanner;
import java.util.Random;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain;
        int totalScore = 0;

        System.out.println("Welcome to Guess the Number!");

        do {
            // Generate a random number within the specified range (1 to 100)
            int randomNumber = random.nextInt(100) + 1;
            int attemptsLeft = 5; // Limit the number of attempts
            boolean numberGuessed = false;
            
            System.out.println("\nA new number has been generated! You have " + attemptsLeft + " attempts to guess it.");
            
            // User's guessing loop
            while (attemptsLeft > 0) {
                System.out.print("Enter your guess (1 to 100): ");
                int userGuess = scanner.nextInt();
                
                if (userGuess == randomNumber) {
                    System.out.println("Congratulations! You guessed the correct number!");
                    numberGuessed = true;
                    totalScore += attemptsLeft; // Add remaining attempts to score
                    break;
                } else if (userGuess > randomNumber) {
                    System.out.println("Your guess is too high.");
                } else {
                    System.out.println("Your guess is too low.");
                }

                attemptsLeft--;
                System.out.println("Attempts left: " + attemptsLeft);
            }

            if (!numberGuessed) {
                System.out.println("Sorry! You've run out of attempts. The correct number was: " + randomNumber);
            }

            System.out.print("\nDo you want to play another round? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");

        } while (playAgain);

        // Display the final score
        System.out.println("\nThank you for playing! Your final score is: " + totalScore);
        scanner.close();
    }
}
