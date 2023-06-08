package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ValveTest {
    Valve valve;

    @BeforeEach
    protected void setUp() {
        hasBeenChanged = false;
        valve = new Valve();
    }

    private boolean hasBeenChanged;

    private void doStuff(){
        hasBeenChanged = true;
    }

    @Test
    public void unblockedValveAllowsForFreeFlow() {
        valve.whenUnblockedDo(this::doStuff);

        assertThat(hasBeenChanged).isTrue();
    }

    @Test
    public void blockingValvePreventsFunctionFromPerforming() {
        valve.blockBy(this);
        valve.whenUnblockedDo(this::doStuff);

        assertThat(hasBeenChanged).isFalse();
    }

    @Test
    public void unblockingValveCallsDoStuffMethod() {
        valve.blockBy(this);
        valve.whenUnblockedDo(this::doStuff);
        valve.unblockFrom(this);

        assertThat(hasBeenChanged).isTrue();
    }

    @Test
    public void unblockingTwicePerformsDoStuffOnlyOnce() {
        valve.blockBy(this);
        valve.whenUnblockedDo(this::doStuff);
        valve.unblockFrom(this);
        hasBeenChanged = false;

        valve.blockBy(this);
        valve.unblockFrom(this);

        assertThat(hasBeenChanged).isFalse();
    }

    @Test
    public void unblockingPartiallyDoesNotUnblockTheFlow() {
        Object blockage = new Object();
        valve.blockBy(this);
        valve.blockBy(blockage);
        valve.whenUnblockedDo(this::doStuff);

        valve.unblockFrom(this);

        assertThat(hasBeenChanged).isFalse();
    }

    @Test
    public void throwsWhenUnblockingFromSomethingThatWasNotBlocking() {
    }
}
