package Werewolf.players;

import net.dv8tion.jda.core.entities.User;

public interface Class {

    boolean isAlive = true;
    User player = null;
    int shortcut = 0;
    int voteCounts = 0;
    boolean hasVoted = false;

    default public void onDeath() {
        return;
    }

    default public boolean isAlive() {
        return isAlive;
    }

    default public User getPlayer() {
        return player;
    }

    public void setAlive(boolean alive);

    default public int getShortcut() {
        return shortcut;
    }

    public void setShortcut(int shortcut);

    default public int getVoteCounts() {
        return voteCounts;
    }

    public void incVoteCounts();

    public void resetVoteCounts();

    default public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted);
}
