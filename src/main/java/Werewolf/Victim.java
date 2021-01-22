package Werewolf;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Victim {
    private Message message;
    private Spieler spieler;
    private int voteCount;

    public Victim(Spieler spieler, MessageChannel channel, String... emotes) {
        this.spieler = spieler;
        message = channel.sendMessage("Vote for " + spieler.getNameAsMention()).complete();
        channel.sendMessage(message).queue();
        for (String emote : emotes) {
            message.addReaction(emote).queue();
        }
        this.voteCount = 0;
    }

    public void incrementVotes() {
        voteCount++;
    }

    public void decrementVotes() {
        voteCount = voteCount > 0 ? voteCount-- : 0;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public User getUser() {
        return spieler.getUser();
    }

    public Spieler getSpieler() {
        return spieler;
    }
}
