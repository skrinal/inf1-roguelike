package model;

public class Status {
    private final String name;
    private int remainingTurns;

    public Status(String name, int remainingTurns) {
        this.name = name;
        this.remainingTurns = remainingTurns;
    }

    public String getName() {
        return this.name;
    }

    public int getRemainingTurns() {
        return this.remainingTurns;
    }

    public void decrementTurns() {
        if (this.remainingTurns > 0) {
            this.remainingTurns--;
        }
    }

    public boolean isExpired() {
        return this.remainingTurns == 0;
    }

}

