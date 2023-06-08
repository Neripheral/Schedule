package util;

import schedule.ReceiverlessSchedule;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Director implements Controller{
    public Director(ReceiverlessSchedule schedule) {
        this.receiverlessSchedule = schedule;
        participants = new ArrayList<>();
        valve = new Valve();
        hasFinished = false;
    }

    public void start() {
        schedule = receiverlessSchedule.forReceiver(this::onEventReceived);
        tryProceed();
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    @Override
    public void stopFor(Object token) {
        valve.blockBy(token);
    }

    @Override
    public void resumeFor(Object token) {
        valve.unblockFrom(token);
    }



    private final ReceiverlessSchedule receiverlessSchedule;
    private Schedule schedule;
    private final List<Participant> participants;
    private final Valve valve;
    private boolean hasFinished;

    private void tryProceed(){
        if(hasFinished)
            return;
        valve.whenUnblockedDo(()->{
            if(!schedule.proceed())
                hasFinished = true;
            tryProceed();
        });
    }


    private void onEventReceived(Event event){
        participants.forEach(p->p.onEventReceived(event, this));
    }
}
