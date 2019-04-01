package Werewolf.commands;

import Werewolf.Werewolf;
import Werewolf.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class List extends ListenerAdapter {
    private Werewolf werewolf;
    private Character prefix;

    public List(Werewolf werewolf, Character prefix) {
        this.werewolf = werewolf;
        this.prefix = prefix;

    }

    @Override
    public void onMessageReceived (MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = werewolf.getChannel();    //This is the MessageChannel that the message was sent to.
        if (werewolf.getChannel() == null) {
            channel = event.getChannel();
        }
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "list")) {
                if (!werewolf.isGame()) {
                    channel.sendMessage("Please create a game!").queue();
                }else if (werewolf.isRunningGame()) {
                    channel.sendMessage("There is already a running game.").queue();
                }else {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Player List");
                    eb.setColor(Color.red);
                    eb.setDescription("");

                    for (Player player1: werewolf.getPlayers()) {
                        eb.addField(player1.getPlayer().getName(), "", true);
                        eb.addBlankField(false);
                    }
                    channel.sendMessage(eb.build()).queue();
                }
            }
        }
    }
}
