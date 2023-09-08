import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class StorylineParser {
    public static void main(String[] args) {
        String storyjsn = "{\n" +
                "\"title\": \"A Study in Computer Science\",\n" +
                "\"events\": [\n" +
                "\"The player has started university and is studying Computer Science.\",\n" +
                "\"The player is struggling to juggle their studies, sports and social life.\",\n" +
                "\"The player's grades are slipping and they are feeling overwhelmed and exhausted.\"\n" +
                "],\n" +
                "\"decision\": {\n" +
                "\"title\": \"How should the player tackle their academic workload?\",\n" +
                "\"options\": [\n" +
                "{\n" +
                "\"title\": \"Focus on academics\",\n" +
                "\"storyline\": {\n" +
                "\"title\": \"Focusing on Academics\",\n" +
                "\"events\": [\n" +
                "\"The player decides to focus their energy solely on their academics and stops playing sports and attending social events.\",\n" +
                "\"The player's grades begin to improve and they gain a better understanding of the course material.\",\n" +
                "\"The player becomes more confident in their academic abilities.\"\n" +
                "],\n" +
                "\"decision\": null\n" +
                "},\n" +
                "\"academicPerformanceImpact\": 8,\n" +
                "\"socialLifeImpact\": -8,\n" +
                "\"healthImpact\": -4,\n" +
                "\"happinessImpact\": -3,\n" +
                "\"attendanceImpact\": -5\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"Maintain balance\",\n" +
                "\"storyline\": {\n" +
                "\"title\": \"Maintaining Balance\",\n" +
                "\"events\": [\n" +
                "\"The player decides to maintain a balance between their studies, sports and social life.\",\n" +
                "\"The player completes their coursework while continuing to participate in sports and attending social events.\",\n" +
                "\"The player finds a better balance between their academic and personal life and thus improves their overall performance.\"\n" +
                "],\n" +
                "\"decision\": null\n" +
                "},\n" +
                "\"academicPerformanceImpact\": 6,\n" +
                "\"socialLifeImpact\": 4,\n" +
                "\"healthImpact\": 6,\n" +
                "\"happinessImpact\": 7,\n" +
                "\"attendanceImpact\": 3\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}";
        Storyline storyline = parse(storyjsn);
            System.out.println("\n" + storyline.getTitle());

            // Print out the storyline details
            System.out.println("Events:");
            for (String event : storyline.getEvents()) {
                System.out.println("- " + event);
            }
            System.out.println("Decision: " + storyline.getDecision().getPrompt());
            for (Option option : storyline.getDecision().getOptions()) {
                System.out.println("- " + option.getText());
                String events = "";
                for (String event : option.getOutcome().getEvents()) {
                    System.out.println("- " + event);
                    events += event + "\n";
                }
                System.out.println(events);
            }
        }

        public static void parseJson (String input){
            Storyline storyline = parse(input);
            System.out.println("\n" + storyline.getTitle());

            // Print out the storyline details
            System.out.println("Events:");
            for (String event : storyline.getEvents()) {
                System.out.println("- " + event);
            }
            System.out.println("Decision: " + storyline.getDecision().getPrompt());
            for (Option option : storyline.getDecision().getOptions()) {
                System.out.println("- " + option.getText());
                for (String event : option.getOutcome().getEvents()) {
                    System.out.println("- " + event);
                }
            }
        }

    private static class JsonStoryline {
        String title;
        List<String> events;
        JsonDecision decision;
    }

    private static class JsonDecision {
        String title;
        List<JsonOption> options;
    }

    private static class JsonOption {
        String title;
        JsonStoryline storyline;
        int academicPerformanceImpact;
        int socialLifeImpact;
        int healthImpact;
        int happinessImpact;
        int attendanceImpact;
    }

    public static Storyline parse(String json) {
        Gson gson = new Gson();
        JsonStoryline jsonStoryline = gson.fromJson(json, JsonStoryline.class);

        String title = jsonStoryline.title;
        List<String> events = jsonStoryline.events;
        Decision decision = parseDecision(jsonStoryline.decision);

        return new Storyline(title, events, decision);
    }

    private static Decision parseDecision(JsonDecision jsonDecision) {
        String title = jsonDecision.title;
        List<Option> options = new ArrayList<>();

        for (JsonOption jsonOption : jsonDecision.options) {
            String optionTitle = jsonOption.title;
            Storyline optionStoryline = parseStoryline(jsonOption.storyline);
            int academicPerformanceImpact = jsonOption.academicPerformanceImpact;
            int socialLifeImpact = jsonOption.socialLifeImpact;
            int healthImpact = jsonOption.healthImpact;
            int happinessImpact = jsonOption.happinessImpact;
            int attendanceImpact = jsonOption.attendanceImpact;

            options.add(new Option(optionTitle, optionStoryline, academicPerformanceImpact, socialLifeImpact,
                    healthImpact, happinessImpact, attendanceImpact));
        }

        return new Decision(title, options);
    }

    private static Storyline parseStoryline(JsonStoryline jsonStoryline) {
        String title = jsonStoryline.title;
        List<String> events = jsonStoryline.events;
        Decision decision = jsonStoryline.decision == null ? null : parseDecision(jsonStoryline.decision);

        return new Storyline(title, events, decision);
    }
}
