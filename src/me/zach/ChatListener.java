package me.zach;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class ChatListener implements Runnable {


    private Player player;
    private String message;
    private MessageChannelEvent.Chat event;

    public ChatListener(Object player, String message, MessageChannelEvent.Chat event) {
        this.message = message;
        this.event = event;
        if(player instanceof Player) {
            this.player = (Player) player;
            run();
        }
    }

    public void run() {
        String[] args = message.split(" ");

    }

}
