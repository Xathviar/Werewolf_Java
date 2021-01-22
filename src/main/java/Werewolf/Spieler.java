package Werewolf;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

public class Spieler implements Comparable<Spieler> {
    private Rolle rolle;
    private User user;
    private boolean isDead;
    private PrivateChannel privateChannel;

    public Spieler(User user) {
        this.user = user;
        privateChannel = user.openPrivateChannel().complete();
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

    @Override
    public int compareTo(@NotNull Spieler o) {
        if (o.user.getId().equals(user.getId())) {
            return 0;
        } else {
            return user.getName().compareTo(o.user.getName());
        }
    }

    public String getNameAsMention() {
        return user.getAsMention();
    }

    public void sendPrivateMessage(String message) {
        privateChannel.sendMessage(message).queue();
    }

    public void sendPrivateMessage(EmbedBuilder builder) {
        privateChannel.sendMessage(builder.build()).queue();
    }
}
