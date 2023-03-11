package games.moegirl.sinocraft.sinocore.old.capability;

public class QuizzingPlayerProvider extends TagCapabilitySerializable<IQuizzingPlayer> {

    public QuizzingPlayerProvider() {
        super(SCCapabilities.QUIZZING_PLAYER_CAPABILITY, QuizzingPlayer::new);
    }
}
