package Werewolf.phase;

import Werewolf.Game;
import Werewolf.players.Class;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Day implements Phase {
    Game game = null;
    Class needed = null;
    boolean isPrivate = false;

    public Day(Game game) {
        this.game = game;
    }

    @Override
    public Class vote(Class player, int shortcut) {
        if (!player.hasVoted()) {
            Class voted = game.getPlayers().stream().filter(n -> n.getShortcut() == shortcut).findFirst().get();
            voted.incVoteCounts();
            player.setHasVoted(true);
            return voted;
        } else {
            return null;
        }
    }

    @Override
    public void narrator(MessageChannel channel) {
        channel.sendMessage("The Village awakes.").queue();
        channel.sendMessage("The Village chooses their Target.").queue();
    }

    @Override
    public boolean allVoted() {
        return game.allVoted();
    }


    @Override
    public Object action(Class player) {
        game.removePlayer(player);
        return Math.random() < 0.5 ? "The Villagers killed " + player.getPlayer().getAsMention() + "." :
                "The Villagers hung " + player.getPlayer().getAsMention() + ".";
    }

    @Override
    public void nextPhase() {
        game.setPhase(new WerewolfPhase(game));
    }
}
