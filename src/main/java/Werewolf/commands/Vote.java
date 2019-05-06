package Werewolf.commands;

import Werewolf.Game;
import Werewolf.phase.Phase;
import Werewolf.players.Class;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vote extends ListenerAdapter {
    private Game game;
    private Character prefix;

    public Vote(Game game, Character prefix) {
        this.game = game;
        this.prefix = prefix;
    }

//    @Override
//    public void onMessageReceived(MessageReceivedEvent event) {
//        User author = event.getAuthor();                //The user that sent the message
//        Message message = event.getMessage();           //The message that was received.
//        MessageChannel channel = game.getChannel();    //This is the MessageChannel that the message was sent to.
//        String msg = message.getContentDisplay();       //msg
//        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
//
//        if (game.getChannel() == null) {
//            channel = event.getChannel();
//        }
//        if (!isBot && !event.isFromType(ChannelType.PRIVATE)) {
//            if (msg.contains((prefix + "vote"))) {
//                if (!game.isGame()) {
//                    channel.sendMessage("Please create a game first!").queue();
//
//                } else if (!game.isRunningGame()) {
//                    channel.sendMessage("There isn't a running game.").queue();
//                    //}else if (game.getPlayers().size() < 5) {
//                    //    channel.sendMessage("Please add " + (5 - game.getPlayers().size()) + " Players").queue();
//                } else if (game.isNight()) {
//                    channel.sendMessage("You cannot vote at night.").queue();
//                } else {
//                    Matcher matcher = Pattern.compile(".* ([1-9][0-9]*) *").matcher(msg);
//                    if (matcher.find()) {
//                        int a = Character.getNumericValue(matcher.group(1).charAt(0));
//                        if (!game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get().hasVoted()) {
//                            channel.sendMessage("You already voted.").queue();
//                        } else {
//
//                            if (game.getPlayers().stream().filter(n -> n.getShortcut() == a).findFirst().orElse(null) == null) {
//                                channel.sendMessage("Please provide a valid vote!").queue();
//                            } else {
//                                game.getPlayers().get(a - 1).incVoteCounts();
//                                Class player = game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get();
//                                player.setHasVoted(true);
//                                channel.sendMessage(author.getAsMention() + " voted for " + game.getPlayers().get(a - 1).getPlayer().getAsMention()).queue();
//                                channel.sendMessage(game.getPlayers().get(a - 1).getPlayer().getAsMention() + " has currently " + game.getPlayers().get(a - 1).getVoteCounts() + " Votes").queue();
//                                if (game.allVoted()) {
//                                    Class dead = game.getMostVoted();
//                                    if (dead == null) {
//                                        channel.sendMessage("No one died").queue();
//                                    } else {
//                                        dead.setAlive(false);
//                                        channel.sendMessage(dead.getPlayer().getAsMention() + " died").queue();
//                                    }
//                                    game.updatePlayers();
//                                    game.resetVoting();
//                                    game.resetVoted();
//                                    if (game.checkVictoryWW()) {
//                                        channel.sendMessage("The Werewolves won the game.").queue();
//                                        game.setGame(false);
//                                        game.setRunningGame(false);
//                                        game.setNight(true);
//                                        game.resetPlayer();
//                                    } else if (game.checkVictory()) {
//                                        channel.sendMessage("The Villagers won the game.").queue();
//                                        game.setGame(false);
//                                        game.setRunningGame(false);
//                                        game.setNight(true);
//                                        game.resetPlayer();
//                                    }
//                                    channel.sendMessage("Player List").queue();
//                                    for (Class player1 : game.getPlayers()) {
//                                        channel.sendMessage(player1.getShortcut() + ") " + player1.getPlayer().getAsMention()).queue();
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        channel.sendMessage("Please provide a valid vote!").queue();
//                    }
//                }
//            }
//        }
//    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = game.getChannel();     //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        Phase phase = game.getPhase();
        if (game.getChannel() == null) {
            channel = event.getChannel();
        }
        if (event.isFromType(ChannelType.PRIVATE) == phase.isPrivate && !isBot) {
            if (msg.contains((prefix + "vote"))) {
                if (!game.isGame()) {
                    channel.sendMessage("Please create a game first!").queue();
                } else if (!game.isRunningGame()) {
                    channel.sendMessage("There isn't a running game.").queue();
                    //}else if (game.getPlayers().size() < 5) {
                    //    channel.sendMessage("Please add " + (5 - game.getPlayers().size()) + " Players").queue();
                }else {
                    Matcher matcher = Pattern.compile(".* ([1-9][0-9]*) *").matcher(msg);
                    if (matcher.find()) {
                        int a = Character.getNumericValue(matcher.group(1).charAt(0));
                        if (!game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get().hasVoted()) {
                            channel.sendMessage("You already voted.").queue();
                        } else {
                            if (game.getPlayers().stream().filter(n -> n.getShortcut() == a).findFirst().orElse(null) == null) {
                                channel.sendMessage("Please provide a valid vote!").queue();
                            } else {
                                Class player = game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get();
                                Class voted = phase.vote(player, a - 1);
                                channel.sendMessage("You voted for " + game.getPlayers().get(a - 1).getPlayer().getAsMention()).queue();
                                channel.sendMessage(game.getPlayers().get(a - 1).getPlayer().getAsMention() + " has currently " + game.getPlayers().get(a - 1).getVoteCounts() + " Votes").queue();
                                if (phase.allVoted()) {
                                    Class dead = game.getMostVoted();
                                    phase.action(dead);
                                    game.updatePlayers();
                                    game.resetVoting();
                                    game.resetVoted();
                                    if (game.checkVictoryWW()) {
                                        channel.sendMessage("The Werewolves won the game.").queue();
                                        game.setGame(false);
                                        game.setRunningGame(false);
                                        game.setNight(true);
                                        game.resetPlayer();
                                    } else if (game.checkVictory()) {
                                        channel.sendMessage("The Villagers won the game.").queue();
                                        game.setGame(false);
                                        game.setRunningGame(false);
                                        game.setNight(true);
                                        game.resetPlayer();
                                    }
                                    channel.sendMessage("Player List").queue();
                                    for (Class player1 : game.getPlayers()) {
                                        channel.sendMessage(player1.getShortcut() + ") " + player1.getPlayer().getAsMention()).queue();
                                    }
                                }
                            }
                        }
                    } else {
                        channel.sendMessage("Please provide a valid vote!").queue();
                    }
                }
            }
        }
    }
}
