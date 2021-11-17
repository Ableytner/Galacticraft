package micdoodle8.mods.galacticraft.mixins.minecraft;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "orientCamera", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onOrientCamera(float partialTicks, CallbackInfo callbackInfo) {
        ClientProxyCore.orientCamera(partialTicks);
    }

    // TODO targeted: updateLightmapMethod
    // check runtime code

    // TODO: Only inject if Optifine is NOT installed!
    @Redirect(method = "updateFogColor",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getFogColor(F)Lnet/minecraft/util/Vec3;"))
    private Vec3 onUpdateFogColor(WorldClient worldClient, float v) {
        return WorldUtil.getFogColorHook(worldClient);
    }


    @Redirect(method = "updateFogColor",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getSkyColor(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/util/Vec3;"))
    private Vec3 onUpdateSkyColor(WorldClient worldClient, Entity entity, float v) {
        return WorldUtil.getSkyColorHook(worldClient);
    }
}
