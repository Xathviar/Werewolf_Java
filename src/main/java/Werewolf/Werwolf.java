package Werewolf;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Werwolf {
    private Set<Spieler> spieler;
    private Spieler buergermeister;
    private Spieler serverCreator;
    private Settings settings;
    private Vote vote;

    private StateMachine currentState;
    private int rounds;
    private JDA jda;
    private List<Member> members;

    TextChannel armorChannel;
    TextChannel hexeChannel;
    TextChannel seherChannel;
    TextChannel werwolfChannel;
    TextChannel votingChannel;
    TextChannel erzaehlerChannel;
    TextChannel lobby;

    public Werwolf() {
        currentState = StateMachine.IDLE;
        spieler = new TreeSet<>();
        settings = new Settings();
        members = new ArrayList<>();
        vote = new Vote(this);
    }

    public void setJDA(JDA jda) {
        this.jda = jda;
        initChannels();
        removePermissionsOverrides();
        clearChannels();
    }

    private void clearChannels() {
        clearChannel(votingChannel);
        clearChannel(armorChannel);
        clearChannel(hexeChannel);
        clearChannel(seherChannel);
        clearChannel(werwolfChannel);
        clearChannel(erzaehlerChannel);
    }

    private void clearChannel(TextChannel channel) {
        List<Message> messages = channel.getHistory().retrievePast(50).complete();
        List<Message> nonDupilcateMessage = new ArrayList<>();
        a:
        for (Message message : messages) {
            for (Message message1 : nonDupilcateMessage) {
                if (message.getId().equals(message1.getId())) {
                    continue a;
                }
            }
            nonDupilcateMessage.add(message);
        }
        for (Message message : nonDupilcateMessage) {
            try {
                message.delete().queue();
            } catch (Exception e) {
                return;
            }
        }
    }

    private void removePermissionsOverrides() {
        Guild guild = lobby.getGuild();
        Role role = guild.getRoleById("802169059260629042");
        members = guild.findMembers(n -> {
            return n.getRoles().contains(role);
        }).get();
        for (Member member1 : members) {
            System.out.println(member1);
            if (member1.getUser().isBot()) {
                continue;
            }
            if (armorChannel.getPermissionOverride(member1) == null) {
                armorChannel.createPermissionOverride(member1)
                        .reset()
                        .queue();
            } else {
                armorChannel.putPermissionOverride(member1)
                        .reset()
                        .queue();
            }
            if (hexeChannel.getPermissionOverride(member1) == null) {
                hexeChannel.createPermissionOverride(member1)
                        .reset()
                        .queue();
            } else {
                hexeChannel.putPermissionOverride(member1)
                        .reset()
                        .queue();
            }
            if (werwolfChannel.getPermissionOverride(member1) == null) {
                werwolfChannel.createPermissionOverride(member1)
                        .reset()
                        .queue();
            } else {
                werwolfChannel.putPermissionOverride(member1)
                        .reset()
                        .queue();
            }
            if (votingChannel.getPermissionOverride(member1) == null) {
                votingChannel.createPermissionOverride(member1)
                        .reset()
                        .queue();
            } else {
                votingChannel.putPermissionOverride(member1)
                        .reset()
                        .queue();
            }
            if (seherChannel.getPermissionOverride(member1) == null) {
                seherChannel.createPermissionOverride(member1)
                        .reset()
                        .queue();
            } else {
                seherChannel.putPermissionOverride(member1)
                        .reset()
                        .queue();
            }
            if (erzaehlerChannel.getPermissionOverride(member1) == null) {
                erzaehlerChannel.createPermissionOverride(member1)
                        .reset()
                        .queue();
            } else {
                erzaehlerChannel.putPermissionOverride(member1)
                        .reset()
                        .queue();
            }
        }
    }

    private void initChannels() {
        votingChannel = jda.getTextChannelById("802150014750687283");
        armorChannel = jda.getTextChannelById("802150055262945300");
        hexeChannel = jda.getTextChannelById("802150092005179424");
        seherChannel = jda.getTextChannelById("802150117191843850");
        werwolfChannel = jda.getTextChannelById("802150152810922044");
        erzaehlerChannel = jda.getTextChannelById("802181427357286451");
        lobby = jda.getTextChannelById("802139811376398389");
    }

    public StateMachine getCurrentState() {
        return currentState;
    }

    public void addnewUser(User user, MessageChannel channel) {
        if (currentState == StateMachine.LOBBY) {
            for (Spieler spieler1 : spieler) {
                if (spieler1.getUser().equals(user)) {
                    channel.sendMessage("Du bist der Lobby nicht beigetreten, weil du bereits in der Lobby bist.").queue();
                    return;
                }
            }
            this.spieler.add(new Spieler(user));
            channel.sendMessage(user.getAsMention() + " ist dem Spiel beigetreten.").queue();
            return;
        }
        if (currentState == StateMachine.IDLE) {
            channel.sendMessage("Du bist nicht beigetreten weil noch keine Lobby erstellt wurde.").queue();
        } else {
            channel.sendMessage("Du bist nicht beigetreten weil bereits ein laufendes Spiel existiert.").queue();
        }
    }

    public boolean bgTot() {
        return buergermeister != null && buergermeister.isDead();
    }

    public boolean isFirstNight() {
        return rounds == 0;
    }

    public void incRounds() {
        rounds++;
    }

    public void initRollen() {
        settings.setRemainingSettings(spieler.size());
        List<Rolle> rollen = new ArrayList<>();
        for (int i = 0; i < settings.getArmorAnzahl(); i++) {
            rollen.add(Rolle.ARMOR);
        }
        for (int i = 0; i < settings.getHexenAnzahl(); i++) {
            rollen.add(Rolle.HEXE);
        }
        for (int i = 0; i < settings.getWerwolfAnzahl(); i++) {
            rollen.add(Rolle.WERWOLF);
        }
        for (int i = 0; i < settings.getSeherAnzahl(); i++) {
            rollen.add(Rolle.SEHER);
        }
        for (int i = 0; i < spieler.size() - settings.getUsedSlots(); i++) {
            rollen.add(Rolle.DORFBEWOHNER);
        }
        Collections.shuffle(rollen);
        Collections.shuffle(rollen);
        Collections.shuffle(rollen);
        int i = 0;
        for (Spieler spieler : spieler) {
            spieler.setRolle(rollen.get(i));
            i++;
        }
        for (Spieler spieler : spieler) {
            spieler.sendPrivateMessage("Deine Rolle ist *" + spieler.getRolle() + "*");
            Rolle spielerRolle = spieler.getRolle();
            switch (spielerRolle) {
                case HEXE:
                    addUserToChannel(hexeChannel, spieler.getUser());
                    break;
                case WERWOLF:
                    addUserToChannel(werwolfChannel, spieler.getUser());
                    break;
                case ARMOR:
                    addUserToChannel(armorChannel, spieler.getUser());
                    break;
                case SEHER:
                    addUserToChannel(seherChannel, spieler.getUser());
                    break;
            }
            addUserToChannel(votingChannel, spieler.getUser());
            addUserToChannel(erzaehlerChannel, spieler.getUser());
        }
    }

    public void addUserToChannel(TextChannel channel, User user) {
        Guild guild = lobby.getGuild();
        Role role = guild.getRoleById("802169059260629042");
        Member member = members.get(0);
        for (Member member1 : members) {
            if (member1.getId().equals(user.getId())) {
                member = member1;
            }
        }
        try {
            channel.putPermissionOverride(member)
                    .setAllow(Permission.MESSAGE_ADD_REACTION)
                    .queue();
            channel.putPermissionOverride(member)
                    .setAllow(Permission.VIEW_CHANNEL)
                    .queue();
        } catch (IllegalStateException ignored) {

        }
    }


    public void createLobby(User author, MessageChannel channel) {
        if (currentState == StateMachine.IDLE) {
            currentState = currentState.newState(this);
            channel.sendMessage("Die Lobby wurde erfolgreich erstellt.").queue();
            addnewUser(author, channel);
            serverCreator = getSpielerFromUser(author);
        } else {
            if (currentState == StateMachine.LOBBY) {
                channel.sendMessage("Die Lobby wurde nicht erstellt weil bereits eine Lobby vorhanden ist.").queue();
            } else {
                channel.sendMessage("Die Lobby wurde nicht erstellt weil bereits ein laufendes Spiel vorhanden ist.").queue();
            }
        }
    }

    public void sendList(MessageChannel channel, User author) {
        if (currentState == StateMachine.IDLE) {
            channel.sendMessage("Es gibt keine Team-Liste da noch keine Lobby initializiert wurde.").queue();
        } else if (currentState == StateMachine.LOBBY) {
            sendLobbyList(channel);
        } else {
            if (channel.equals(armorChannel)) {
                channel.sendMessage(createListe(Rolle.ARMOR).build()).queue();
            } else if (channel.equals(hexeChannel)) {
                channel.sendMessage(createListe(Rolle.HEXE).build()).queue();
            } else if (channel.equals(seherChannel)) {
                channel.sendMessage(createListe(Rolle.SEHER).build()).queue();
            } else if (channel.equals(werwolfChannel)) {
                channel.sendMessage(createListe(Rolle.WERWOLF).build()).queue();
            } else if (channel.equals(votingChannel)) {
                channel.sendMessage(createListe(Rolle.DORFBEWOHNER).build()).queue();
            } else if (channel.getType() == ChannelType.PRIVATE) {
                Spieler spieler = getSpielerFromUser(author);
                if (spieler != null) {
                    spieler.sendPrivateMessage(createListe(spieler.getRolle()));
                }
            }
        }
    }

    public Spieler getSpielerFromUser(User author) {
        for (Spieler spieler1 : spieler) {
            if (author.getId().equals(spieler1.getUser().getId()))
                return spieler1;
        }
        return null;
    }

    public void startLobby(MessageChannel channel) {
//        if (spieler.size() < 6) {
//            channel.sendMessage("Das Spiel wurde nicht erstellt, weil es zu wenig Spieler gibt (Minimum 6).").queue();
//            return;
//        } else
        if (currentState != StateMachine.LOBBY) {
            if (currentState == StateMachine.IDLE) {
                channel.sendMessage("Das Spiel wurde nicht erstellt, weil noch keine Lobby erstellt wurde.").queue();
            } else {
                channel.sendMessage("Das Spiel wurde nicht erstellt, weil es schon ein laufendes Spiel gibt.").queue();
            }
            return;
        }
        currentState = currentState.newState(this);
        channel.sendMessage("Das Spiel wurde erfolgreich erstellt").queue();
    }

    public void sendLobbyList(MessageChannel channel) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Spieler in der Lobby");
        eb.setColor(Color.RED);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Spieler spieler : spieler) {
            sb.append(i).append(". ").append(spieler.getNameAsMention()).append("\n");
            i++;
        }
        eb.setDescription(sb.toString());
        channel.sendMessage(eb.build()).queue();
    }

    public EmbedBuilder createListe(Rolle rolle) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Spieler in der Lobby");
        eb.setColor(Color.RED);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Spieler spieler : spieler) {
            if (rolle == spieler.getRolle()) {
                sb.append(i).append(". ").append(spieler.getNameAsMention()).append(" - ").append(spieler.getRolle()).append("  ").append(spieler.getRolle().getEmoji()).append("\n");
            } else {
                sb.append(i).append(". ").append(spieler.getNameAsMention()).append(" - ").append(Rolle.DORFBEWOHNER).append("  ").append(Rolle.DORFBEWOHNER.getEmoji()).append("\n");
            }
            i++;
        }
        eb.setDescription(sb.toString());
        return eb;
    }

    public void removeUser(User author, MessageChannel channel) {

    }

    public Set<Spieler> getSpieler() {
        return spieler;
    }

    public Vote getVote() {
        return vote;
    }

    public void sendNarrator(String s) {
        erzaehlerChannel.sendMessage(s).queue();
    }
}
