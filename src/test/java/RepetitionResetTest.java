import org.junit.jupiter.api.BeforeEach;

public class RepetitionResetTest extends RepetitionTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(repetition.proceed());
        model = ScheduleModel.getFreshModel();
        repetition.reset();
    }
}
