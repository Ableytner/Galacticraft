package micdoodle8.mods.galacticraft.core.client.sounds;

import micdoodle8.mods.galacticraft.api.world.IGalacticraftWorldProvider;
import micdoodle8.mods.galacticraft.core.client.ClientProxyCore;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * Copyright 2012-2013, micdoodle8
 * 
 * All rights reserved.
 * 
 */
public class GCCoreSounds
{
    @ForgeSubscribe
    public void onMusicSound(PlayBackgroundMusicEvent event)
    {
        final Minecraft mc = FMLClientHandler.instance().getClient();

        if (mc.thePlayer != null && mc.thePlayer.worldObj != null && mc.thePlayer.worldObj.provider instanceof IGalacticraftWorldProvider)
        {
            final int randInt = FMLClientHandler.instance().getClient().thePlayer.worldObj.rand.nextInt(ClientProxyCore.newMusic.size() + 2);

            if (randInt < ClientProxyCore.newMusic.size())
            {
                event.result = ClientProxyCore.newMusic.get(randInt);
            }
        }
    }
}
