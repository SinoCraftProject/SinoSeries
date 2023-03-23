package games.moegirl.sinocraft.sinocore.old.capability;

import games.moegirl.sinocraft.sinocore.capability.TagCapabilitySerializable;

public class QuizzingPlayerProvider extends TagCapabilitySerializable<IQuizzingPlayer> {

    public QuizzingPlayerProvider() {
        super(SCCapabilities.QUIZZING_PLAYER_CAPABILITY, QuizzingPlayer::new);
    }
}
