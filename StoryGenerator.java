import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Scanner;

public class StoryGenerator {

    public static void main(String[] args) {
        // Prompt the user for inputs about their university experience
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the University Adventure Game!");
        System.out.println("Please answer the following questions to generate your adventure:");
        System.out.print("What happened?\n");
        String decisions = scanner.nextLine();


        String storyline = generateStoryline(decisions);
        System.out.println("\nGenerating your adventure...");
        // Print the generated story
        System.out.println("\nHere's your adventure:");
        System.out.println(storyline);
        StorylineParser.parseJson(storyline);
    }

    public static String generateStoryline(String decisions) {
        // Initialize the OpenAI API service
        String token = System.getenv("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(90));

        // Construct the prompt
        String prompt = "Create a creative storyline object progressing from player's prior events given that they are in the middle of their experience as a student in university so do not reference which stage they are in their university experience.\n" +
                "Those events being:" + decisions + "\n" +
                "\n" +
                "To create the storyline object, consider the following aspects:\n" +
                "\n" +
                "What social situations may come of the events that have just transpired? How will the player react?\n" +
                "The player's current situation: What is the player currently doing? Are they in school, working, or something else?\n" +
                "The player's interests: What are the player's interests and hobbies? Are they interested in any particular field of study or activity?\n" +
                "The player's personality: What are the player's personality traits? Are they introverted or extroverted? Are they outgoing or reserved?\n" +
                "The decision: What drastic decision will the player need to make, and what are the consequences of each option? How will the decision affect the player's academic performance, social life, health, happiness, and attendance?\n" +
                "\n" +
                "When creating the storyline object, try to incorporate unexpected and surprising yet realistic twists, unique and interesting characters, and engaging plot points. The storyline must be drastically different than the one given as the input.\n" +
                "Try not to make include the idea of balancing in the story and when prompting the player to decide between two options, give the character situations to choose from rather than outright telling them to focus on one or the other.\n" +
                "Each event should be two sentences and should be taking place in the third person and referring to the player as 'You'.\n" +
                "Relate the story to the previous events but do not base the options upon it, only progress further\n" +
                "Make sure the story is progressing from the given storyline and not being continuously focused on the same topics. Different types of new events could include attending parties, finding love, having arguments with friends and so on.\n" +
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
                "\"academicPerformanceImpact\": 0,\n" +
                "\"socialLifeImpact\": 0,\n" +
                "\"healthImpact\": 0,\n" +
                "\"happinessImpact\": 0,\n" +
                "\"attendanceImpact\": 0\n" +
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
                "\"academicPerformanceImpact\": 0,\n" +
                "\"socialLifeImpact\": 0,\n" +
                "\"healthImpact\": 0,\n" +
                "\"happinessImpact\": 0,\n" +
                "\"attendanceImpact\": 0\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}\n" +
                "\n" +
                "Make sure to fill in the appropriate values for academicPerformanceImpact, socialLifeImpact, healthImpact, happinessImpact, and attendanceImpact, depending on the consequences of each option with each value no more than 8 and no less than -8." +
                "Please note the output should be JSON data and nothing else.\n" +
                "\n";

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
