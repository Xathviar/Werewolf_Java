package Werewolf.commands;

import Werewolf.Game;
import Werewolf.players.Class;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Start extends ListenerAdapter {
    private Game game;
    private Character prefix;

    public Start(Game game, Character prefix) {
        this.game = game;
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = game.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (game.getChannel() == null) {
            channel = event.getChannel();
        }
        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "start")) {
                if (!game.isGame()) {
                    channel.sendMessage("Please create a game!").queue();

                } else if (game.isRunningGame()) {
                    channel.sendMessage("There is already a running game.").queue();
                }else if (game.getPlayers().size() < 5) {
                     channel.sendMessage("Please add at least " + (5 - game.getPlayers().size()) + " more players").queue();
                } else {
                    channel.sendMessage("Game has successfully started.").queue();
                    game.updatePlayers();
                    game.setRunningGame(true);
                    game.setRoles();
                    for (Class player : game.getPlayers()) {
                        player.getPlayer().openPrivateChannel().complete().sendMessage("You are a "+ player.getClass().toString()).queue();
                    }
                    channel.sendMessage("The Villagers are sleeping").queue();
                    channel.sendMessage("The Werewolves awake!").queue();
                    channel.sendMessage("They silently choose their prey").queue();
                    // for (Player werewolf1 : game.getPlayers().stream().filter(n -> n.isWerewolf()).collect(Collectors.toList())) {
                    //     werewolf1.getPlayer().openPrivateChannel().complete().sendMessage("Prey List").queue();
                    //     for (Player player : game.getPlayers()) {
                    //         werewolf1.getPlayer().openPrivateChannel().complete().sendMessage(player.getShortcut() + " ) " + player.getPlayer().getAsMention()).queue();
                    //     }
                    // }
                    game.setNight(true);
                    channel.sendMessage("PM the bot with \'.vote <0-99>\' where <0-99> is  the number of who you want to kill.").queue();
                }
            }
        }
    }
}
