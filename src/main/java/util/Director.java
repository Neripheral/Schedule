package util;

import schedule.ReceiverlessSchedule;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Director implements Controller{
    private final ReceiverlessSchedule receiverlessSchedule;
    private Schedule schedule;
    private final List<Participant> participants;
    private final Valve valve;
    private boolean hasFinished;

    public Director(ReceiverlessSchedule schedule) {
        this.receiverlessSchedule = schedule;
        participants = new ArrayList<>();
        valve = new Valve();
        hasFinished = false;
    }

    private void onEventReceived(Event event){
        participants.forEach(p->p.onEventReceived(event, this));
    }

    public void start() {
        schedule = receiverlessSchedule.forReceiver(this::onEventReceived);
        while(hasFinished){
            tryProceed();
        }
    }

    private void tryProceed(){
        valve.whenUnblockedDo(()->{
            if(!schedule.proceed())
                hasFinished = true;
        });
    }

    public void addParticipant(Participant doing) {
        participants.add(doing);
    }

    @Override
    public void stopFor(Object token) {
        valve.blockBy(token);
    }

    @Override
    public void resumeFor(Object token) {
        valve.unblockFrom(token);
    }
}
