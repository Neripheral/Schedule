package util;

import schedule.Schedule;

public class Director {
    private final Schedule schedule;

    public Director(Schedule schedule) {
        this.schedule = schedule;
    }

    public void start() {
        while(schedule.proceed());
    }
}
