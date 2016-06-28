package com.skyerzz.boosternotifier;

import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by sky on 28-6-2016.
 */
public class Settings {

    public static boolean showActive;
    public static boolean showInactive;
    public static Configuration config;
    public BoosterNotifier bn;

    public Settings(BoosterNotifier b){
        bn = b;
    }

    public static void setShowActive(boolean active)
    {
        showActive = active;
        save();
    }

    public static void setShowInactive(boolean inactive)
    {
        showInactive = inactive;
        save();
    }

    public void load(File file)
    {
        config = new Configuration(file);
        config.load();
        String[] timers = config.get(config.CATEGORY_GENERAL, "timers", "").getString().split("%");
        for(int i = 0; i < timers.length; i++)
        {
            String[] split = timers[i].split(":");
            System.out.println("splitlength " + split.length);
            try
            {
                long time = Long.parseLong(split[1]);
                if(time < System.currentTimeMillis())
                {
                    continue;
                }
                bn.addBooster(split[0], time, false);
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException e)
            {
                continue;
            }
        }
        setShowActive(config.get(config.CATEGORY_GENERAL, "showactive", true).getBoolean());
        setShowInactive(config.get(config.CATEGORY_GENERAL, "showinactive", true).getBoolean());
    }

    public static void save()
    {
        config.get(config.CATEGORY_GENERAL, "showactive", true).set(showActive);
        config.get(config.CATEGORY_GENERAL, "showinactive", true).set(showInactive);
        String timers = "";
        for(String s: RenderTimes.boosterActives.keySet())
        {
            timers += (s + ":" + RenderTimes.boosterActives.get(s) + "%");
        }
        config.get(config.CATEGORY_GENERAL, "timers", "").set(timers);
        config.save();
    }
}
