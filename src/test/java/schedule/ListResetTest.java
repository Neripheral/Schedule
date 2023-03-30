package schedule;

import org.junit.jupiter.api.BeforeEach;

public class ListResetTest extends ListTest {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        while(list.proceed());
        list.reset();
        model = ScheduleModel.getFreshModel();
    }
}
