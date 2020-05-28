package me.zach.commands;

import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import me.zach.ConfigManager;
import me.zach.PokeData;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.TypeTokens;
import org.spongepowered.common.data.value.mutable.SpongeListValue;

import java.util.ArrayList;
import java.util.List;

public class PokeCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("Command Executed"));
        if(!(src instanceof Player)) {
            src.sendMessage(Text.of(TextColors.DARK_RED, "Can only be used by players!"));
            return CommandResult.empty();
        }
        Player player = (Player) src;
        int pokeSlot = args.<Integer>getOne("pokenum").get();
        PartyStorage party = Pixelmon.storageManager.getParty(player.getUniqueId());
        Pokemon pokemon = party.get(pokeSlot);
        if(pokemon == null) {
            player.sendMessage(Text.of(TextColors.DARK_RED + "You have no pokemon in that slot!"));
            return CommandResult.empty();
        }
        Text wat = PokeData.getHText(pokemon);
        Text finalmessage = Text.builder("[").color(TextColors.DARK_GRAY)
                .append(Text.builder(player.getName()).color(TextColors.LIGHT_PURPLE).build())
                .append(Text.builder("] ").color(TextColors.DARK_GRAY).build())
                .append(Text.of(Text.NEW_LINE))
                .append(wat).build();
        //Text finalmessage = Text.builder("[" + pokeSlot).color(TextColors.DARK_GRAY).build();
        MessageChannel.TO_PLAYERS.send(finalmessage);
        return CommandResult.success();
    }
}
