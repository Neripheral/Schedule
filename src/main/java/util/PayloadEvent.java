package util;

public abstract class PayloadEvent<T> implements Event {
    public PayloadEvent(T payload) {
        this.payload = payload;
    }

    @Override
    public boolean hasPayload() {
        return true;
    }

    @Override
    public T getPayload() {
        return payload;
    }

    private final T payload;
}
