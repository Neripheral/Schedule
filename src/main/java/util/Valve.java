package util;

public class Valve {
    private boolean isBlocked = false;
    private Runnable onFlowUnblocked;

    public void whenUnblockedDo(Runnable functionToPerform) {
        onFlowUnblocked = functionToPerform;
    }

    private void tryResumeFlow(){
        if(onFlowUnblocked != null && !isBlocked)
            onFlowUnblocked.run();
    }

    public void blockBy(Object valveTest) {
        isBlocked = true;
    }

    public void unblockFrom(Object valveTest) {
        isBlocked = false;
        tryResumeFlow();
    }
}
