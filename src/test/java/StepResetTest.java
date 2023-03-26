import org.junit.jupiter.api.BeforeEach;

public class StepResetTest extends StepTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(s.proceed());
        s.reset();
        model = ScheduleModel.getFreshModel();
    }
}
