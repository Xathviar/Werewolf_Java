package Werewolf;

import Werewolf.commands.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Narrator {

    public static void main(String[] args) throws LoginException, InterruptedException {
        Character prefix = '.';
        Werewolf werewolf = new Werewolf();
        JDA jda = new JDABuilder(new Config().token)
                .setStatus(OnlineStatus.ONLINE)
                .setGame(Game.playing("Werewolf"))
                .setAutoReconnect(true)
                .setAudioEnabled(false)
                .addEventListener(new ReadyListener())
                .addEventListener(new Create(werewolf, prefix))
                .addEventListener(new Join(werewolf, prefix))
                .addEventListener(new List(werewolf, prefix))
                .addEventListener(new Start(werewolf, prefix))
                .build();

        jda.awaitReady();
    }

}
