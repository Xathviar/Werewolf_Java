package Werewolf.commands;

import Werewolf.Werewolf;
import Werewolf.Player;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Join extends ListenerAdapter {
    private Werewolf werewolf;
    private Character prefix;

    public Join(Werewolf werewolf, Character prefix) {
        this.werewolf = werewolf;
        this.prefix = prefix;

    }

    @Override
    public void onMessageReceived (MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        Player player = new Player(author);

        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "join")) {
                if (!werewolf.game) {
                    channel.sendMessage("Please create a game!").queue();

                }else if (werewolf.runningGame) {
                    channel.sendMessage("There is already a running game.").queue();
                }else {
                    channel.sendMessage("Player " + author.getAsMention() + " has been successfully added").queue();
                    werewolf.players.add(player);
                }
            }
        }
    }
}
