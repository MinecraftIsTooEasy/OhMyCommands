package com.github.Debris.OhMyCommands.util;

import com.github.Debris.OhMyCommands.api.BlockPos;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;
import net.minecraft.NumberInvalidException;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Collections;
import java.util.List;

public class MiscUtil {
    public static BlockPos parseBlockPos(ICommandSender sender, String[] args, int startIndex) {
        return new BlockPos(CommandBase.parseIntBounded(sender, args[startIndex], -30000000, 30000000), CommandBase.parseIntBounded(sender, args[startIndex + 1], 0, 256), CommandBase.parseIntBounded(sender, args[startIndex + 2], -30000000, 30000000));
    }

    public static double parseDouble(String input) throws NumberInvalidException {
        try {
            double d0 = Double.parseDouble(input);

            if (!Doubles.isFinite(d0)) {
                throw new NumberInvalidException("commands.generic.num.invalid", input);
            } else {
                return d0;
            }
        } catch (NumberFormatException var3) {
            throw new NumberInvalidException("commands.generic.num.invalid", input);
        }
    }

    public static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }

    public static List<String> getTabCompletionCoordinate(String[] inputArgs, int index, @Nullable BlockPos pos) {
        if (pos == null) {
            return Lists.newArrayList("~");
        } else {
            int i = inputArgs.length - 1;
            String s;

            if (i == index) {
                s = Integer.toString(pos.getX());
            } else if (i == index + 1) {
                s = Integer.toString(pos.getY());
            } else {
                if (i != index + 2) {
                    return Collections.<String>emptyList();
                }
                s = String.valueOf(pos.getZ());
            }
            return Lists.newArrayList(s);
        }
    }

    public static void copyToClipBoard(String content) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(content);
        clipboard.setContents(selection, null);
    }

    public static int[] chunkCoordinatesFromLong(long chunkLong) {
        int[] result = new int[2];
        result[0] = (int) (chunkLong & 0xFFFFFFFFL); // Extract low 32 bits
        result[1] = (int) ((chunkLong >>> 32) & 0xFFFFFFFFL); // Extract high 32 bits
        return result;
    }

}
