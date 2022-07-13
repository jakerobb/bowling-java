package bowling;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Frame> frames;

    public Game() {
        frames = new ArrayList<>();
        frames.add(new Frame());
    }

    /**
     * @return true if the game is over
     */
    public boolean roll(int pinCount) {
        boolean rollWasValid = false;
        for (Frame frame : frames) {
            if (frame.needsMoreRolls()) {
                frame.addRoll(pinCount);
                rollWasValid = true;
            }
        }
        if (!rollWasValid) {
            throw new IllegalStateException("No more rolls allowed!");
        }

        Frame lastFrame = frames.get(frames.size() - 1);
        if (!lastFrame.needsInitialRolls()) {
            if (frames.size() < 10) {
                frames.add(new Frame());
            } else {
                return !lastFrame.needsMoreRolls();
            }
        }

        return false;
    }

    public int score() {
        return frames.stream()
                     .map(Frame::score)
                     .reduce(0, Integer::sum);
    }

    public void printScoreSheet() {
        printHeaderRow();
        printRollsRow();
        printScoreRow();
    }

    private void printHeaderRow() {
        for (int i = 1; i <= 9; i++) {
            System.out.printf("|  %d  ", i);
        }
        System.out.println("|  10   | Total |");
    }

    private void printRollsRow() {
        for (int i = 0; i < 10; i++) {
            if (frames.size() > i) {
                Frame frame = frames.get(i);
                String frameScore;
                if (i == 9) {
                    frameScore = frame.getScoreSheetStringForLastFrame();
                } else {
                    frameScore = frame.getScoreSheetString();
                }
                System.out.printf("| %s ", frameScore);
            } else {
                System.out.print("|     ");
                if (i == 9) {
                    System.out.print("  ");
                }
            }
        }
        System.out.println("|       |");
    }

    private void printScoreRow() {
        int runningScore = 0;
        for (int i = 0; i < 10; i++) {
            if (frames.size() > i) {
                Frame frame = frames.get(i);
                if (frame.hasRolls()) {
                    runningScore += frame.score();
                    if (i == 9) {
                        System.out.printf("|%6s ", runningScore);
                    } else {
                        System.out.printf("|%4s ", runningScore);
                    }
                } else {
                    System.out.print("|     ");
                    if (i == 9) {
                        System.out.print("   ");
                    }
                }
            } else {
                System.out.print("|     ");
                if (i == 9) {
                    System.out.print("  ");
                }
            }
        }
        System.out.printf("|%6s ", runningScore);
        System.out.println("|");
    }
}
