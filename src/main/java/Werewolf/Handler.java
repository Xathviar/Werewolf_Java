package Werewolf;

import Werewolf.commands.LobbyCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import javax.swing.*;

public class Handler {
    private JDA jda;

    public static void main(String[] args) throws LoginException, InterruptedException {
        String token;
        if (args.length > 0) {
            token = args[0];
        } else {
            token = JOptionPane.showInputDialog("Enter your Discord Token");
        }
        try {
            new Handler(token);
        } catch (Exception e) {
            e.printStackTrace();
            token = JOptionPane.showInputDialog("Enter your Discord Token");
            new Handler(token);
        }
    }


    private Handler(String token) throws InterruptedException, LoginException {
        Character prefix = '.';
        JDA jda;
        Werwolf werwolf = new Werwolf();
        jda = JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new LobbyCommands(werwolf))
                .setActivity(Activity.playing("!help for a list of commands"))
                .setAutoReconnect(true)
//                .setAudioEnabled(false)
//                .addEventListeners(new ReadyListener(), new StoryCommand(logic), new HelpCommand())
                .enableIntents(GatewayIntent.GUILD_PRESENCES
                        , GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES
                        , GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_EMOJIS
                        , GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS)
                .build();
        jda.awaitReady();
        werwolf.setJDA(jda);
    }

    private boolean getStatus() {
        return jda.getStatus() == JDA.Status.CONNECTED;
    }

    boolean stopDiscord() {
        if (getStatus()) {
            jda.shutdownNow();
            return true;
        }
        return false;
    }

    boolean startDiscord() throws InterruptedException {
        if (!getStatus()) {
            jda.awaitReady();
            return true;
        }
        return false;
    }
}
