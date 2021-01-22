package Werewolf;

import net.dv8tion.jda.api.entities.User;

public class Spieler {
    private Rolle rolle;
    private User user;
    private boolean isDead;

    public Spieler(User user) {
        this.user = user;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    public Rolle getRolle() {
        return rolle;
    }

    public User getUser() {
        return user;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
