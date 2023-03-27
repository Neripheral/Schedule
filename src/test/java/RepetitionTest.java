import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import schedule.Repetition;
import schedule.Schedule;
import schedule.Step;

import static com.google.common.truth.Truth.assertThat;

class RepetitionTest {
    protected Schedule repetition;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        repetition = new Repetition(
                ()->(model.age > 9) && (model.age <= 20),
                new Step(()->model.age--)
        );
        model = ScheduleModel.getFreshModel();
    }

    @Test
    public void repetitionRepeatsSchedule() {
        int i = 0;
        while(repetition.proceed())
            i++;
        assertThat(i).isGreaterThan(1);
    }

    @Test
    public void repetitionDoesNothingIfConditionUnsatisfiedInitially() {
        model.age = 30;
        assertThat(repetition.proceed()).isFalse();
    }

    @Test
    public void repetitionStopsRepeatingWhenConditionNotSatisfied() {
        assertThat(repetition.proceed()).isTrue();
        model.age = 5;
        assertThat(repetition.proceed()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,5,10,20,50,100,101})
    public void repetitionExecutesCorrectNumberOfTimes(int repeatCount) {
        for(int i = 0; i < repeatCount; i++){
            model.age = 10;
            assertThat(repetition.proceed()).isTrue();
        }
        assertThat(repetition.proceed()).isFalse();
    }
}