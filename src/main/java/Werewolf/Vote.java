package Werewolf;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.*;
import java.util.stream.Collectors;

public class Vote {
    private Werwolf werwolf;
    public Rolle rolle;
    public ArrayList<Victim> votedSpieler;
    public Set<Spieler> playerThatVoted;
    public TextChannel channel;

    public Vote(Werwolf werwolf) {
        votedSpieler = new ArrayList<>();
        playerThatVoted = new HashSet<>();
        this.rolle = Rolle.WERWOLF;
        this.werwolf = werwolf;
    }

    public void initVoting() {
        resetVoting();
        initVictims(rolle);
        switch (rolle) {
            case WERWOLF:
            case DORFBEWOHNER:
            case HEXE:
            case ARMOR:
            case SEHER:
        }
    }

    private void initVictims(Rolle rolle) {
        Set<Spieler> spieler = werwolf.getSpieler();
        List<Spieler> votableSpieler = spieler.stream().filter(n -> n.getRolle() != rolle).collect(Collectors.toList());
    }

    public boolean canVote(User user) {
        Spieler spieler = werwolf.getSpielerFromUser(user);
        return (!playerThatVoted.contains(spieler)) && (spieler.getRolle() == rolle || rolle == Rolle.DORFBEWOHNER);
    }

    public void vote(User voter, User victim) {
        if (!canVote(voter)) {
            return;
        }
        Spieler voterS = werwolf.getSpielerFromUser(voter);
        playerThatVoted.add(voterS);
        votedSpieler.stream().filter(n -> n.getUser().equals(victim)).findFirst().get().incrementVotes();
    }

    public void removeVote(User voter, User victim) {
        if (canVote(voter)) {
            return;
        }
        Spieler voterS = werwolf.getSpielerFromUser(voter);
        playerThatVoted.remove(voterS);
        votedSpieler.stream().filter(n -> n.getUser().equals(victim)).findFirst().get().decrementVotes();
    }

    public Spieler getVotedSpieler() {
        int max = 0;
        Victim currentVictim = null;
        for (Victim victim : votedSpieler) {
            if (victim.getVoteCount() > max) {
                max = victim.getVoteCount();
                currentVictim = victim;
            }
        }
        return currentVictim.getSpieler();
    }

    public void setChannel(TextChannel channel) {
        this.channel = channel;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    public void resetVoting() {
        this.votedSpieler.clear();
        this.playerThatVoted.clear();
    }
}
