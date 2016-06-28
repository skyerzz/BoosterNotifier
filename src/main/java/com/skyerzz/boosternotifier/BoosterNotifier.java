package com.skyerzz.boosternotifier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Mod(modid = BoosterNotifier.MODID, version = BoosterNotifier.VERSION)
public class BoosterNotifier
{
    public static final String MODID = "BoosterNotifier";
    public static final String VERSION = "1.0";
    private RenderTimes renderTimes;
    private Settings settings;
    public static boolean privateTip = false;


    //TODO: add settings for displaying <everything|nothing|actives|cooldowns> using gui
    //TODO: make a configuration file that saves the settings above and the boosterList on shutdown
    //TODO: doesnt instantly display; find out why


    @EventHandler
    public void preinit(FMLPreInitializationEvent e) {
        renderTimes = new RenderTimes();
        settings = new Settings(this);
        settings.load(e.getSuggestedConfigurationFile());

    }


    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new commandBNSettings());
    }

    @SubscribeEvent
    public void RenderGameOverlayEvent(RenderGameOverlayEvent event)
    {
        //render everything onto the screen
        if(event.type == RenderGameOverlayEvent.ElementType.TEXT)
        {
            renderTimes.draw();
        }
    }

    @SubscribeEvent
    public void joinWorld(FMLNetworkEvent.ClientConnectedToServerEvent e)
    {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    checkUpdates();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void checkUpdates() throws IOException {
        Minecraft.getMinecraft().getCurrentServerData();
        try {
            String ver = "";
            URL u = new URL("https://skyerzz.com/mods/boosternotifier/version.txt");
            HttpsURLConnection con = (HttpsURLConnection)u.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input, site = "https://www.skyerzz.com/mods/boosternotifier";
            if((input = br.readLine()) != null) {
                ver = input;
            }
            br.close();
            if(compareVersions(ver)) {
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[BN] A new update is available here: " + site));
            }
        } catch (MalformedURLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static boolean compareVersions(String webversion)
    {
        webversion = webversion.toLowerCase().trim();
        String ver = BoosterNotifier.VERSION.toLowerCase().trim();
        if(ver.contains("b") && webversion.contains("r"))
        {
            return true;
        }
        ver = ver.replace("b", "").replace("a", "").replace("r", "").trim();
        webversion = webversion.replace("b", "").replace("a", "").replace("r", "").trim();
        String[] version = ver.split("\\.");
        String[] newversion = webversion.split("\\.");
        for(int i = 0; i < version.length; i++)
        {
            try
            {
                if(newversion.length < i)
                {
                    return false;
                }
                if(Integer.parseInt(version[i]) < Integer.parseInt(newversion[i]))
                {
                    return true;
                }
            }
            catch(NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    @SubscribeEvent
    public void checkChat(ClientChatReceivedEvent e)
    {
        if(e.type!=0)
        {
            return;
        }
        String chat = e.message.getUnformattedText().replaceAll("\u00A7.", "");
        if(!chat.contains("You tipped ") || chat.contains(":"))
        {
            return;
        }
        String gamemode = "";
        String[] split = chat.split(" ");
        for(int i = 4; i < split.length; i++)
        {
            gamemode+=(" " + split[i]);
        }
        addBooster(gamemode);

    }

    public void addBooster(String gametype, long endtime, boolean save)
    {
        switch(gametype.toLowerCase().trim())
        {
            case "arcade":
                renderTimes.addTime(Gametype.ARCADE, endtime, save);
                break;
            case "arena brawl":
                renderTimes.addTime(Gametype.ARENA_BRAWL, endtime, save);
                break;
            case "blitz survival games":
                renderTimes.addTime(Gametype.BLITZ, endtime, save);
                break;
            case "cops and crims":
                renderTimes.addTime(Gametype.COPS_AND_CRIMS, endtime, save);
                break;
            case "crazy walls":
                renderTimes.addTime(Gametype.CRAZY_WALLS, endtime, save);
                break;
            case "mega walls":
                renderTimes.addTime(Gametype.MEGA_WALLS, endtime, save);
                break;
            case "paintball":
                renderTimes.addTime(Gametype.PAINTBALL, endtime, save);
                break;
            case "quakecraft":
                renderTimes.addTime(Gametype.QUAKECRAFT, endtime, save);
                break;
            case "skywars":
                renderTimes.addTime(Gametype.SKYWARS, endtime, save);
                break;
            case "smash heroes":
                renderTimes.addTime(Gametype.SMASH_HEROES, endtime, save);
                break;
            case "speed uhc":
                renderTimes.addTime(Gametype.SPEED_UHC, endtime, save);
                break;
            case "turbo kart racers":
                renderTimes.addTime(Gametype.TURBO_KART_RACERS, endtime, save);
                break;
            case "tnt games":
                renderTimes.addTime(Gametype.ARCADE.TNT_GAMES, endtime, save);
                break;
            case "uhc champions":
                renderTimes.addTime(Gametype.UHC, endtime, save);
                break;
            case "vampirez":
                renderTimes.addTime(Gametype.VAMPIREZ, endtime, save);
                break;
            case "walls":
                renderTimes.addTime(Gametype.WALLS, endtime, save);
                break;
            case "warlords":
                renderTimes.addTime(Gametype.WARLORDS, endtime, save);
                break;
            default:
                System.out.println("ERROR IN GAMETYPE: " + gametype);
                renderTimes.addTime(Gametype.ERROR, endtime, save);

        }
    }

    public void addBooster(String gametype)
    {
        addBooster(gametype, System.currentTimeMillis()+3600000, true);
    }
}
