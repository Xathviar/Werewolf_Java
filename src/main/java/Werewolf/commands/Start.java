package Werewolf.commands;

import Werewolf.Werewolf;
import Werewolf.Player;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Start extends ListenerAdapter {
    private Werewolf werewolf;
    private Character prefix;

    public Start(Werewolf werewolf, Character prefix) {
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
        if (!isBot) {
            if (msg.equalsIgnoreCase(prefix + "start")) {
                if (!werewolf.isGame()) {
                    channel.sendMessage("Please create a game!").queue();

                }else if (werewolf.isRunningGame()) {
                    channel.sendMessage("There is already a running game.").queue();
                //}else if (werewolf.getPlayers().size() < 5) {
                //    channel.sendMessage("Please add " + (5 - werewolf.getPlayers().size()) + " Players").queue();
                }else {
                    channel.sendMessage("Game has successfully started.").queue();
                    werewolf.setRunningGame(true);
                    werewolf.setRoles();
                    for (Player player: werewolf.getPlayers()) {
                        System.out.println(player.getPlayer().getName() + " is a" + (player.isWerewolf()? " Werewolf" : " Villager"));
                    }
                }
            }
        }
    }
}
