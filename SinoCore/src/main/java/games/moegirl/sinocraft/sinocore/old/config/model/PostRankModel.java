package games.moegirl.sinocraft.sinocore.old.config.model;

public class PostRankModel {
    public String key;
    public NewRankRecordModel.TimeSpan time;
    public RankBoardModel.PlayerData player;
    public boolean successful;

    public static class Response {
        public boolean best;
    }
}
