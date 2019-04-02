package Werewolf.commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class FYou extends ListenerAdapter {
    private Character prefix;

    public FYou(Character prefix) {
        this.prefix = prefix;

    }

    @Override
    public void onMessageReceived (MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (!isBot ) {
            if (msg.equalsIgnoreCase(prefix + "fuckyou")) {
                channel.sendMessage("no u").queue();
            }else if(msg.equalsIgnoreCase(prefix + "owo")) {
                channel.sendMessage("Furry detector...").queue();
                channel.sendMessage(author.getAsMention() + " is a Furry.").queue();
            }
        }
    }
}
