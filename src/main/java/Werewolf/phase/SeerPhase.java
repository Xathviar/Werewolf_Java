package Werewolf.phase;

import Werewolf.Game;
import Werewolf.players.Class;
import Werewolf.players.Seer;
import net.dv8tion.jda.core.entities.MessageChannel;

public class SeerPhase implements Phase {
    Game game;
    Class neededClass = new Seer();
    boolean isPrivate = true;

    public SeerPhase(Game game) {
        this.game = game;
    }

    @Override
    public void narrator(MessageChannel channel) {
        channel.sendMessage("The Seer awakes.").queue();
        channel.sendMessage("The Seer chooses its target.").queue();

    }

    @Override
    public String action(Class player) {
        return player.getPlayer().getAsMention() + " is  a " + player.getClass().toString();
    }

    @Override
    public void nextPhase() {
        game.setPhase(new WerewolfPhase(game));
    }
}
