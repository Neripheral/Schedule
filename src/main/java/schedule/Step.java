package schedule;

import java.util.Objects;

class Step implements Schedule{
    private final Runnable procedure;
    private final String description;
    private boolean isDone = false;

    public Step(Runnable procedure){
        this.procedure = Objects.requireNonNull(procedure);
        this.description = "unspecified";
    }

    public Step(Runnable procedure, String description){
        this.procedure = Objects.requireNonNull(procedure);
        this.description = Objects.requireNonNull(description);
    }

    @Override
    public boolean proceed() {
        if(isDone)
            return false;
        procedure.run();
        isDone = true;
        return true;
    }

    @Override
    public void reset() {
        isDone = false;
    }

    @Override
    public String toString() {
        String checkmarkText =
                isDone ? "X" : " ";
        return String.format("[%s] %s", checkmarkText, description);
    }
}
