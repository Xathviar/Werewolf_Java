package Werewolf;

import Werewolf.commands.*;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class Narrator {
    private JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your Discord-Bot Token: ");
        String token = scanner.nextLine();
        new Narrator(token);
    }

    private Narrator(String token) throws InterruptedException, LoginException {
        Character prefix = '.';
        Game game = new Game();
        this.jda =  new JDABuilder(token)
                .setStatus(OnlineStatus.ONLINE)
                .setGame(net.dv8tion.jda.core.entities.Game.playing("Werewolf"))
                .setAutoReconnect(true)
                .setAudioEnabled(false)
                .addEventListener(new ReadyListener())
                .addEventListener(new Create(game, prefix))
                .addEventListener(new Join(game, prefix))
                .addEventListener(new List(game, prefix))
                .addEventListener(new Start(game, prefix))
                .addEventListener(new Vote(game, prefix))
                .addEventListener(new FYou(prefix))
                .build();
        jda.awaitReady();

    }
}
