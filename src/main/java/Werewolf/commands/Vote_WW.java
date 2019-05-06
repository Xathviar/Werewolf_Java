package Werewolf.commands;

import Werewolf.Game;
import Werewolf.Player;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vote_WW extends ListenerAdapter {
    private Game game;
    private Character prefix;

    public Vote_WW(Game game, Character prefix) {
        this.game = game;
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        MessageChannel global = game.getChannel();
        if (!isBot && event.isFromType(ChannelType.PRIVATE)) {

            if (msg.contains((prefix + "vote"))) {
                if (!game.isGame()) {
                    channel.sendMessage("Please create a game first!").queue();

                } else if (!game.isRunningGame()) {
                    channel.sendMessage("There isn't a running game.").queue();
                    //}else if (game.getPlayers().size() < 5) {
                    //    channel.sendMessage("Please add " + (5 - game.getPlayers().size()) + " Players").queue();
                } else if (game.isNight()) {
                    if (!game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get().isWerewolf()) {
                        channel.sendMessage("You are not a Game").queue();
                    } else {
                        Matcher matcher = Pattern.compile(".* ([1-9][0-9]*) *").matcher(msg);
                        if (matcher.find()) {
                            int a = Character.getNumericValue(matcher.group(1).charAt(0));
                            if (!game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get().hasVoted()) {
                                channel.sendMessage("You already voted.").queue();
                            } else {
                                if (game.getPlayers().stream().filter(n -> n.getShortcut() == a).findFirst().orElse(null) == null) {
                                    channel.sendMessage("Please provide a valid vote!").queue();
                                }else {
                                    Player player = game.getPlayers().get(a - 1);
                                    Player werewolf1 = game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get();
                                    if (player == werewolf1) {
                                        channel.sendMessage("You cannot vote for yourself!").queue();
                                    }else {
                                        werewolf1.setHasVoted(true);
                                        player.incVoteCounts();
                                        channel.sendMessage(author.getAsMention() + " voted for " + player.getPlayer().getAsMention()).queue();
                                        channel.sendMessage(player.getPlayer().getAsMention() + " has currently " + player.getVoteCounts() + " Votes").queue();
                                        if (game.allWWVoted()) {
                                            Player dead = game.getMostVoted();
                                            dead.setAlive(false);
                                            game.resetVoting();
                                            game.resetVoted();
                                            global.sendMessage("The Werewolves have chosen their prey.").queue();
                                            global.sendMessage("The Werewolves go to sleep.").queue();
                                            global.sendMessage("The Villager wakes up.").queue();
                                            global.sendMessage(dead.getPlayer().getAsMention() + " died.").queue();
                                            game.setNight(false);
                                            game.updatePlayers();
                                            if (game.checkVictoryWW()) {
                                                global.sendMessage("The Werewolves won the game.").queue();
                                                game.setGame(false);
                                                game.setRunningGame(false);
                                                game.setNight(true);
                                                game.resetPlayer();
                                            } else {
                                                global.sendMessage("Player List").queue();
                                                for (Player player1 : game.getPlayers()) {
                                                    global.sendMessage(player1.getShortcut() + ") " + player1.getPlayer().getAsMention()).queue();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (Pattern.compile(".pass *").matcher(msg).find()) {
                            channel.sendMessage(author.getAsMention() + " voted for nobody").queue();
                            Player werewolf1 = game.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get();
                            werewolf1.setHasVoted(true);
                            if (game.allWWVoted()) {
                                Player dead = game.getMostVotedWW();
                                game.resetVoting();
                                game.resetVoted();
                                if (dead==null) {
                                    global.sendMessage("The Werewolves have chosen their prey").queue();
                                    global.sendMessage("The Werewolves go to sleep").queue();
                                    global.sendMessage("The Villager wakes up").queue();
                                    global.sendMessage("Nobody died").queue();
                                    game.setNight(false);
                                    game.updatePlayers();
                                    if (game.checkVictoryWW()) {
                                        global.sendMessage("The Werewolves won the game").queue();
                                        game.setGame(false);
                                        game.setRunningGame(false);
                                        game.setNight(true);
                                        game.resetPlayer();
                                    } else {
                                        global.sendMessage("Player List").queue();
                                        for (Player player1 : game.getPlayers()) {
                                            global.sendMessage(player1.getShortcut() + ") " + player1.getPlayer().getAsMention()).queue();
                                        }
                                    }
                                } else {
                                    global.sendMessage("The Werewolves have chosen their prey").queue();
                                    global.sendMessage("The Werewolves go to sleep").queue();
                                    global.sendMessage("The Village wakes up").queue();
                                    global.sendMessage(dead.getPlayer().getAsMention() + " died").queue();
                                    dead.setAlive(false);
                                    game.setNight(false);
                                    game.updatePlayers();
                                    if (game.checkVictoryWW()) {
                                        global.sendMessage("The Werewolves won the game.").queue();
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
                                    }else {
                                        global.sendMessage("Player List").queue();
                                        for (Player player1 : game.getPlayers()) {
                                            global.sendMessage(player1.getShortcut() + ") " + player1.getPlayer().getAsMention()).queue();
                                        }
                                    }
                                }
                            }
                        } else {
                            channel.sendMessage("Please provide a valid vote!").queue();
                        }

                    }
                } else {
                    channel.sendMessage(author.getAsMention() + " did already vote!").queue();
                }
            }
        }
    }
}
