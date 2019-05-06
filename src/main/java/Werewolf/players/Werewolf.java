package Werewolf.players;

import net.dv8tion.jda.core.entities.User;

public class Werewolf implements Class {
    boolean isAlive;
    User player;
    int shortcut;
    int voteCounts;
    boolean hasVoted;

    public Werewolf(User player) {
        this.player = player;
        this.isAlive = true;
        this.shortcut = 0;
        this.voteCounts = 0;
        this.hasVoted = false;
    }

    public Werewolf() {
            this.player = null;
            this.isAlive = true;
            this.shortcut = 0;
            this.voteCounts = 0;
            this.hasVoted = false;
    }

    @Override
    public void setAlive(boolean alive) {
        isAlive = true;
    }

    @Override
    public void setShortcut(int shortcut) {
        this.shortcut = shortcut;
    }

    @Override
    public void incVoteCounts() {
        voteCounts++;
    }

    @Override
    public void resetVoteCounts() {
        voteCounts = 0;
    }

    @Override
    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}
