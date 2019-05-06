package Werewolf.commands;

import Werewolf.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Create extends ListenerAdapter {
    private Game game;
    private Character prefix;

    public Create(Game game, Character prefix) {
        this.game = game;
        this.prefix = prefix;

    }

    @Override
    public void onMessageReceived (MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "create")) {
                if (game.isGame()) {
                    channel.sendMessage("There is already a game.").queue();

                }else if (game.isRunningGame()) {
                    channel.sendMessage("There is already a running game.").queue();
                }else {
                    game.setChannel(channel);
                    channel.sendMessage("Game has been successfully created.").queue();
                    game.setGame(true);
                }
            }
        }
    }
}
