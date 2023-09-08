import java.util.List;

public class Storyline {
    private final String title;
    private final List<String> events;
    private final Decision decision;

    public Storyline(String title, List<String> events, Decision decision) {
        this.title = title;
        this.events = events;
        this.decision = decision;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getEvents() {
        return events;
    }

    public Decision getDecision() {
        return decision;
    }
}

class Decision {
    private final String prompt;
    private final List<Option> options;

    public Decision(String prompt, List<Option> options) {
        this.prompt = prompt;
        this.options = options;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<Option> getOptions() {
        return options;
    }
}

class Option {
    private final String text;
    private final Storyline outcome;
    private final int socialLifeChange;
    private final int gradesChange;
    private final int healthChange;
    private final int happinessChange;
    private final int attendanceChange;

    public Option(String text, Storyline outcome, int socialLifeChange, int gradesChange,
                  int healthChange, int happinessChange, int attendanceChange) {
        this.text = text;
        this.outcome = outcome;
        this.socialLifeChange = socialLifeChange;
        this.gradesChange = gradesChange;
        this.healthChange = healthChange;
        this.happinessChange = happinessChange;
        this.attendanceChange = attendanceChange;
    }

    public String getText() {
        return text;
    }

    public Storyline getOutcome() {
        return outcome;
    }

    public int getSocialLifeChange() {
        return socialLifeChange;
    }

    public int getGradesChange() {
        return gradesChange;
    }

    public int getHealthChange() {
        return healthChange;
    }

    public int getHappinessChange() {
        return happinessChange;
    }

    public int getAttendanceChange() {
        return attendanceChange;
    }
}
