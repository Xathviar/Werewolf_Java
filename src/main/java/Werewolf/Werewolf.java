package Werewolf;


import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Werewolf {
    private boolean game;
    private boolean runningGame;
    private boolean isNight;
    private List<Player> players;
    private MessageChannel channel;


    public Werewolf() {
        game = false;
        runningGame = false;
        players = new ArrayList<Player>();
        isNight = true;
    }

    public boolean allVoted() {
        return players.stream().filter(n -> n.hasVoted()).collect(Collectors.toList()).size() == players.size();
    }

    public boolean allWWVoted () {
        return players.stream().filter(n -> n.hasVoted() && n.isWerewolf()).collect(Collectors.toList()).size() == players.stream().filter(n -> n.isWerewolf()).collect(Collectors.toList()).size();

    }

    public Player getMostVoted() {
        return players.stream().max(Comparator.comparingInt(n -> n.getVoteCounts())).get();
    }

    public void resetVoting () {
        players.forEach(n -> n.resetVoteCounts());
    }

    public void setRoles() {
        double needed_ww = getWWCount();
        int ww = 0;
        while (ww < needed_ww) {
            for (Player player: players) {
                if (Math.random() < (1.0/getWWCount()) && !player.isWerewolf()) {
                    player.setWerewolf(true);
                    ww++;
                    if (ww == needed_ww) {
                        break;
                    }
                }

            }
        }
    }

    private int getWWCount() {
        if (players.size() < 7) {
            return 1;
        }else if (players.size() < 11) {
            return 2;
        } else if (players.size() < 15) {
            return 3;
        } else if (players.size() < 19) {
            return 4;
        } else {
            return 5;
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void setGame(boolean game) {
        this.game = game;
    }

    public void setRunningGame(boolean runningGame) {
        this.runningGame = runningGame;
    }

    public boolean isGame() {
        return game;
    }

    public boolean isRunningGame() {
        return runningGame;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isNight() {
        return isNight;
    }

    public void setNight(boolean night) {
        isNight = night;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public void setChannel(MessageChannel channel) {
        this.channel = channel;
    }

    public void updatePlayers () {
        List<Player> players1 = new ArrayList<>();
        List<Player> players2 = new ArrayList<>();
        int c = 1;
        for (Player player : players) {
            if (player.isAlive()) {
                player.setShortcut(c);
                c++;
                players1.add(player);
            } else {
                player.setShortcut(0);
                players2.add(player);
            }
        }
        players1.addAll(players2);
        players = players1;
    }

    public boolean checkVictoryWW() {
        return players.stream().filter(n -> n.isWerewolf()).collect(Collectors.toList()).size() == players.size();
    }
    public boolean checkVictory() {
        return players.stream().filter(n -> !n.isWerewolf()).collect(Collectors.toList()).size() == players.size();
    }
}
