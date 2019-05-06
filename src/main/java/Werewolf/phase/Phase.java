package Werewolf.phase;

import Werewolf.Game;
import Werewolf.players.Class;
import net.dv8tion.jda.core.entities.MessageChannel;

public interface Phase {
    Game game = null;
    Class needed = null;
    boolean isPrivate = false;

    default Class vote(Class player, int shortcut) {
        if (player.getClass().equals(needed.getClass()) && !player.hasVoted()) {
            Class voted = game.getPlayers().stream().filter(n -> n.getShortcut() == shortcut).findFirst().get();
            voted.incVoteCounts();
            player.setHasVoted(true);
            return voted;
        } else {
            return null;
        }
    }

    void narrator(MessageChannel channel);

    default boolean allVoted() {
        return game.allVoted(needed);
    }

    Object action(Class player);

    void nextPhase();
}