package moddedmite.ohmycommands.util;

import net.minecraft.ChatMessageComponent;

public class Components {
    public static ChatMessageComponent literal(String s) {
        return ChatMessageComponent.createFromText(s);
    }

    public static ChatMessageComponent translatable(String key) {
        return ChatMessageComponent.createFromTranslationKey(key);
    }

    public static ChatMessageComponent translatable(String key, Object... objects) {
        return ChatMessageComponent.createFromTranslationWithSubstitutions(key, objects);
    }
}
