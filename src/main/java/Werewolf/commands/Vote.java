package Werewolf.commands;

import Werewolf.Werewolf;
import Werewolf.Player;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vote extends ListenerAdapter {
    private Werewolf werewolf;
    private Character prefix;

    public Vote(Werewolf werewolf, Character prefix) {
        this.werewolf = werewolf;
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = werewolf.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (werewolf.getChannel() == null) {
            channel = event.getChannel();
        }
        if (!isBot && !event.isFromType(ChannelType.PRIVATE)) {
            if (msg.contains((prefix + "vote"))) {
                if (!werewolf.isGame()) {
                    channel.sendMessage("Please create a game first!").queue();

                } else if (!werewolf.isRunningGame()) {
                    channel.sendMessage("There isn't a running game.").queue();
                    //}else if (werewolf.getPlayers().size() < 5) {
                    //    channel.sendMessage("Please add " + (5 - werewolf.getPlayers().size()) + " Players").queue();
                } else if (werewolf.isNight()) {
                    channel.sendMessage("You cannot vote at night.").queue();
                } else if (!werewolf.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get().hasVoted()) {
                    Matcher matcher = Pattern.compile(".* ([1-9][0-9]*) *").matcher(msg);
                    if (matcher.find()) {
                        int a = Character.getNumericValue(matcher.group(1).charAt(0));
                        if (werewolf.getPlayers().stream().filter(n -> n.getShortcut() == a).findFirst().orElse(null) == null) {
                            channel.sendMessage("Please provide a valid vote!").queue();
                        } else {
                            werewolf.getPlayers().get(a - 1).incVoteCounts();
                            Player player = werewolf.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get();
                            player.setHasVoted(true);
                            channel.sendMessage(author.getAsMention() + " voted for " + player.getPlayer().getAsMention()).queue();
                            channel.sendMessage(player.getPlayer().getAsMention() + " has currently " + player.getVoteCounts() + " Votes").queue();
                            if (werewolf.allVoted()) {
                                Player dead = werewolf.getMostVoted();
                                dead.setAlive(false);
                                channel.sendMessage(dead.getPlayer().getAsMention() + " died").queue();
                                werewolf.resetVoting();
                                if (werewolf.checkVictoryWW()) {
                                    channel.sendMessage("The Werewolves won the game.").queue();
                                    werewolf.setGame(false);
                                    werewolf.setRunningGame(false);
                                    werewolf.setNight(true);
                                } else if (werewolf.checkVictory()) {
                                    channel.sendMessage("The Villagers won the game.").queue();
                                    werewolf.setGame(false);
                                    werewolf.setRunningGame(false);
                                    werewolf.setNight(true);
                                }
                                channel.sendMessage("After a hard day the villagers go home and start sleeping").queue();
                                werewolf.setNight(true);
                                werewolf.updatePlayers();
                                EmbedBuilder eb = new EmbedBuilder();
                                eb.setTitle("Player List");
                                eb.setColor(Color.red);
                                eb.setDescription("");
                                for (Player player1: werewolf.getPlayers()) {
                                    eb.addField(player1.getShortcut() + player1.getPlayer().getName(), "", true);
                                }
                            }
                        }
                    } else {
                        channel.sendMessage("Please provide a valid vote!").queue();
                    }
                } else {
                    channel.sendMessage(author.getAsMention() + " did already vote!").queue();
                }
            }
        }
    }
}
