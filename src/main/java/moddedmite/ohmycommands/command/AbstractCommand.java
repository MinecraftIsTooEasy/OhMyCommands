package moddedmite.ohmycommands.command;

import net.minecraft.CommandBase;
import net.minecraft.ICommandSender;

public abstract class AbstractCommand extends CommandBase {
    private final String name;

    public AbstractCommand(String name) {
        this.name = name;
    }

    @Override
    public String getCommandName() {
        return this.name;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return String.format("commands.%s.usage", this.name);
    }
}
