import org.junit.jupiter.api.BeforeEach;

public class ForkResetTest extends ForkTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(fork.proceed());
        fork.reset();
        model = ScheduleModel.getFreshModel();
    }
}
