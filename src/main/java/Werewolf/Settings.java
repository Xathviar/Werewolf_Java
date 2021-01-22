package Werewolf;

public class Settings {
    private int hexenAnzahl;
    private int seherAnzahl;
    private int werwolfAnzahl;
    private int armorAnzahl;
    private int maxVotingTime;

    public Settings() {
    }

    public void setHexenAnzahl(int hexenAnzahl, int spielerAnzahl) {
        this.hexenAnzahl = hexenAnzahl;
    }

    public void setSeherAnzahl(int seherAnzahl) {
        this.seherAnzahl = seherAnzahl;
    }

    public void setWerwolfAnzahl(int werwolfAnzahl) {
        this.werwolfAnzahl = werwolfAnzahl;
    }

    public void setArmorAnzahl(int armorAnzahl) {
        this.armorAnzahl = armorAnzahl;
    }

    public void setMaxVotingTime(int maxVotingTime) {
        this.maxVotingTime = maxVotingTime;
    }

    public int getHexenAnzahl() {
        return hexenAnzahl;
    }

    public int getSeherAnzahl() {
        return seherAnzahl;
    }

    public int getWerwolfAnzahl() {
        return werwolfAnzahl;
    }

    public int getArmorAnzahl() {
        return armorAnzahl;
    }

    public int getMaxVotingTime() {
        return maxVotingTime;
    }

    public int getUsedSlots() {
        return werwolfAnzahl + hexenAnzahl + armorAnzahl + seherAnzahl;
    }

    public void setRemainingSettings(int spielerAnzahl) {
        if (seherAnzahl == 0)
            seherAnzahl = 1;
        if (hexenAnzahl == 0)
            hexenAnzahl = spielerAnzahl > 5 ? 1 : 0;
        if (armorAnzahl == 0)
            armorAnzahl = spielerAnzahl > 7 ? 1 : 0;
        if (werwolfAnzahl == 0) {
            if (spielerAnzahl > 11) {
                werwolfAnzahl = 3;
            } else if (spielerAnzahl > 8) {
                werwolfAnzahl = 2;
            } else {
                werwolfAnzahl = 1;
            }
        }
        if (maxVotingTime == 0)
            maxVotingTime = 20 + (spielerAnzahl * 3);
    }
}
