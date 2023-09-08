import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class USGame {
    InitialStoryGenerator beginStory = new InitialStoryGenerator();
    StoryGenerator midStory = new StoryGenerator();
    StorylineParser parser = new StorylineParser();

    public static String generateConclusion(int socialLife, int grades, int health, int happiness, int attendance) {
        int totalPoints = socialLife + grades + health + happiness + attendance;
        int average = 20;

        String conclusion = "CONGRATULATIONS ON GRADUATIONS!!! \nBased on your university experience, ";

        if (socialLife > average) {
            conclusion += "you excelled in your social life, making many new friends and having a great time.";
        } else if (socialLife == average) {
            conclusion += "you had a fair social life, but missed out on some opportunities to make new friends.";
        } else {
            conclusion += "you didn't prioritize your social life enough and missed out on some opportunities to make new friends.";
        }

        if (grades > average) {
            conclusion += " You also did very well in your classes and achieved great grades.";
        } else if (grades == average) {
            conclusion += " You had a fair performance in your classes, but could have done better with more focus and effort.";
        } else {
            conclusion += " However, your grades suffered a bit due to lack of focus or other priorities.";
        }

        if (health > average) {
            conclusion += " You took good care of yourself and stayed healthy throughout your university experience.";
        } else if (health == average) {
            conclusion += " You had a fair health experience, but could have done better with more attention to your well-being.";
        } else {
            conclusion += " You neglected your health a bit and may have had some health issues as a result.";
        }

        if (happiness > average) {
            conclusion += " You remained happy throughout your university experience, enjoying your studies and social life.";
        } else if (happiness == average) {
            conclusion += " You had a fair experience with your emotional well-being, but could have done better by seeking support and fulfillment.";
        } else {
            conclusion += " Unfortunately, you didn't enjoy your time at university as much as you could have and may have experienced some negative emotions.";
        }

        if (attendance > average) {
            conclusion += " You also had great attendance and made sure to attend all your classes and events.";
        } else if (attendance == average) {
            conclusion += " You had a fair attendance, but could have attended more classes and events to fully engage with the university experience.";
        } else {
            conclusion += " You missed some classes and events, which may have impacted your grades or social life.";
        }

        if (totalPoints > average * 5) {
            conclusion += " Overall, you had a great university experience, excelling in multiple areas.";
        } else if (totalPoints < average * 5) {
            conclusion += " Unfortunately, your university experience was not as positive as it could have been, with some missed opportunities and challenges.";
        } else {
            conclusion += " Overall, you had a decent university experience, with strengths and areas for improvement.";
        }

        return conclusion;
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean isPlaying = true;

        // Game loop
        while (isPlaying) {
            CopyOnWriteArrayList<Storyline> storylines = new CopyOnWriteArrayList<>();

            // Print introduction and get player's name
            System.out.println("Welcome to University Story Game!");
            System.out.print("Please enter your name: ");
            String playerName = scanner.nextLine();

            // Gather the user information to personalise university experience
            System.out.println("Welcome to the University Adventure Game " + playerName + "!");

            // Initialize player's attributes
            int socialLife = 20;
            int grades = 20;
            int health = 20;
            int happiness = 20;
            int attendance = 20;

            // Print starting attributes
            System.out.println("\nYour starting attributes are:");
            System.out.println("Social Life: " + socialLife);
            System.out.println("Grades: " + grades);
            System.out.println("Health: " + health);
            System.out.println("Happiness: " + happiness);
            System.out.println("Attendance: " + attendance + "\n");

            // Begin game
            storylines.add(StorylineParser.parse(InitialStoryGenerator.main()));
            System.out.println("\nLet's begin your adventure!\n");
            StringBuilder events = new StringBuilder();
            int storiesNum = 3; //The number of stories the player can have
            for (int i = 0; i < storiesNum; i++) {
                System.out.println("\n" + storylines.get(i).getTitle());

                // Print out the storyline details
                System.out.println("Events:");
                for (String event : storylines.get(i).getEvents()) {
                    System.out.println("- " + event);
                }
                System.out.println("Decision: " + storylines.get(i).getDecision().getPrompt());
                for (Option option : storylines.get(i).getDecision().getOptions()) {
                    System.out.println("- " + option.getText());
                }

                // Ask for player input
                int choiceIndex = -1;
                while (choiceIndex < 0 || choiceIndex >= storylines.get(i).getDecision().getOptions().size()) {
                    System.out.print("\nWhat option do you choose? (1/2) ");
                    String playerChoice = scanner.nextLine();
                    try {
                        choiceIndex = Integer.parseInt(playerChoice) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter 1 or 2.");
                        continue;
                    }
                    if (choiceIndex < 0 || choiceIndex >= storylines.get(i).getDecision().getOptions().size()) {
                        System.out.println("Invalid input. Please enter 1 or 2.");
                    }
                }
                Option chosenOption = storylines.get(i).getDecision().getOptions().get(choiceIndex);
                System.out.println("\n" + chosenOption.getOutcome().getTitle());
                for (String event : chosenOption.getOutcome().getEvents()) {
                    System.out.println("- " + event);
                    events.append(event).append("\n");
                }

                // Update player's attributes
                socialLife += chosenOption.getSocialLifeChange();
                grades += chosenOption.getGradesChange();
                health += chosenOption.getHealthChange();
                happiness += chosenOption.getHappinessChange();
                attendance += chosenOption.getAttendanceChange();

                // Display updated attributes
                System.out.println("\nYour updated attributes are:");
                System.out.println("Social Life: " + socialLife);
                System.out.println("Grades: " + grades);
                System.out.println("Health: " + health);
                System.out.println("Happiness: " + happiness);
                System.out.println("Attendance: " + attendance);

                if (i == storiesNum - 1) {
                    break;
                }

                // Generate next storyline if the array size hasn't reached the storiesNum
                if (storylines.size() == storiesNum - 1){
                    System.out.println("\nGenerating Story..." );
                    storylines.add(StorylineParser.parse(ConclusionGenerator.generateStoryline(events.toString())));
                }
                else if (storylines.size() < storiesNum) {
                    System.out.println("\nGenerating Story..." );

                    storylines.add(StorylineParser.parse(StoryGenerator.generateStoryline(events.toString())));
                }
            }
            // Generate conclusion based on attribute points
            String conclusion = generateConclusion(socialLife, grades, health, happiness, attendance);
            System.out.println("\n" + conclusion);

            // Ask player if they want to play again
            System.out.print("\nDo you want to play again? (y/n) ");
            String playAgain = scanner.nextLine();
            if (!playAgain.equalsIgnoreCase("y")) {
                isPlaying = false;
            }

        }

        // Exit game
        System.out.println("Thank you for playing University Story Game!");
    }
}
