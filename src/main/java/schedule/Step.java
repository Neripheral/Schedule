package schedule;

import java.util.Objects;

public class Step implements Schedule{
    private final Runnable procedure;
    private boolean isDone = false;

    public Step(Runnable procedure){
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
