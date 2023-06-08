package util;

import java.util.ArrayList;
import java.util.List;

public class Valve {
    private final List<Object> blockages = new ArrayList<>();
    private Runnable onFlowUnblocked;

    public void whenUnblockedDo(Runnable functionToPerform) {
        onFlowUnblocked = functionToPerform;
    }

    private void tryResumeFlow(){
        if(onFlowUnblocked != null && blockages.isEmpty()) {
            Runnable toDo = onFlowUnblocked;
            onFlowUnblocked = null;
            toDo.run();
        }
    }

    public void blockBy(Object valveTest) {
        blockages.add(valveTest);
    }

    public void unblockFrom(Object valveTest) {
        blockages.remove(valveTest);
        tryResumeFlow();
    }
}
