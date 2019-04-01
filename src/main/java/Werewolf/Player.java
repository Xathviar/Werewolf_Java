package Werewolf;

import net.dv8tion.jda.core.entities.User;

public class Player {
    public boolean isAlive;
    public boolean isWerewolf;
    public User player;

    public Player(User player) {
        this.player = player;
        isAlive = true;
        isWerewolf = false;
    }

}
