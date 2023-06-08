package util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Participant {
    void onEventReceived(Event event, Controller controller);

    static Participant doing(Consumer<Event> action) {
        return (event, controller) -> action.accept(event);
    }

    static Participant withHalting(BiConsumer<Event, Controller> action) {
        return action::accept;
    }
}
