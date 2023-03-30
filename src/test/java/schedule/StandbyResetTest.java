package schedule;

import org.junit.jupiter.api.BeforeEach;

public class StandbyResetTest extends StandbyTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        standby.proceed();
        model.age = 10;
        while(standby.proceed());
        standby.reset();
        model = ScheduleModel.getFreshModel();
    }
}
