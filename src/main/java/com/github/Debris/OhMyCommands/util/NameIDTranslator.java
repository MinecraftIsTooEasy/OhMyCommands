package com.github.Debris.OhMyCommands.util;

import net.minecraft.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static net.minecraft.CommandBase.parseInt;
import static net.minecraft.CommandBase.parseIntBounded;

public class NameIDTranslator {
    public static BiomeGenBase getBiomeByText(ICommandSender iCommandSender, String text) {
        for (BiomeGenBase biomeGenBase : BiomeGenBase.biomeList) {
            if (biomeGenBase == null) continue;
            if (biomeGenBase.biomeName.replace(" ", "").equalsIgnoreCase(text)) {
                return biomeGenBase;
            }
        }
        return BiomeGenBase.biomeList[parseInt(iCommandSender, text)];
    }

    public static String[] getBiomeTexts() {
        return Arrays.stream(BiomeGenBase.biomeList)
                .filter(Objects::nonNull)
                .map(x -> x.biomeName.replace(" ", ""))
                .filter(x -> !x.startsWith("Sky") && !x.startsWith("Hell") && !x.startsWith("Underworld"))
                .toArray(String[]::new);
    }

    public static Enchantment getEnchantmentByText(ICommandSender iCommandSender, String text) {
        for (Enchantment enchantment : Enchantment.enchantmentsList) {
            if (enchantment == null) continue;
            if (enchantment.getName().replace(" ", "").substring(12).equalsIgnoreCase(text)) {
                return enchantment;
            }
        }
        return Enchantment.enchantmentsList[parseInt(iCommandSender, text)];
    }

    public static String[] getEnchantmentTexts() {
        return Arrays.stream(Enchantment.enchantmentsList)
                .filter(Objects::nonNull)
                .map(x -> x.getName().replace(" ", ""))
                .map(x -> x.substring(12))
                .toArray(String[]::new);
    }

    public static String[] filterEnchantmentTexts(Item item) {
        return Arrays.stream(Enchantment.enchantmentsList)
                .filter(Objects::nonNull)
                .filter(enchantment -> enchantment.canEnchantItem(item))
                .map(x -> x.getName().replace(" ", ""))
                .map(x -> x.substring(12))
                .toArray(String[]::new);
    }

    public static int getBlockIDByText(ICommandSender iCommandSender, String text) {
        for (Block block : Block.blocksList) {
            if (block == null) continue;
            if (block.getUnlocalizedName().replace(" ", "").substring(5).equalsIgnoreCase(text)) {
                return block.blockID;
            }
        }
        return parseInt(iCommandSender, text);
    }

    public static String[] getBlockTexts() {
        return Arrays.stream(Block.blocksList)
                .filter(Objects::nonNull)
                .map(x -> x.getUnlocalizedName().replace(" ", ""))
                .map(x -> x.substring(5))
                .toArray(String[]::new);
    }

    public static Potion getEffectIdByText(ICommandSender iCommandSender, String text) {
        for (Potion potionType : Potion.potionTypes) {
            if (potionType == null) continue;
            if (potionType.getName().replace(" ", "").substring(7).equalsIgnoreCase(text)) {
                return potionType;
            }
        }
        return Potion.potionTypes[parseInt(iCommandSender, text)];
    }

    public static String[] getEffectTexts() {
        return Arrays.stream(Potion.potionTypes)
                .filter(Objects::nonNull)
                .map(x -> x.getName().replace(" ", ""))
                .map(x -> x.substring(7))
                .toArray(String[]::new);
    }

    @SuppressWarnings("unchecked")
    public static String[] getEntityTexts() {
        List<EntityListEntry> entries = EntityList.entries;
        return entries.stream().map(x -> x.name).toArray(String[]::new);
    }

    public static Curse getCurseByText(ICommandSender iCommandSender, String text) {
        for (Curse curse : Curse.cursesList) {
            if (curse == null) continue;
            if (curse.key.equalsIgnoreCase(text)) {
                return curse;
            }
        }
        return Curse.cursesList[parseIntBounded(iCommandSender, text, 0, Curse.cursesList.length)];
    }

    public static String[] getCurseTexts() {
        return Arrays.stream(Curse.cursesList)
                .filter(Objects::nonNull)
                .map(x -> x.key)
                .toArray(String[]::new);
    }
}
