package Werewolf.phase;

import Werewolf.Game;
import Werewolf.players.Class;
import Werewolf.players.*;
import net.dv8tion.jda.core.entities.MessageChannel;

public class WerewolfPhase implements Phase {
    Game game;
    Class neededClass = new Werewolf();
    boolean isPrivate = true;

    public WerewolfPhase(Game game) {
        this.game = game;
    }

    @Override
    public void narrator(MessageChannel channel) {
        channel.sendMessage("The Village sleeps.").queue();
        channel.sendMessage("The Werewolves awake.").queue();
        channel.sendMessage("The Werewolves choose their prey.").queue();
    }

    @Override
    public String action(Class player) {
        game.removePlayer(player);
        return Math.random() < 0.5 ? "The Werewolves killed " + player.getPlayer().getAsMention() + "." :
                "The Werewolves filled their stomach by killing " + player.getPlayer().getAsMention() + ".";
    }

    @Override
    public void nextPhase() {
        game.setPhase(new Day(game));
    }
}