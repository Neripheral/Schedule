package util;

public interface Event {
    default boolean isOfType(Class<? extends Event> eventType) {
        return this.getClass() == eventType;
    }

    default boolean hasPayload(){
        return false;
    }

    default Object getPayload(){
        throw new UnsupportedOperationException(
                "Getting a payload hasn't been supplied by a subclass.");
    }
}
