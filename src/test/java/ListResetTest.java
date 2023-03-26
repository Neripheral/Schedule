import org.junit.jupiter.api.BeforeEach;

public class ListResetTest extends ListTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        list.reset();
        model = ScheduleModel.getFreshModel();
    }
}
