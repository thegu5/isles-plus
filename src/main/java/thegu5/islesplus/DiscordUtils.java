package thegu5.islesplus;

import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;

import java.time.OffsetDateTime;

public class DiscordUtils {

    public static OffsetDateTime lastTimestamp;

    public static void updateRPC(String firstline, String secondline)
    {
        if(IslesPlus.ipcClient.getStatus() != PipeStatus.CONNECTED)
        {
            System.out.println("not connected sadge");
            return;
        }

        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setDetails(firstline)
                .setState(secondline)
                .setLargeImage("logo", "play.skyblockisles.com - Isles Plus");
        builder.setStartTimestamp(lastTimestamp);

        try
        {
            IslesPlus.ipcClient.sendRichPresence(builder.build());
            System.out.println("it should have worked by now.. :O");
        } catch (IllegalStateException e)
        {
            e.printStackTrace();
        }
    }

}