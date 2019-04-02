package Werewolf;

import net.dv8tion.jda.core.entities.User;

public class Player {
    private boolean isAlive;
    private boolean isWerewolf;
    private User player;
    private int shortcut;
    private int voteCounts;
    private int hasVoted;
    private boolean didVote;

    public Player(User player, char shortcut) {
        this.player = player;
        isAlive = true;
        isWerewolf = false;
        this.shortcut = shortcut;
        voteCounts = 0;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isWerewolf() {
        return isWerewolf;
    }

    public User getPlayer() {
        return player;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setWerewolf(boolean werewolf) {
        isWerewolf = werewolf;
    }

    public int getShortcut() {
        return shortcut;
    }

    public void setShortcut(int shortcut) {
        this.shortcut = shortcut;
    }

    public int getVoteCounts() {
        return voteCounts;
    }

    public void incVoteCounts() {
        voteCounts++;
    }

    public void resetVoteCounts() {
        voteCounts = 0;
    }

    public boolean hasVoted(int currentVote) {
        return hasVoted == currentVote || hasVoted == 0;
    }

    public void setHasVoted(int hasVoted) {
        this.hasVoted = hasVoted;
    }

    public boolean isDidVote() {
        return didVote;
    }

    public void setDidVote(boolean didVote) {
        this.didVote = didVote;
    }
}
