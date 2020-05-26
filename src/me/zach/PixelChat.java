package me.zach;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "clpixelchat", name = "CLPixelChat", version = "1.0", description = "A plugin for showing pokemon in chat.")

public class PixelChat {

    @Inject
    Logger logger;

    @Listener
    public void onServerStartup(GameStartedServerEvent event) {
        logger.info("PixelChat has been enabled!!");
    }

    @Listener
    public void onChatMessage(MessageChannelEvent.Chat event) {
        new ChatListener(event.getSource(), event.getMessage().toPlain(), event);
    }


}
