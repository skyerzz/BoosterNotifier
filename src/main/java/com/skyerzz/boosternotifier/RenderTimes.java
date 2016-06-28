package com.skyerzz.boosternotifier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

import java.util.HashMap;

/**
 * Created by sky on 27-6-2016.
 */
public class RenderTimes {

    public static HashMap<String, Long> boosterActives = new HashMap<String, Long>();

    public RenderTimes(){};

    public void draw()
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.inGameHasFocus || (mc.currentScreen != null && (mc.currentScreen instanceof GuiChat))) {
            int offsety = 10;
            for(Gametype g: Gametype.values())
            {
                if(renderTime(g.displayName, offsety, mc))
                {
                    offsety += 10;
                }
            }
        }

    }

    public boolean renderTime(String booster, int offsety, Minecraft mc)
    {
        if(!boosterActives.containsKey(booster) && Settings.showInactive)
        {
            if(!booster.equalsIgnoreCase("error")) {
                mc.fontRendererObj.drawStringWithShadow(booster, 10, offsety, 0x2BCC3E);
                return true;
            }
            return false;
        }
        else if(boosterActives.containsKey(booster) && Settings.showActive) {
            long seconds = (boosterActives.get(booster) - System.currentTimeMillis()) / 1000;
            if (seconds < 1) {
                boosterActives.remove(booster);
                Settings.save();
                return false;
            }
            String time = ":  ";
            if(seconds/60<10)
            {
                time += "0";
            }
            time+=seconds/60 +":";
            if(seconds%60 < 10)
            {
                time+=0;
            }
            time+=seconds%60;

            mc.fontRendererObj.drawStringWithShadow(booster, 10, offsety, 0xE65757);
            mc.fontRendererObj.drawStringWithShadow(time, 65, offsety, 0xE8DB4F);
            return true;
        }
        return false;
    }

    public void addTime(Gametype gamemode, long endTime, boolean save) {
        boosterActives.put(gamemode.displayName, endTime);
        if(save){
            Settings.save();
        }
    }

}
