package schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class MultipleTest {
    protected Schedule multiple;
    protected ScheduleModel model;

    @BeforeEach
    protected void setUp() {
        multiple = new Multiple(5,
                new Step(()->model.age++, "+1 to age")
        );
        model = ScheduleModel.getFreshModel();
    }

    @Test
    public void multipleBlocksItselfOnEnd() {
        while(multiple.proceed());
        assertThat(multiple.proceed()).isFalse();
    }

    @Test
    public void multipleExecutesCorrectNumberOfTimes() {
        int i = 0;
        while(multiple.proceed()) {
            i++;
        }
        assertThat(multiple.proceed()).isFalse();
        assertThat(i).isEqualTo(5);
    }

    @Test
    public void multipleModifiesModelCorrectly() {
        int correctAge = 21;
        while(multiple.proceed()){
            assertThat(model.age).isEqualTo(correctAge);
            correctAge++;
        }
        assertThat(model.age).isEqualTo(25);
    }

    @Test public void toStringReturnsCorrectString(){
        assertThat(multiple.toString()).isEqualTo(
                """
                        [ ] (0/5):
                        |[ ] +1 to age"""
        );
    }

    @Test public void toStringReturnsCorrectStringAfterCompletion(){
        while(multiple.proceed());
        assertThat(multiple.toString()).isEqualTo(
                """
                        [X] (5/5):
                        |[X] +1 to age"""
        );
    }
}