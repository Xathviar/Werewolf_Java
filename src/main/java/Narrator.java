
import listeners.MessageListener;
import listeners.ReadyListener;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Narrator {


    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = new JDABuilder(new Config().token)
                .setStatus(OnlineStatus.ONLINE)
                .setGame(Game.playing("Werewolf"))
                .setAutoReconnect(true)
                .setAudioEnabled(false)
                .addEventListener(new ReadyListener())
                .addEventListener(new MessageListener())
                .build();

        jda.awaitReady();
    }

}
