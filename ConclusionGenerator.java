import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Scanner;

public class ConclusionGenerator {

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
        String prompt = "Create a conclusion storyline that wraps up the game, allowing the player to make one final decision, taking into account the player's prior decisions throughout their experience as a student in university in the UK. The storyline should lead to a natural endpoint, such as graduation or final exams..\n" +
                "Those decisions being:" + decisions + "\n" +
                "\n" +
                "To create the storyline object, consider the following aspects:\n" +
                "\n" +
                "The player's current situation: What is the player currently doing? What will they be doing after finishing university?\n" +
                "The player's interests: What are the player's interests and hobbies? Are they going to pursue a career in their hobbies or degree?\n" +
                "The player's personality: What are the player's personality traits? Are they introverted or extroverted? Are they outgoing or reserved?\n" +
                "\n" +
                "When creating the storyline object, try to incorporate unexpected and surprising yet realistic twists, unique and interesting characters, and engaging plot points. The storyline must be drastically different than the one given as the input.\n" +
                "Each event should be two sentences and should be taking place in the third person and referring to the player as 'You'.\n" +
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
                "Make sure to fill in the appropriate values for academicPerformanceImpact, socialLifeImpact, healthImpact, happinessImpact, and attendanceImpact, depending on the consequences of each option the value will increment no more than 8 and no less than -8." +
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
