package Werewolf.commands;

import Werewolf.Werwolf;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class LobbyCommands extends ListenerAdapter {
    private Werwolf werwolf;

    public LobbyCommands(Werwolf werwolf) {
        this.werwolf = werwolf;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        String msg = message.getContentDisplay();

        if (!author.isBot()) {
            if (msg.toLowerCase().startsWith("!lobby")) {
                werwolf.createLobby(author, channel);
            } else if (msg.toLowerCase().startsWith("!join")) {
                werwolf.addnewUser(author, channel);
            } else if (msg.toLowerCase().startsWith("!list")) {
                werwolf.sendList(channel, author);
            } else if (msg.toLowerCase().startsWith("!start")) {
                werwolf.startLobby(channel);
            } else if (msg.toLowerCase().startsWith("!leave")) {
                werwolf.removeUser(author, channel);
            }
        }
    }
}
