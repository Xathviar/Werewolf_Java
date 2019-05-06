package Werewolf.phase;

import Werewolf.Game;
import Werewolf.Player;
import Werewolf.players.Class;

public interface Night {
    Game game = null;
    Class needed = null;

    default Player vote(Class player, int shortcut) {
        if (player.getClass().equals(needed.getClass()) && !player.hasVoted()) {
            Player voted = game.getPlayers().stream().filter(n -> n.getShortcut() == shortcut).findFirst().get();
            voted.incVoteCounts();
            player.setHasVoted(true);
            if (allVoted()) {
                return game.getMostVoted();
            }
            return voted;
        }else {
            return null;
        }
    }

    default boolean allVoted () {
        return game.allVoted(needed);
    }
}
