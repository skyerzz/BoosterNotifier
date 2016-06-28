package com.skyerzz.boosternotifier;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 28-6-2016.
 */
public class commandBNSettings implements ICommand {

    public List<String> alias, tabcomplete;

    public commandBNSettings()
    {
        tabcomplete = new ArrayList<String>();
        tabcomplete.add("all");
        tabcomplete.add("active");
        tabcomplete.add("cooldown");
        tabcomplete.add("none");

        alias = new ArrayList<String>();
        alias.add("bns");
        alias.add("boostersettings");
        alias.add("boosternofier");
    }

    @Override
    public String getCommandName() {
        return "bnsettings";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "bns <all|active|cooldown|none>";
    }

    @Override
    public List<String> getCommandAliases() {
        return alias;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length!=1)
        {
            throw new CommandException(getCommandUsage(sender));
        }
        switch(args[0].toLowerCase().trim())
        {
            case "none":
                sendPlayerMessage("Set booster messages to none");
                Settings.setShowActive(false);
                Settings.setShowInactive(false);
                break;
            case "active":
                sendPlayerMessage("Set booster messages to available tips");
                Settings.setShowActive(false);
                Settings.setShowInactive(true);
                break;
            case "cooldown":
                sendPlayerMessage("Set booster messages to active cooldowns");
                Settings.setShowActive(true);
                Settings.setShowInactive(false);
                break;
            case "all":
                sendPlayerMessage("Set booster messages to all");
                Settings.setShowActive(true);
                Settings.setShowInactive(true);
                break;
            default:
                throw new CommandException(getCommandUsage(sender));
        }
    }

    public void sendPlayerMessage(String message)
    {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + message));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return tabcomplete;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
