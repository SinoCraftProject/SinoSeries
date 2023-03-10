package games.moegirl.sinocraft.sinocore.old.config.model;

public class NewRankRecordModel {
    public String key;
    public TimeSpan time;
    public RankBoardModel.PlayerData player;
    public boolean successful;

    public static class TimeSpan {
        public long start;
        public long end;
    }
}
