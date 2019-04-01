package Werewolf;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Night {

    Werewolf werewolf;
    MessageReceivedEvent event;

    public Night(Werewolf werewolf, MessageReceivedEvent event) {
        this.event = event;
        this.werewolf = werewolf;
    }
}
