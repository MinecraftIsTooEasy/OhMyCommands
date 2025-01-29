package com.github.Debris.OhMyCommands.mixins.command.commands;

import com.github.Debris.OhMyCommands.util.NameIDTranslator;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.CommandBase.*;

@Mixin(CommandEnchant.class)
public abstract class CommandEnchantMixin {
    @Shadow
    public abstract String getCommandUsage(ICommandSender par1ICommandSender);

    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
    private void inject(ICommandSender par1ICommandSender, String[] args, CallbackInfo ci) {
        ci.cancel();
        if (args.length < 1) {
            throw new WrongUsageException(this.getCommandUsage(par1ICommandSender));
        }
        ItemStack heldItemStack = getCommandSenderAsPlayer(par1ICommandSender).getHeldItemStack();
        if (heldItemStack == null) {
            throw new CommandException("commands.enchant.noItem");
        }

        if (args[0].equals("clear")) {
            if (heldItemStack.hasTagCompound()) {
                heldItemStack.clearEnchantTagList();
                notifyAdmins(par1ICommandSender, "commands.enchant.clear.success");
            }
            return;
        }
        if (!args[0].equals("add")) {
            throw new WrongUsageException(this.getCommandUsage(par1ICommandSender));
        }
        int enchantmentLevel = 1;
        Enchantment enchantment = NameIDTranslator.getEnchantmentByText(par1ICommandSender, args[1]);
        if (enchantment == null) {
            throw new NumberInvalidException("commands.enchant.notFound", args[1]);
        }
        if (args.length >= 3) {
            enchantmentLevel = parseIntBounded(par1ICommandSender, args[2], 1, enchantment.getNumLevels());
        }
        if (heldItemStack.getItem() == Item.book || heldItemStack.getItem() == Item.enchantedBook) {
            heldItemStack.itemID = Item.enchantedBook.itemID;
            Item.enchantedBook.addEnchantment(heldItemStack, new EnchantmentData(enchantment.effectId, enchantmentLevel));
            notifyAdmins(par1ICommandSender, "commands.enchant.success");
        } else if (!enchantment.canEnchantItem(heldItemStack.getItem())) {
            throw new CommandException("commands.enchant.cantEnchant");
        } else {
            if (heldItemStack.hasTagCompound()) {
                NBTTagList var8 = heldItemStack.getEnchantmentTagList();
                if (var8 != null) {
                    for (int var9 = 0; var9 < var8.tagCount(); ++var9) {
                        short var10 = ((NBTTagCompound) var8.tagAt(var9)).getShort("id");

                        if (Enchantment.enchantmentsList[var10] != null) {
                            Enchantment var11 = Enchantment.enchantmentsList[var10];

                            if (!var11.canApplyTogether(enchantment)) {
                                throw new CommandException("commands.enchant.cantCombine", enchantment.getName(), ((NBTTagCompound) var8.tagAt(var9)).getShort("lvl"));
                            }
                        }
                    }
                }
            }
            heldItemStack.addEnchantment(enchantment, enchantmentLevel);
            notifyAdmins(par1ICommandSender, "commands.enchant.success");
        }
    }


//    @Inject(method = "processCommand", at = @At("HEAD"), cancellable = true)
//    private void injectBefore(ICommandSender par1ICommandSender, String[] par2ArrayOfStr, CallbackInfo ci) {
//        ci.cancel();
//        if (par2ArrayOfStr.length < 2) {
//            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
//        } else {
//            ServerPlayer var3 = CommandBase.getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
//            int var4 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[1], 0, Enchantment.enchantmentsList.length - 1);
//            int var5 = 1;
//            ItemStack var6 = var3.getHeldItemStack();
//            if (var6 == null) {
//                throw new CommandException("commands.enchant.noItem", new Object[0]);
//            } else {
//                Enchantment var7 = Enchantment.enchantmentsList[var4];
//                if (var7 == null) {
//                    throw new NumberInvalidException("commands.enchant.notFound", new Object[]{var4});
//                } else if (!var7.canEnchantItem(var6.getItem())) {
//                    throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
//                } else {
//                    if (par2ArrayOfStr.length >= 3) {
//                        var5 = CommandBase.parseIntBounded(par1ICommandSender, par2ArrayOfStr[2], 1, var7.getNumLevels());
//                    }
//
//                    if (var6.hasTagCompound()) {
//                        NBTTagList var8 = var6.getEnchantmentTagList();
//                        if (var8 != null) {
//                            for(int var9 = 0; var9 < var8.tagCount(); ++var9) {
//                                short var10 = ((NBTTagCompound)var8.tagAt(var9)).getShort("id");
//                                if (Enchantment.enchantmentsList[var10] != null) {
//                                    Enchantment var11 = Enchantment.enchantmentsList[var10];
//                                    if (!var11.canApplyTogether(var7)) {
//                                        throw new CommandException("commands.enchant.cantCombine", new Object[]{var7.getName(), ((NBTTagCompound)var8.tagAt(var9)).getShort("lvl")});
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    var6.addEnchantment(var7, var5);
//                    CommandBase.notifyAdmins(par1ICommandSender, "commands.enchant.success", new Object[0]);
//                }
//            }
//        }
//    }


    /**
     * @author
     * @reason
     */
    @Overwrite
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length == 1) return getListOfStringsMatchingLastWord(par2ArrayOfStr, "add", "clear");
        if (par2ArrayOfStr.length == 2) {
            if (par1ICommandSender instanceof ServerPlayer) {
                return getListOfStringsMatchingLastWord(par2ArrayOfStr, NameIDTranslator.filterEnchantmentTexts(getCommandSenderAsPlayer(par1ICommandSender).getHeldItemStack().getItem()));
            }
            return getListOfStringsMatchingLastWord(par2ArrayOfStr, NameIDTranslator.getEnchantmentTexts());
        }
        return null;
    }
}
