package games.moegirl.sinocraft.sinocore.event;

public class EventCancelException extends RuntimeException {
    public static final EventCancelException INSTANCE = new EventCancelException();

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
