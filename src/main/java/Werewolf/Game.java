package Werewolf;


import Werewolf.phase.Phase;
import Werewolf.phase.WerewolfPhase;
import Werewolf.players.Innocent;
import Werewolf.players.Seer;
import Werewolf.players.Werewolf;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.*;

import Werewolf.players.Class;
import net.dv8tion.jda.core.entities.User;

public class Game {
    private boolean game;
    private boolean runningGame;
    private boolean isNight;
    private List<Class> players;
    private MessageChannel channel;
    private Phase phase;
    private final Map<Class, Integer[]> CLASSES;
    public List<User> joined_players;
    private List<Class> candidates;

    public Game() {
        joined_players = new ArrayList<>();
        candidates = new ArrayList<>();
        fillCandidates();
        game = false;
        runningGame = false;
        players = new ArrayList<>();
        isNight = true;
        phase = new WerewolfPhase(this);
        CLASSES = new HashMap<>();
        fillClasses();
    }

    public List<Class> getCandidates() {
        return candidates;
    }

    private void fillCandidates() {
        candidates.add(new Werewolf());
        candidates.add(new Innocent());
        candidates.add(new Seer());
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    //TODO Minimum players are 5
    private void fillClasses() {
        CLASSES.put(new Werewolf(), new Integer[]{1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4});
        CLASSES.put(new Innocent(), new Integer[]{3, 4, 5, 5, 6, 7, 8, 8, 9, 10, 11, 11, 12, 13, 14, 14, 15});
        CLASSES.put(new Seer(), new Integer[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
    }

    public boolean allVoted() {
        return players.stream().filter(n -> n.hasVoted()).count() == players.size();
    }

    public boolean allVoted(Class player) {
        return players.stream().filter(n -> n.hasVoted() && n.getClass().equals(player.getClass())).count() == players.stream().filter(n -> n.getClass().equals(player.getClass())).count();
    }

    public Class getMostVoted() {
        if ((double) players.stream().max(Comparator.comparingInt(n -> n.getVoteCounts())).get().getVoteCounts() / (double) players.size() > 0.5) {
            return players.stream().max(Comparator.comparingInt(n -> n.getVoteCounts())).get();
        } else {
            return null;
        }
    }

    public void resetVoting() {
        players.forEach(n -> n.resetVoteCounts());
    }

    public void resetVoted() {
        players.forEach(n -> n.setHasVoted(false));
    }

    public void setRoles() {
        for (Map.Entry<Class, Integer[]> entry : CLASSES.entrySet()) {
            Class key = entry.getKey();
            int needed = entry.getValue()[players.size() - 5];
            int role = 0;
            Random randomGenerator = new Random();
            while (needed < role) {
                User player = joined_players.get(randomGenerator.nextInt(players.size()));
                if (players.stream().noneMatch(n -> n.getPlayer().equals(player))) {
                    if (key.getClass().equals(candidates.get(0).getClass())) {
                        players.add(new Werewolf(player));
                    } else if (key.getClass().equals(candidates.get(1).getClass())) {
                        players.add(new Innocent(player));
                    } else if (key.getClass().equals(candidates.get(2).getClass())) {
                        players.add(new Seer(player));
                    }
                    role++;
                }
            }
        }
    }

//    public void setRoles() {
//        double needed_ww = getWWCount();
//        int ww = 0;
//        while (ww < needed_ww) {
//            Player player = players.stream().filter(n -> !n.isWerewolf()).findAny().get();
//            player.setWerewolf(true);
//            ww++;
//        }
//    }
//
//    private double getWWCount() {
//        if (players.size() < 7) {
//            return 1.0;
//        }else if (players.size() < 11) {
//            return 2.0;
//        } else if (players.size() < 15) {
//            return 3.0;
//        } else if (players.size() < 19) {
//            return 4.0;
//        } else {
//            return 5.0;
//        }
//    }

    public void addPlayer(Class player) {
        players.add(player);
    }

    public void removePlayer(Class player) {
        player.setAlive(false);
        players.remove(player);
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

    public List<Class> getPlayers() {
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

    public void updatePlayers() {
        List<Class> players1 = new ArrayList<>();
        int c = 1;
        for (Class player : players) {
            if (player.isAlive()) {
                player.setShortcut(c);
                c++;
                players1.add(player);
            }
        }
        players = players1;
    }

    public boolean checkVictoryWW() {
        return players.stream().filter(n -> !n.getClass().equals(Werewolf.class)).count() <= players.stream().filter(n -> n.getClass().equals(Werewolf.class)).count();
    }

    public boolean checkVictory() {
        return players.stream().noneMatch(n -> n.getClass().equals(Werewolf.class));
    }

    public void resetPlayer() {
        players = new ArrayList<>();
    }
}
