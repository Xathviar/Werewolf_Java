package Werewolf;

import net.dv8tion.jda.core.entities.User;

public class Player {
    private boolean isAlive;
    private boolean isWerewolf;
    private User player;

    public Player(User player) {
        this.player = player;
        isAlive = true;
        isWerewolf = false;
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

}
