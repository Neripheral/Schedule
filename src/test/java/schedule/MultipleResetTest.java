package schedule;

import org.junit.jupiter.api.BeforeEach;

public class MultipleResetTest extends MultipleTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(multiple.proceed());
        multiple.reset();
        model = ScheduleModel.getFreshModel();
    }
}
