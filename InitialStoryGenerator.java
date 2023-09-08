import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Scanner;

public class InitialStoryGenerator {

    public static String main() {
        // Prompt the user for inputs about their university experience
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the University Adventure Game!");
        System.out.println("Please answer the following questions to generate your adventure:");
        System.out.print("What is your course of study? ");
        String course = scanner.nextLine();
        System.out.print("What are your hobbies? ");
        String hobbies = scanner.nextLine();
        System.out.print("Would you describe yourself as introverted or extroverted? ");
        String personality = scanner.nextLine();
        System.out.print("How would you describe your academic prowess? ");
        String academics = scanner.nextLine();
        System.out.println("\nGenerating Story...");

        String storyline = generateStoryline(course, hobbies, personality, academics);

//        System.out.println("\nGenerating your adventure...");
        // Print the generated story
//        System.out.println("\nHere's your adventure:");
//        System.out.println(storyline);
//        StorylineParser.parseJson(storyline);
        return storyline;
    }

    public static String generateStoryline(String course, String hobbies, String personality, String academics) {
        // Initialize the OpenAI API service
        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(90));

        // Construct the prompt
        String prompt = "Create a creative storyline object that is tailored to the player's interests and personality. The player studies " + course + "at university, enjoys " + hobbies + ", and is " + personality + " but " + academics + ". The storyline object should consist of a title and a series of events, as well as a decision that leads into another creative storyline and will have an impact on the player's academic performance, social life, health, happiness, and attendance.\n" +
                "The storyline should be loosely based around a student's introduction to the university environment, where they would be making friends, finding societies to join and enjoying their hobby \n" +
                "\n" +
                "To create the storyline object, consider the following aspects:\n" +
                "\n" +
                "The player's current situation: What is the player currently doing? Are they in school, working, or something else?\n" +
                "The player's interests: What are the player's interests and hobbies? Are they interested in any particular field of study or activity?\n" +
                "The player's personality: What are the player's personality traits? Are they introverted or extroverted? Are they outgoing or reserved?\n" +
                "The decision: What two drastically opposing decisions will the player need to make based on their prior decisions related to their hobbies, education and especially social situations, and what are the consequences of each option? How will each decision affect the player's academic performance, social life, health, happiness, and attendance?\n" +
                "\n" +
                "Each event should be two sentences and should be taking place in the third person and referring to the player as 'You'.\n" +
                "\n" +
                "When creating the storyline object, try to incorporate unexpected and surprising yet realistic twists, unique and interesting characters, and engaging plot points. The storyline must be drastically different than the one given as the input.\n" +
                "Try not to make include the idea of balancing in the story and when prompting the player to decide between two options, give the character situations to choose from rather than outright telling them to focus on one or the other.\n" +
                "Make sure the story is progressing from the given storyline and not being continuously focused on the same topics.\n" +
                "Use the following JSON format to organize your output:\n" +
                "\n" +
                "{\n" +
                "\"title\": \"Title of the Storyline\",\n" +
                "\"events\": [\n" +
                "\"Event 1\",\n" +
                "\"Event 2\",\n" +
                "\"Event 3\"\n" +
                "],\n" +
                "\"decision\": {\n" +
                "\"title\": \"Decision Title\",\n" +
                "\"options\": [\n" +
                "{\n" +
                "\"title\": \"Option 1\",\n" +
                "\"storyline\": {\n" +
                "\"title\": \"Option 1 Storyline\",\n" +
                "\"events\": [\n" +
                "\"Option 1 Event 1\",\n" +
                "\"Option 1 Event 2\",\n" +
                "\"Option 1 Event 3\"\n" +
                "],\n" +
                "\"decision\": null\n" +
                "},\n" +
                "\"academicPerformanceImpact\": 7,\n" +
                "\"socialLifeImpact\": 4,\n" +
                "\"healthImpact\": 6,\n" +
                "\"happinessImpact\": 9,\n" +
                "\"attendanceImpact\": 5\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"Option 2\",\n" +
                "\"storyline\": {\n" +
                "\"title\": \"Option 2 Storyline\",\n" +
                "\"events\": [\n" +
                "\"Option 2 Event 1\",\n" +
                "\"Option 2 Event 2\",\n" +
                "\"Option 2 Event 3\"\n" +
                "],\n" +
                "\"decision\": null\n" +
                "},\n" +
                "\"academicPerformanceImpact\": 5,\n" +
                "\"socialLifeImpact\": 8,\n" +
                "\"healthImpact\": 3,\n" +
                "\"happinessImpact\": 6,\n" +
                "\"attendanceImpact\": 2\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}\n" +
                "\n" +
                "Make sure to fill in the appropriate values for academicPerformanceImpact, socialLifeImpact, healthImpact, happinessImpact, and attendanceImpact, depending on the consequences of each option with each value no more than 8 and no less than -8.\n" +
                "\n" +
                "The output should be JSON data and no other text.";

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .temperature(0.7)
                .prompt(prompt)
                .maxTokens(3022)
                .build();

        // Generate the story using OpenAI API
        String story = "";
        try {
            story = service.createCompletion(completionRequest).getChoices().get(0).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(prompt);
        return story;
    }
}
