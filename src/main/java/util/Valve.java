package util;

public class Valve {

    public void whenUnblockedDo(Runnable functionToPerform) {
        functionToPerform.run();
    }
}
