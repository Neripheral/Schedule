package util;

import java.util.function.Consumer;

public interface Participant {
    void onEventReceived(Event event);

    static Participant doing(Consumer<Event> action) {
        return action::accept;
    }
}
