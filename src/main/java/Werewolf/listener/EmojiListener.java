package Werewolf.listener;

import Werewolf.Werwolf;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EmojiListener extends ListenerAdapter {
    private Werwolf werwolf;

    public EmojiListener(Werwolf werwolf) {
        this.werwolf = werwolf;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User reactor = event.getUser();
        MessageChannel channel = event.getChannel();
        String msgID = event.getMessageId();
        super.onMessageReactionAdd(event);
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        super.onMessageReactionRemove(event);
    }
}
