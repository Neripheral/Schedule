import org.junit.jupiter.api.BeforeEach;

public class DuoResetTest extends DuoTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(duo.proceed());
        duo.reset();
        model = ScheduleModel.getFreshModel();
    }
}
