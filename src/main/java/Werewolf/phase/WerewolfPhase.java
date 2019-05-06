package Werewolf.phase;

import Werewolf.Game;
import Werewolf.players.Class;
import Werewolf.players.*;

public class WerewolfPhase implements Night {
    Game game;
    Class neededClass = new Werewolf();

    public WerewolfPhase(Game game) {
        this.game = game;
    }
}