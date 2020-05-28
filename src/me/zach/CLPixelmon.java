package me.zach;

import com.google.inject.Inject;
import me.zach.commands.PokeCommand;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.nio.file.Path;

@Plugin(id = "clpixelmon", name = "CLPixelmon", version = "1.0", description = "A plugin for CL.")

public class CLPixelmon {

    @Inject
    @DefaultConfig(sharedRoot =  true)
    private Path configFile;

    @Inject
    Logger logger;

    @Listener
    public void onServerStartup(GameStartedServerEvent event) {
        ConfigManager.getInstance().setup(configFile, this);
        initCommands();
        logger.info("CLPixelmon has been enabled!!");
    }

    @Listener
    public void onChatMessage(MessageChannelEvent.Chat event) {
        new ChatListener(event.getSource(), event.getMessage().toPlain(), event);
    }

    private CommandSpec pokeShowCommand = CommandSpec.builder()
            .description(Text.of("Show pokemon in chat."))
            .permission("clpixelmon.showpokemon")
            .arguments(GenericArguments.integer(Text.of("pokenum")))
            .executor(new PokeCommand())
            .build();

    public void initCommands() {
        Sponge.getCommandManager().register(this, pokeShowCommand, "poke");
    }


}
