package Werewolf;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class Victim {
    private Spieler spieler;
    private int voteCount;
    private String messageID;

    public Victim(Spieler spieler, MessageChannel channel, String... emotes) {
        this.spieler = spieler;
        EmbedBuilder eb = new EmbedBuilder();
        eb.setDescription("Vote für " + spieler.getNameAsMention());
        eb.setColor(Color.GREEN);
        channel.sendMessage(eb.build()).queue((message) -> {
            for (String emote : emotes) {
                message.addReaction(emote).queue();
            }
            this.messageID = message.getId();
        });
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
