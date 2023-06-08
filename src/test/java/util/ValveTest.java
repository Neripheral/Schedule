package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        valve.blockBy(this);

        Object unobtrusiveBlockage = new Object();
        Executable shadyFunction = ()->valve.unblockFrom(unobtrusiveBlockage);

        assertThrows(IllegalArgumentException.class, shadyFunction);
    }

    @Test
    public void throwsWhenPassedNullAsArgument_blockBy(){
        Executable shadyFunction = ()->valve.blockBy(null);

        assertThrows(IllegalArgumentException.class, shadyFunction);
    }
    
    @Test
    public void throwsWhenPassedNullAsArgument_unblockFrom(){
        Executable shadyFunction = ()->valve.unblockFrom(null);
        
        assertThrows(IllegalArgumentException.class, shadyFunction);
    }
}
