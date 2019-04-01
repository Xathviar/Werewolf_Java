package Werewolf;

import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Werewolf {
    public boolean game;
    public boolean runningGame;
    public List<Player> players;

    public Werewolf() {
        game = false;
        runningGame = false;
        players = new ArrayList<Player>();
    }


}
