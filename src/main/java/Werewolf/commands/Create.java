package Werewolf.commands;

import Werewolf.Werewolf;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Create extends ListenerAdapter {
    private Werewolf werewolf;
    private Character prefix;

    public Create(Werewolf werewolf, Character prefix) {
        this.werewolf = werewolf;
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
                if (werewolf.isGame()) {
                    channel.sendMessage("There is already a game.").queue();

                }else if (werewolf.isRunningGame()) {
                    channel.sendMessage("There is already a running game.").queue();
                }else {
                    werewolf.setChannel(channel);
                    channel.sendMessage("Game has been successfully created.").queue();
                    werewolf.setGame(true);
                }
            }
        }
    }
}
