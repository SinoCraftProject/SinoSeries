package games.moegirl.sinocraft.sinocore.old.config.model;

import java.util.UUID;

public class RankBoardModel {
    public Rank[] ranks;

    public class Rank {
        public int rank;
        public PlayerData player;
        public long timeUsed;
        public int tries;
    }

    public class BestModel {
        public long best;
        public PlayerData player;
    }

    public static class PlayerData {
        public String id;
        public UUID uuid;
    }
}
