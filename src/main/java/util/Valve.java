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

    public void blockBy(Object token) {
        if(token == null)
            throw new IllegalArgumentException("Token cannot be null");
        blockages.add(token);
    }

    public void unblockFrom(Object token) {
        if(token == null)
            throw new IllegalArgumentException("Token cannot be null");
        if(!blockages.remove(token))
            throw new IllegalArgumentException("Attempting to unblock from object that is not blocking. Object: " + token);
        tryResumeFlow();
    }
}
