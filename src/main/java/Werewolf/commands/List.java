package Werewolf.commands;

import Werewolf.Game;
import Werewolf.Player;

import Werewolf.players.Class;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class List extends ListenerAdapter {
    private Game game;
    private Character prefix;

    public List(Game game, Character prefix) {
        this.game = game;
        this.prefix = prefix;

    }

    @Override
    public void onMessageReceived (MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = game.getChannel();    //This is the MessageChannel that the message was sent to.
        if (game.getChannel() == null) {
            channel = event.getChannel();
        }
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "list")) {
                if (!game.isGame()) {
                    channel.sendMessage("Please create a game!").queue();
                }else if (game.isRunningGame()) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Player List");
                    eb.setColor(Color.red);
                    eb.setDescription("");
                    for (Class player1: game.getPlayers()) {
                        eb.addField(player1.getShortcut() + " ) " + player1.getPlayer().getName(), "", true);
                        eb.addBlankField(false);
                    }
                    channel.sendMessage(eb.build()).queue();
                }else {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Player List");
                    eb.setColor(Color.red);
                    eb.setDescription("");

                    for (Class player1: game.getPlayers()) {
                        eb.addField(player1.getPlayer().getName(), "", true);
                        eb.addBlankField(false);
                    }
                    channel.sendMessage(eb.build()).queue();
                    eb.clear();
                }
            }
        }
    }
}
