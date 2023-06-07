package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.S;
import schedule.Schedule;

import static com.google.common.truth.Truth.assertThat;

public class DirectorTest {


    private static class Model{
        public boolean hasBeenChanged = false;
    }

    private static final Event preEvent = new Event(){};
    private static final Event postEvent = new Event() {};

    private static Schedule generateSchedule(S.EventReceiver er, Model model) {
        return S.list(
                S.event(er, preEvent),
                S.perform(()->model.hasBeenChanged = true),
                S.event(er, postEvent)
        );
    }

    private Model model;
    //private List<Event> eventList;
    private Director director;

    @BeforeEach
    protected void setUp() {
        model = new Model();
        //eventList = new ArrayList<>();
        director = new Director(generateSchedule(/*eventList::add*/e->{}, model));
    }

    @Test
    public void startingWithNoParticipantsExecutesWholeSchedule() {
        director.start();

        assertThat(model.hasBeenChanged).isTrue();
    }
}
