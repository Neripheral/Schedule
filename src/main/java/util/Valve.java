package util;

public class Valve {
    private boolean blocked = false;

    public void whenUnblockedDo(Runnable functionToPerform) {
        if(!blocked)
            functionToPerform.run();
    }

    public void blockBy(Object valveTest) {
        blocked = true;
    }
}
