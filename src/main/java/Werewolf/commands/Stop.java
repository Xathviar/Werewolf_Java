package Werewolf.commands;

import Werewolf.Werewolf;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Stop extends ListenerAdapter {
    Werewolf werewolf;
    Character prefix;

    public Stop(Werewolf werewolf, Character prefix) {
        this.werewolf = werewolf;
        this.prefix = prefix;
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        MessageChannel global = werewolf.getChannel();
        if (!isBot && msg.equalsIgnoreCase(prefix + "stop") && author.getId().equals("563076963242082326")) {
            werewolf.setGame(false);
            werewolf.setRunningGame(false);
            werewolf.setNight(true);
        }
    }
}
