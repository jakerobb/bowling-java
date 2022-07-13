package bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Frame {
    private final List<Integer> rolls;
    private FrameState state;

    public Frame() {
        this.rolls = new ArrayList<>();
        state = FrameState.OPEN;
    }

    public void addRoll(int roll) {
        if (needsMoreRolls()) {
            rolls.add(roll);
        } else {
            throw new IllegalStateException("Can't add more rolls: state: " + state + " rolls:" + rolls);
        }

        switch (rolls.size()) {
            case 1 -> {
                if (roll == 10) {
                    state = FrameState.STRIKE;
                }
            }
            case 2 -> {
                if (roll + rolls.get(0) == 10) {
                    state = FrameState.SPARE;
                }
            }
        }
    }

    public FrameState getState() {
        return state;
    }

    public int score() {
        return rolls.stream().reduce(0, Integer::sum);
    }

    public String getScoreSheetString() {
        return switch (getState()) {
            case STRIKE -> "X  ";
            case SPARE -> String.format("%s /", getRollStringForScoreSheet(0));
            case OPEN -> String.format("%s %s", getRollStringForScoreSheet(0), getRollStringForScoreSheet(1));
        };
    }
    public String getScoreSheetStringForLastFrame() {
        return switch (getState()) {
            case STRIKE -> String.format("X %s %s", getRollStringForScoreSheet(1), getRollStringForScoreSheet(2));
            case SPARE -> String.format("%s / %s", getRollStringForScoreSheet(0), getRollStringForScoreSheet(2));
            case OPEN -> String.format("%s %s  ", getRollStringForScoreSheet(0), getRollStringForScoreSheet(1));
        };
    }

    private String getRollStringForScoreSheet(int index) {
        if (rolls.size() > index) {
            int roll = rolls.get(index);
            if (roll == 10) {
                return "X";
            } else {
                return Integer.toString(roll);
            }
        } else {
            return " ";
        }
    }

    public boolean needsInitialRolls() {
        return rolls.size() < state.getInitialRolls();
    }

    public boolean needsMoreRolls() {
        return rolls.size() < state.getMaxRolls();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Frame.class.getSimpleName() + "[", "]")
                .add("rolls=" + rolls)
                .add("state=" + state)
                .add("score=" + score())
                .add("needsInitialRolls=" + needsInitialRolls())
                .add("needsMoreRolls=" + needsMoreRolls())
                .toString();
    }

    public boolean hasRolls() {
        return !rolls.isEmpty();
    }
}
