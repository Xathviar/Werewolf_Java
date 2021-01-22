package Werewolf;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Werwolf {
    private List<Spieler> user;
    private Spieler buergermeister;
    private Settings settings;

    private StateMachine currentState;
    private int rounds;

    public Werwolf() {
        currentState = StateMachine.IDLE;
        user = new ArrayList<>();
        settings = new Settings();
    }

    public StateMachine getCurrentState() {
        return currentState;
    }

    public void addnewUser(User user) {
        this.user.add(new Spieler(user));
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
        settings.setRemainingSettings(user.size());
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
        for (int i = 0; i < user.size() - settings.getUsedSlots(); i++) {
            rollen.add(Rolle.DORFBEWOHNER);
        }
        Collections.shuffle(rollen);
        Collections.shuffle(rollen);
        Collections.shuffle(rollen);
        Collections.shuffle(user);
        Collections.shuffle(user);
        Collections.shuffle(user);
        for (int i = 0; i < user.size(); i++) {
            user.get(i).setRolle(rollen.get(i));
        }
    }
}
