package me.zach;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.BaseStats;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import com.pixelmonmod.pixelmon.enums.EnumNature;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.TypeTokens;


import java.util.ArrayList;
import java.util.List;

import static org.spongepowered.api.text.Text.NEW_LINE;
import static org.spongepowered.api.text.format.TextColors.*;
import static org.spongepowered.api.text.format.TextStyles.BOLD;

@SuppressWarnings("WeakerAccess")
public class PokeData {

    public static String getName(PartyStorage party, Integer slot){
        String name = party.get(slot).getDisplayName();
        return name != null ? name : "None";
    }

    public static Boolean isNull(Pokemon slot){
        return slot == null;
    }


    public static boolean isInBattle(EntityPixelmon pixelmon) {
        return pixelmon.battleController != null;
    }

    public static EnumType checkHiddenPowerType(Pokemon pokemonData) {
        //https://bulbapedia.bulbagarden.net/wiki/Hidden_Power_(move)/Calculation
        int f = pokemonData.getIVs().get(StatsType.SpecialDefence) % 2;
        int e = pokemonData.getIVs().get(StatsType.SpecialAttack) % 2;
        int d = pokemonData.getIVs().get(StatsType.Speed) % 2;
        int c = pokemonData.getIVs().get(StatsType.Defence) % 2;
        int b = pokemonData.getIVs().get(StatsType.Attack) % 2;
        int a = pokemonData.getIVs().get(StatsType.HP) % 2;

        double fedbca = 32 * f + 16 * e + 8 * d + 4 * c + 2 * b + a;
        int type = (int)Math.floor(fedbca * 15.0 / 63.0);
        switch(type) {
            case 0:
                return EnumType.Fighting;
            case 1:
                return EnumType.Flying;
            case 2:
                return EnumType.Poison;
            case 3:
                return EnumType.Ground;
            case 4:
                return EnumType.Rock;
            case 5:
                return EnumType.Bug;
            case 6:
                return EnumType.Ghost;
            case 7:
                return EnumType.Steel;
            case 8:
                return EnumType.Fire;
            case 9:
                return EnumType.Water;
            case 10:
                return EnumType.Grass;
            case 11:
                return EnumType.Electric;
            case 12:
                return EnumType.Psychic;
            case 13:
                return EnumType.Ice;
            case 14:
                return EnumType.Dragon;
            case 15:
                return EnumType.Dark;
            default:
                return EnumType.Normal;
        }
    }

    public static boolean canLearnOtherMove(Pokemon pokemon, String move) {
        BaseStats baseStats = pokemon.getBaseStats();
        ArrayList<Attack> otherMoves = baseStats.tutorMoves;
        otherMoves.addAll(baseStats.eggMoves);
        return otherMoves.stream().anyMatch(attack -> attack.savedAttack.getLocalizedName().equals(move));
    }

    public static boolean canLearnAbility(Pokemon pokemon, String ability) {
        BaseStats baseStats = pokemon.getBaseStats();
        for (String s : baseStats.abilities) {
            if (s == null || !s.equals(ability)) continue;
            return true;
        }
        return false;
    }

    public static boolean isHiddenAbility(Pokemon pokemon, String ability) {
        BaseStats baseStats = pokemon.getBaseStats();
        return baseStats.abilities[2] != null && baseStats.abilities[2].equalsIgnoreCase(ability);
    }

    public static String getHeldItemName(Pokemon pokemonData) {
        //String itemText = "None";

        //ItemStack itemStack = pokemonData.getHeldItemAsItemHeld().getHeldItemType();
        //if(itemStack != ItemStack.EMPTY && itemStack.getItem() != ItemTypes.AIR) {//Prevent pixelmon bug that allows item:air
        //noinspection ConstantConditions
        //org.spongepowered.api.item.inventory.ItemStack heldItemStack = (org.spongepowered.api.item.inventory.ItemStack) (Object) itemStack;//TODO check format and make less ugh
        //itemText = heldItemStack.getType().getTranslation().get();
        // }

        //return pokemonData.getHeldItemAsItemHeld().getHeldItemType().name();
        return "None";
    }

    public static ArrayList<Text> getFullStatTexts(Pokemon pokemonData) {//TODO neaten up
        ArrayList<Text> lore = new ArrayList<>();

        EnumSpecies enumSpecies = pokemonData.getSpecies();

        int ivHP = pokemonData.getIVs().get(StatsType.HP);
        int ivAttack = pokemonData.getIVs().get(StatsType.Attack);
        int ivDefence = pokemonData.getIVs().get(StatsType.Defence);
        int ivSpeed = pokemonData.getIVs().get(StatsType.Speed);
        int ivSpecialAttack = pokemonData.getIVs().get(StatsType.SpecialAttack);
        int ivSpecialDefense = pokemonData.getIVs().get(StatsType.SpecialDefence);
        int totalIvs = ivHP + ivAttack + ivDefence + ivSpeed + ivSpecialAttack + ivSpecialDefense;

        int evHP = pokemonData.getEVs().get(StatsType.HP);
        int evAttack = pokemonData.getEVs().get(StatsType.Attack);
        int evDefence = pokemonData.getEVs().get(StatsType.Defence);
        int evSpeed = pokemonData.getEVs().get(StatsType.Speed);
        int evSpecialAttack = pokemonData.getEVs().get(StatsType.SpecialAttack);
        int evSpecialDefense = pokemonData.getEVs().get(StatsType.SpecialDefence);
        int totalEvs = evHP + evAttack + evDefence + evSpeed + evSpecialAttack + evSpecialDefense;

        EnumNature nature = pokemonData.getNature();
        Gender gender = pokemonData.getGender();
        EnumGrowth growth = pokemonData.getGrowth();
        EnumPokeballs caughtBall = pokemonData.getCaughtBall();
        String originalTrainerNamer = pokemonData.getOriginalTrainer();
        String heldItemName = getHeldItemName(pokemonData);


        lore.add(Text.of(GRAY, "Level: ", YELLOW, pokemonData.getLevel()));
        lore.add(Text.of(GRAY, "Ability: ", YELLOW, pokemonData.getAbility().getName()));
        lore.add(Text.of(GRAY, "Nature: ", YELLOW, nature.name()));
        lore.add(Text.of(GRAY, "Item: ", YELLOW, heldItemName));
        lore.add(Text.EMPTY);
        lore.add(Text.of(GRAY, "Gender: ", AQUA, gender.name()));
        lore.add(Text.of(GRAY, "Size: ", YELLOW, growth.name()));
        lore.add(Text.of(GRAY, "Pokeball: ", YELLOW, caughtBall.name()));
        lore.add(Text.of(GRAY, "Form: ", YELLOW, (enumSpecies.getNumForms(false) > 0 ? ((Enum)pokemonData.getFormEnum()).name() : "N/A")));
        lore.add(Text.of(GRAY, "OT: ", YELLOW, originalTrainerNamer));
        lore.add(Text.EMPTY);

        lore.add(Text.of(GRAY, "IVs: ", YELLOW, totalIvs, "/186 ", GRAY, "(", (int)(((float)totalIvs * 100f)/ 186f) ,"%)"));
        lore.add(Text.of(GRAY, "   (", YELLOW, ivHP, GRAY, "/", YELLOW, ivAttack, GRAY, "/", YELLOW, ivDefence, GRAY, "/", YELLOW, ivSpecialAttack, GRAY, "/", YELLOW, ivSpecialDefense, GRAY, "/", YELLOW, ivSpeed, GRAY, ")"));
        lore.add(Text.of(GRAY, "   (", RED, "HP", GRAY, "/", RED, "A", GRAY, "/", RED, "D", GRAY, "/", RED, "SA", GRAY, "/", RED, "SD", GRAY, "/", RED, "SPD", GRAY, ")"));
        lore.add(Text.EMPTY);

        lore.add(Text.of(GRAY, "Evs: ", YELLOW, totalEvs, "/510 ", GRAY, "(", (int)(((float)totalEvs * 100f)/ 510f) ,"%)"));
        lore.add(Text.of(GRAY, "   (", YELLOW, evHP, GRAY, "/", YELLOW, evAttack, GRAY, "/", YELLOW, evDefence, GRAY, "/", YELLOW, evSpecialAttack, GRAY, "/", YELLOW, evSpecialDefense, GRAY, "/", YELLOW, evSpeed, GRAY, ")"));
        lore.add(Text.of(GRAY, "   (", RED, "HP", GRAY, "/", RED, "A", GRAY, "/", RED, "D", GRAY, "/", RED, "SA", GRAY, "/", RED, "SD", GRAY, "/", RED, "SPD", GRAY, ")"));
        lore.add(Text.EMPTY);

        lore.add(Text.of(GRAY, "Moves:"));
        lore.add(Text.of(GRAY,"  - ", (pokemonData.getMoveset().get(0) != null ? pokemonData.getMoveset().get(0).savedAttack.getLocalizedName() : "None")));
        lore.add(Text.of(GRAY,"  - ", (pokemonData.getMoveset().get(1) != null ? pokemonData.getMoveset().get(1).savedAttack.getLocalizedName() : "None")));
        lore.add(Text.of(GRAY,"  - ", (pokemonData.getMoveset().get(2) != null ? pokemonData.getMoveset().get(2).savedAttack.getLocalizedName() : "None")));
        lore.add(Text.of(GRAY,"  - ", (pokemonData.getMoveset().get(3) != null ? pokemonData.getMoveset().get(3).savedAttack.getLocalizedName() : "None")));

        /*
        lore.add(Text.of(RED, "[Stats]"));
        lore.add(Text.of(DARK_GREEN, NbtTools.getNicknameFromNbt(pokemonData),
                (pokemonData.getByte(IS_SHINY) == 1 ? Text.of(YELLOW, "\u2605") : Text.EMPTY),
                NEW_LINE, AQUA, "Level: ", pokemonData.getInteger(LEVEL),
                NEW_LINE, YELLOW, "Nature: ", EnumNature.getNatureFromIndex((int)pokemonData.getShort(NATURE)).toString(),
                NEW_LINE, GREEN, "Growth: ", EnumGrowth.getGrowthFromIndex((int)pokemonData.getShort(GROWTH)).toString(),
                NEW_LINE, GOLD, "Ability: ", (pokemonData.getInteger(ABILITY_SLOT) != 2 ? GOLD : GRAY), pokemonData.getString(ABILITY),
                NEW_LINE, DARK_PURPLE, "OT: ", pokemonData.getString(ORIGINAL_TRAINER),
                NEW_LINE, RED, "Item: ", itemText
        ));
        lore.add(Text.of(TextColors.GOLD, "[EVs]"));
        lore.add(Text.of(GOLD, "EVs",
                NEW_LINE, "HP: ", pokemonData.getInteger(EV_HP),
                NEW_LINE, "Attack: ", pokemonData.getInteger(EV_ATTACK),
                NEW_LINE, "Defence: ", pokemonData.getInteger(EV_DEFENCE),
                NEW_LINE, "Sp. Attack: ", pokemonData.getInteger(EV_SPECIAL_ATTACK),
                NEW_LINE, "Sp. Defence: ", pokemonData.getInteger(EV_SPECIAL_DEFENCE),
                NEW_LINE, "Speed: ", pokemonData.getInteger(EV_SPEED)
        ));
        lore.add(Text.of(TextColors.LIGHT_PURPLE, "[IVs]"));
        lore.add(Text.of(LIGHT_PURPLE,  "IVs",
                NEW_LINE, "HP: ", pokemonData.getInteger(IV_HP),
                NEW_LINE, "Attack: ", pokemonData.getInteger(IV_ATTACK),
                NEW_LINE, "Defence: ", pokemonData.getInteger(IV_DEFENCE),
                NEW_LINE, "Sp. Attack: ", pokemonData.getInteger(IV_SP_ATT),
                NEW_LINE, "Sp. Defence: ", pokemonData.getInteger(IV_SP_DEF),
                NEW_LINE, "Speed: ", pokemonData.getInteger(IV_SPEED)
        ));
        lore.add(Text.of(TextColors.BLUE, "[Moves]"));
        lore.add(Text.of(
                BLUE, "Moves",
                NEW_LINE, "Move 1: ", (pokemonData.hasKey(PIXELMON_MOVE_ID+"0") ? AttackBase.getAttackBase(pokemonData.getInteger(PIXELMON_MOVE_ID+"0")).get().getLocalizedName() : "None"),
                NEW_LINE, "Move 2: ", (pokemonData.hasKey(PIXELMON_MOVE_ID+"1") ? AttackBase.getAttackBase(pokemonData.getInteger(PIXELMON_MOVE_ID+"1")).get().getLocalizedName() : "None"),
                NEW_LINE, "Move 3: ", (pokemonData.hasKey(PIXELMON_MOVE_ID+"2") ? AttackBase.getAttackBase(pokemonData.getInteger(PIXELMON_MOVE_ID+"2")).get().getLocalizedName() : "None"),
                NEW_LINE, "Move 4: ", (pokemonData.hasKey(PIXELMON_MOVE_ID+"3") ? AttackBase.getAttackBase(pokemonData.getInteger(PIXELMON_MOVE_ID+"3")).get().getLocalizedName() : "None")
        ));
        */

        return lore;
    }


    public static Text getPartyText(PartyStorage partyData) {
        Pokemon[] pokes = partyData.getAll();
        Text.Builder returnText = TextSerializers.FORMATTING_CODE.deserialize("&aSpecies &b~ &dLevel&r").toBuilder();
        for (int i = 0; i < pokes.length; i++) {
            returnText.append(TextSerializers.FORMATTING_CODE.deserialize("\n&a"+pokes[i].getSpecies().name+"&b ~ &d"+pokes[i].getLevel()));
        }
        return returnText.build();
    }


    //TODO:implement pagination to display individuals when displaying party in chat
   /* public static ArrayList<Text> getPartyPageText(PartyStorage partyData){
        Text.Builder returnText = TextSerializers.FORMATTING_CODE.deserialize("hi").toBuilder();
    }*/

   public static Text getHText(Pokemon pokemonData) {
       Text finalText = Text.of("");

       String name = pokemonData.getSpecies().name;
       int ivHP = pokemonData.getIVs().get(StatsType.HP);
       int ivAttack = pokemonData.getIVs().get(StatsType.Attack);
       int ivDefence = pokemonData.getIVs().get(StatsType.Defence);
       int ivSpeed = pokemonData.getIVs().get(StatsType.Speed);
       int ivSpecialAttack = pokemonData.getIVs().get(StatsType.SpecialAttack);
       int ivSpecialDefense = pokemonData.getIVs().get(StatsType.SpecialDefence);
       int totalIvs = ivHP + ivAttack + ivDefence + ivSpeed + ivSpecialAttack + ivSpecialDefense;

       int evHP = pokemonData.getEVs().get(StatsType.HP);
       int evAttack = pokemonData.getEVs().get(StatsType.Attack);
       int evDefence = pokemonData.getEVs().get(StatsType.Defence);
       int evSpeed = pokemonData.getEVs().get(StatsType.Speed);
       int evSpecialAttack = pokemonData.getEVs().get(StatsType.SpecialAttack);
       int evSpecialDefense = pokemonData.getEVs().get(StatsType.SpecialDefence);
       int totalEvs = evHP + evAttack + evDefence + evSpeed + evSpecialAttack + evSpecialDefense;

       EnumNature nature = pokemonData.getNature();
       Gender gender = pokemonData.getGender();
       EnumGrowth growth = pokemonData.getGrowth();
       EnumPokeballs caughtBall = pokemonData.getCaughtBall();
       String originalTrainerNamer = pokemonData.getOriginalTrainer();
       String heldItemName = getHeldItemName(pokemonData);

       List<String> list = new ArrayList<>();

       System.out.println(ConfigManager.getInstance().getNode().getNode("pokeshow", "style").getChildrenMap().size());
       ConfigManager.getInstance().getNode().getNode("pokeshow", "style").getChildrenMap().forEach((object, node) -> {
           System.out.println(node.toString());
           System.out.println(node.getString());
           String line = node.getString();
           list.add(line);
       });

       ReplaceHelper replaceHelper = new ReplaceHelper("%POKEMON%", name, "%LEVEL%", "" + pokemonData.getLevel());

       Text.Builder statBuilder = Text.builder();
       statBuilder.append(Text.of(DARK_GRAY,BOLD,"[",NONE,GREEN, pokemonData.getSpecies().name, DARK_GRAY,BOLD,"]",RESET));

       Text.Builder messageBuilder = Text.builder();
       list.forEach(text -> {
           messageBuilder.append(TextSerializers.FORMATTING_CODE.deserialize(replaceHelper.replace(text)));
           messageBuilder.append(NEW_LINE);
       });
       statBuilder.onHover(TextActions.showText(messageBuilder.build()));
       return Text.of(statBuilder.build());
   }

    public static Text getHoverText(Pokemon pokemonData) {
        //★ = black star = \u2605

        Text.Builder statBuilder = Text.builder();
        statBuilder.append(Text.of(DARK_GRAY,BOLD,"[",NONE,GREEN, pokemonData.getSpecies().name, DARK_GRAY,BOLD,"]",RESET));

        String itemText = getHeldItemName(pokemonData);


        Text.Builder evBuilder = Text.builder();
        evBuilder.append(Text.of(DARK_GRAY,BOLD,"[",NONE,GREEN,"EVs",DARK_GRAY,BOLD,"]",NONE));
        Text evHover = Text.of(GREEN, "EVs",
                NEW_LINE, GREEN, "HP: ", LIGHT_PURPLE, pokemonData.getEVs().get(StatsType.HP),
                NEW_LINE, GREEN, "Attack: ", LIGHT_PURPLE,pokemonData.getEVs().get(StatsType.Attack),
                NEW_LINE, GREEN, "Defence: ", LIGHT_PURPLE,pokemonData.getEVs().get(StatsType.Defence),
                NEW_LINE, GREEN, "Sp. Attack: ", LIGHT_PURPLE,pokemonData.getEVs().get(StatsType.SpecialAttack),
                NEW_LINE, GREEN, "Sp. Defence: ", LIGHT_PURPLE,pokemonData.getEVs().get(StatsType.SpecialDefence),
                NEW_LINE, GREEN, "Speed: ", LIGHT_PURPLE,pokemonData.getEVs().get(StatsType.Speed)
        );
        evBuilder.onHover(TextActions.showText(evHover));

        Text.Builder ivBuilder = Text.builder();
        ivBuilder.append(Text.of(DARK_GRAY,BOLD,"[",NONE,GREEN,"IVs",DARK_GRAY,BOLD,"]",NONE, RESET));
        Text ivHover = Text.of(GREEN, "IVs",
                NEW_LINE, GREEN, "HP: ", LIGHT_PURPLE,pokemonData.getIVs().get(StatsType.HP),
                NEW_LINE, GREEN, "Attack: ", LIGHT_PURPLE, pokemonData.getIVs().get(StatsType.Attack),
                NEW_LINE, GREEN, "Defence: ", LIGHT_PURPLE, pokemonData.getIVs().get(StatsType.Defence),
                NEW_LINE, GREEN, "Sp. Attack: ", LIGHT_PURPLE, pokemonData.getIVs().get(StatsType.SpecialAttack),
                NEW_LINE, GREEN, "Sp. Defence: ", LIGHT_PURPLE, pokemonData.getIVs().get(StatsType.SpecialDefence),
                NEW_LINE, GREEN, "Speed: ", LIGHT_PURPLE, pokemonData.getIVs().get(StatsType.Speed)
        );
        ivBuilder.onHover(TextActions.showText(ivHover));

        int ivTotal = pokemonData.getIVs().get(StatsType.HP) + pokemonData.getIVs().get(StatsType.Attack) + pokemonData.getIVs().get(StatsType.Defence) + pokemonData.getIVs().get(StatsType.SpecialAttack) + pokemonData.getIVs().get(StatsType.SpecialDefence) + pokemonData.getIVs().get(StatsType.Speed);
        int evTotal = pokemonData.getEVs().get(StatsType.HP) + pokemonData.getEVs().get(StatsType.Attack) + pokemonData.getEVs().get(StatsType.Defence) + pokemonData.getEVs().get(StatsType.SpecialAttack) + pokemonData.getEVs().get(StatsType.SpecialDefence) + pokemonData.getEVs().get(StatsType.Speed);

        Text statHover = Text.of(DARK_GREEN, pokemonData.getDisplayName(),
                (pokemonData.isShiny() ? Text.of(YELLOW, "\u2605") : Text.EMPTY),
                NEW_LINE, GREEN, "Level: ",LIGHT_PURPLE, pokemonData.getLevel(),
                NEW_LINE, GREEN, "Nature: ",LIGHT_PURPLE, pokemonData.getNature().toString(), DARK_GRAY, " (", GREEN, "+", pokemonData.getNature().increasedStat.name(), DARK_GRAY, "/", RED, "-" , pokemonData.getNature().decreasedStat, DARK_GRAY, ")",
                NEW_LINE, GREEN, "Growth: ",LIGHT_PURPLE, pokemonData.getGrowth().toString(),
                NEW_LINE, GREEN, "Ability: ",LIGHT_PURPLE, (pokemonData.getAbilitySlot() != 2 ? GOLD : LIGHT_PURPLE), pokemonData.getAbility().getName(),
                NEW_LINE, GREEN, "OT: ",LIGHT_PURPLE, pokemonData.getOriginalTrainer(),
                NEW_LINE, GREEN, "Item: ",LIGHT_PURPLE, itemText,
                NEW_LINE, GREEN, "Pokeball: ",LIGHT_PURPLE, pokemonData.getCaughtBall().name(),
                NEW_LINE, NEW_LINE, AQUA, "IVs: ", LIGHT_PURPLE, ivTotal, "/186", GREEN, "(", LIGHT_PURPLE, (ivTotal*100/186), "%", GREEN, ")",
                NEW_LINE, RED, "HP: ", pokemonData.getIVs().hp, DARK_GRAY, "/", GOLD, "Atk: ", pokemonData.getIVs().attack, DARK_GRAY, "/", YELLOW, "Def: ", pokemonData.getIVs().defence,
                NEW_LINE, BLUE, "SpA: ", pokemonData.getIVs().specialAttack, DARK_GRAY, "/", GREEN, "SpD: ", pokemonData.getIVs().specialDefence, DARK_GRAY, "/", LIGHT_PURPLE, "Spe: ", pokemonData.getIVs().speed,
                NEW_LINE, AQUA, "EVs: ", LIGHT_PURPLE, evTotal, "/510", GREEN, "(", LIGHT_PURPLE, (evTotal*100/510), "%", GREEN, ")",
                NEW_LINE, RED, "HP: ", pokemonData.getEVs().hp, DARK_GRAY, "/", GOLD, "Atk: ", pokemonData.getEVs().attack, DARK_GRAY, "/", YELLOW, "Def: ", pokemonData.getEVs().defence,
                NEW_LINE, BLUE, "SpA: ", pokemonData.getEVs().specialAttack, DARK_GRAY, "/", GREEN, "SpD: ", pokemonData.getEVs().specialDefence, DARK_GRAY, "/", LIGHT_PURPLE, "Spe: ", pokemonData.getEVs().speed
        );
        statBuilder.onHover(TextActions.showText(statHover));

        Text.Builder movesBuilder = Text.builder();
        movesBuilder.append(Text.of(DARK_GRAY,BOLD,"[",NONE,GREEN,"Moves",DARK_GRAY,BOLD,"]",NONE));
        Text movesHover = Text.of(
                GREEN, "Move 1: ", LIGHT_PURPLE, pokemonData.getMoveset().get(0) != null ? pokemonData.getMoveset().get(0).toString() : "None",
                NEW_LINE, GREEN, "Move 2: ", LIGHT_PURPLE, pokemonData.getMoveset().get(1) != null ? pokemonData.getMoveset().get(1).toString() : "None",
                NEW_LINE, GREEN, "Move 3: ", LIGHT_PURPLE, pokemonData.getMoveset().get(2) != null ? pokemonData.getMoveset().get(2).toString() : "None",
                NEW_LINE, GREEN, "Move 4: ", LIGHT_PURPLE, pokemonData.getMoveset().get(3) != null ? pokemonData.getMoveset().get(3).toString() : "None"
        );
        movesBuilder.onHover(TextActions.showText(movesHover));

        return Text.of(/*DARK_GRAY,BOLD,"[",NONE,RESET,GREEN, pokemonData.getSpecies().name,DARK_GRAY,BOLD,"]",NONE,RESET, " ", */statBuilder.build(), " ", /*evBuilder.build(), " ", ivBuilder.build(), " ", */movesBuilder.build());
    }

}