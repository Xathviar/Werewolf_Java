package Werewolf.commands;

import Werewolf.Game;
import Werewolf.Player;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Join extends ListenerAdapter {
    private Game game;
    private Character prefix;

    public Join(Game game, Character prefix) {
        this.game = game;
        this.prefix = prefix;

    }

    @Override
    public void onMessageReceived (MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = game.getChannel(); //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (game.getChannel() == null) {
            channel = event.getChannel();
        }
        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "join")) {
                if (!game.isGame()) {
                    channel.sendMessage("Please create a game!").queue();
                }else if (game.isRunningGame()) {
                    channel.sendMessage("There is already a running game.").queue();
                }else if (game.getPlayers().stream().anyMatch(n -> n.getPlayer().getId().equals(author.getId()))) {
                    channel.sendMessage("Player " + author.getAsMention() + " is already registered").queue();
                }else {
                    Player player = new Player(author, (char)(game.getPlayers().size() + 1));
                    channel.sendMessage("Player " + author.getAsMention() + " has been successfully added").queue();
                    game.addPlayer(player);
                }
            }
        }
    }
}
