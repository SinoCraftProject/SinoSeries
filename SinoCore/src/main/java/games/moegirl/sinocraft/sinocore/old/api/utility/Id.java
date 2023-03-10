package games.moegirl.sinocraft.sinocore.old.api.utility;

public class Id {

    private int id;

    public synchronized int nextId() {
        return id++;
    }
}
