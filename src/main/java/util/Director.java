package util;

import schedule.ReceiverlessSchedule;
import schedule.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Director {
    private final ReceiverlessSchedule receiverlessSchedule;
    private Schedule schedule;
    private final List<Participant> participants;

    public Director(ReceiverlessSchedule schedule) {
        this.receiverlessSchedule = schedule;
        participants = new ArrayList<>();
    }

    private void onEventReceived(Event event){
        participants.forEach(p->p.onEventReceived(event));
    }

    public void start() {
        schedule = receiverlessSchedule.forReceiver(this::onEventReceived);
        while(schedule.proceed());
    }

    public void addParticipant(Participant doing) {
        participants.add(doing);
    }
}
