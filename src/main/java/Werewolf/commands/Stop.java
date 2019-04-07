package Werewolf.commands;

import Werewolf.Werewolf;
import Werewolf.Player;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Stop extends ListenerAdapter {
    Werewolf werewolf;
    private Character prefix;

    public Stop(Werewolf werewolf, Character prefix) {
        this.werewolf = werewolf;
        this.prefix = prefix;
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        String msg = message.getContentDisplay();       //msg
        boolean isBot = author.isBot();                 //Determines whether user is a bot or not
        if (!isBot && msg.equalsIgnoreCase(prefix + "stop")) {
            Player player = werewolf.getPlayers().stream().filter(n -> n.getPlayer().getId().equals(author.getId())).findFirst().get();
            player.setWantsToStop(true);
            if (werewolf.checkStop()) {
                werewolf.setGame(false);
                werewolf.setRunningGame(false);
                werewolf.setNight(true);
                werewolf.resetVoted();
                werewolf.resetVoting();
                werewolf.resetPlayer();
                werewolf.resetStop();
                channel.sendMessage("Game has been successfully stopped").queue();
            } else {
                double needed = (werewolf.getPlayers().size() * 0.6);
                double current = werewolf.getPlayers().stream().filter(n -> n.wantsToStop()).count();
                channel.sendMessage("There are currently " + current + " Players voting to stop the game.").queue();
                channel.sendMessage("You still need " + (needed - current) + " more.").queue();
            }
        }
    }
}
