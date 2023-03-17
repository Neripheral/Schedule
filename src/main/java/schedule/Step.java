package schedule;

import java.util.Objects;

class Step implements Schedule{
    private final Runnable procedure;
    private boolean isDone = false;

    public static Step of(Runnable procedure){
        return new Step(procedure);
    }

    private Step(Runnable procedure){
        this.procedure = Objects.requireNonNull(procedure);
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
}
