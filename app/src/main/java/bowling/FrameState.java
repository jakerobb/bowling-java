package bowling;

public enum FrameState {
    OPEN(2, 0),
    SPARE(2, 1),
    STRIKE(1, 2);

    private final int maxRolls;
    private final int initialRolls;
    private final int additionalRolls;

    FrameState(int initialRolls, int additionalRolls) {
        this.initialRolls = initialRolls;
        this.maxRolls = initialRolls + additionalRolls;
        this.additionalRolls = additionalRolls;
    }

    public int getMaxRolls() {
        return maxRolls;
    }

    public int getInitialRolls() {
        return initialRolls;
    }

    public int getAdditionalRolls() {
        return additionalRolls;
    }
}
