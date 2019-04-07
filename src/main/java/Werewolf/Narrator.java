package Werewolf;

import Werewolf.commands.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Scanner;

public class Narrator {
    private JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input your Discord-Bot Token: ");
            String token = scanner.nextLine();
            Narrator narrator = new Narrator(token);
            narrator.jda.awaitReady();
        }else {
            Narrator narrator = new Narrator(args[0]);
            narrator.jda.awaitReady();
        }
    }
    private Narrator(String token) throws InterruptedException, LoginException {
        Character prefix = '.';
        Werewolf werewolf = new Werewolf();
        this.jda =  new JDABuilder(token)
                .setStatus(OnlineStatus.ONLINE)
                .setGame(Game.playing("Werewolf"))
                .setAutoReconnect(true)
                .setAudioEnabled(false)
                .addEventListener(new ReadyListener())
                .addEventListener(new Create(werewolf, prefix))
                .addEventListener(new Join(werewolf, prefix))
                .addEventListener(new List(werewolf, prefix))
                .addEventListener(new Start(werewolf, prefix))
                .addEventListener(new Vote(werewolf, prefix))
                .addEventListener(new Vote_WW(werewolf, prefix))
                .addEventListener(new Stop(werewolf, prefix))
                .addEventListener(new FYou(prefix))
                .addEventListener(new Help(prefix))
                .build();
        jda.awaitReady();

    }
}
