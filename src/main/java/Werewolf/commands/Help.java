package Werewolf.commands;

import Werewolf.Werewolf;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Help extends ListenerAdapter {
    private Character prefix;

    public Help(Character prefix) {
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "help")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Help", null);
                eb.setColor(Color.green);
                eb.setDescription("Help for Werewolf Bot");
                eb.addField(".create", "Creates a new Game", false);
                eb.addField(".list", "Lists all Players", false);
                eb.addField(".join", "Joins the Game", false);
                eb.addField(".start", "Starts the Game", false);
                eb.addField(".vote <1-99>", "Vote for the player with the ID", false);
                eb.addField(".fuckyou", "Find out yourself", false);
                author.openPrivateChannel().complete().sendMessage(eb.build()).queue();
            }
        }
    }
}
