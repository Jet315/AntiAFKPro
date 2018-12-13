package me.jet315.antiafkpro.actions;

import com.sun.deploy.util.ReflectionUtil;
import me.jet315.antiafkpro.reflection.ReflectionUtils;
import me.jet315.antiafkpro.reflection.ServerVersion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sun.reflect.Reflection;

import java.lang.reflect.Constructor;

public class TitleAction extends Action {

    public TitleAction(ActionType type, String actionArgument) {
        super(type, actionArgument);
    }

    @Override
    public boolean execute(Player p) {
        String titleSplit[] = getActionArgument().split(";");
        String title = getActionArgument();
        String subtitle = " ";
        if(titleSplit.length > 1){
            title = titleSplit[0];
            subtitle = titleSplit[1];
        }
        sendTitle(p,15,50,20,title,subtitle);
        return true;
    }


    /**
     * Send a title to player
     *
     * @param player      Player to send the title to
     * @param fadeInTime  The time the title takes to fade in
     * @param showTime    The time the title is displayed
     * @param fadeOutTime The time the title takes to fade out
     * @param title       The title
     * @param subtitle    The subtitle
     */
    private void sendTitle(Player player, int fadeInTime, int showTime, int fadeOutTime, String title, String subtitle) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        title = title.replaceAll("%PLAYER%", player.getDisplayName());
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        subtitle = subtitle.replaceAll("%PLAYER%", player.getDisplayName());

        if (ReflectionUtils.serverVersion == ServerVersion.VERSION1_13) {
            player.sendTitle(title,subtitle,fadeInTime,showTime,fadeOutTime);
            return;
        }

        if (ReflectionUtils.serverVersion == ServerVersion.VERSION1_12 || ReflectionUtils.serverVersion == ServerVersion.VERSION1_11) {
            player.sendTitle(title,subtitle);
            return;
        }

        try {
            Object e;
            Object chatTitle;
            Object chatSubtitle;
            Constructor subtitleConstructor;
            Object titlePacket;
            Object subtitlePacket;

            if (title != null) {
                // Times packets
                e = ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
                chatTitle = ReflectionUtils.getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                subtitleConstructor = ReflectionUtils.getClass("PacketPlayOutTitle").getConstructor(new Class[]{ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0], ReflectionUtils.getClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle, fadeInTime, showTime, fadeOutTime});
                sendPacket(player, titlePacket);

                e = ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object) null);
                chatTitle = ReflectionUtils.getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                subtitleConstructor = ReflectionUtils.getClass("PacketPlayOutTitle").getConstructor(new Class[]{ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0], ReflectionUtils.getClass("IChatBaseComponent")});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle});
                sendPacket(player, titlePacket);
            }

            // Times packets
            e = ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
            chatSubtitle = ReflectionUtils.getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
            subtitleConstructor = ReflectionUtils.getClass("PacketPlayOutTitle").getConstructor(new Class[]{ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0], ReflectionUtils.getClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
            subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeInTime, showTime, fadeOutTime});
            sendPacket(player, subtitlePacket);

            e = ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object) null);
            chatSubtitle = ReflectionUtils.getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + subtitle + "\"}"});
            subtitleConstructor = ReflectionUtils.getClass("PacketPlayOutTitle").getConstructor(new Class[]{ReflectionUtils.getClass("PacketPlayOutTitle").getDeclaredClasses()[0], ReflectionUtils.getClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
            subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeInTime, showTime, fadeOutTime});
            sendPacket(player, subtitlePacket);

        } catch (Exception var11) {
            var11.printStackTrace();
        }
    }

    private void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", ReflectionUtils.getClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception ex) {
            //Do something
        }
    }
}
