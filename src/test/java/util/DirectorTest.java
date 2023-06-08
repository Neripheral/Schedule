package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import schedule.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class DirectorTest {
    private static class Model{
        public boolean hasBeenChanged = false;
    }

    private static final Event preEvent = new Event(){};
    private static final Event postEvent = new Event() {};

    private static ReceiverlessSchedule getScheduleBuilder(Model model) {
        return S.receiverlessSchedule(er->S.list(
                S.event(er, preEvent),
                S.perform(()->model.hasBeenChanged = true),
                S.event(er, postEvent)
        ));
    }

    private Model model;
    private List<Event> eventList;
    private Director director;

    @BeforeEach
    protected void setUp() {
        model = new Model();
        eventList = new ArrayList<>();
        director = new Director(getScheduleBuilder(model));
        resumeFlowList = new ArrayList<>();
    }

    @Test
    public void startingWithNoParticipantsExecutesWholeSchedule() {
        director.start();

        assertThat(model.hasBeenChanged).isTrue();
    }

    private void addEvent(Event event){
        eventList.add(event);
    }

    @Test
    public void startingWithOneParticipantExecutesWholeScheduleAndAddsEvents() {
        director.addParticipant(Participant.doing(this::addEvent));
        director.start();

        assertThat(eventList).containsExactly(preEvent, postEvent);
    }

    private List<Runnable> resumeFlowList;

    private void onEventWithHalting(Event event, Controller controller){
        addEvent(event);
        if(event.isOfType(preEvent.getClass())) {
            controller.stopFor(this);
            resumeFlowList.add(()->controller.resumeFor(this));
        }
    }

    @Test
    public void participantCanHaltDirectorsProgress() {
        director.addParticipant(Participant.withHalting(this::onEventWithHalting));
        director.start();

        assertThat(model.hasBeenChanged).isFalse();
    }

    @Test
    public void whenUnhaltedDirectorContinues() {
        director.addParticipant(Participant.withHalting(this::onEventWithHalting));
        director.start();

        resumeFlowList.get(0).run();

        assertThat(model.hasBeenChanged).isTrue();
    }

    @Test
    public void whenUnhaltedFromMultipleHaltsDirectorContinues() {
        director.addParticipant(Participant.withHalting(this::onEventWithHalting));
        director.addParticipant(Participant.withHalting(this::onEventWithHalting));
        director.start();

        assertThat(resumeFlowList).hasSize(2);
        assertThat(model.hasBeenChanged).isFalse();

        resumeFlowList.get(0).run();
        assertThat(model.hasBeenChanged).isFalse();

        resumeFlowList.get(1).run();
        assertThat(model.hasBeenChanged).isTrue();
    }
}
