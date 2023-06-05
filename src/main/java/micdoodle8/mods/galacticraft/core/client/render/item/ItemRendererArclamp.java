package micdoodle8.mods.galacticraft.core.client.render.item;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.obj.WavefrontObject;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import micdoodle8.mods.galacticraft.core.client.render.tile.TileEntityArclampRenderer;

public class ItemRendererArclamp implements IItemRenderer {

    private void renderArclamp(ItemRenderType type) {
        GL11.glPushMatrix();

        switch (type) {
            case INVENTORY:
                GL11.glScalef(0.9F, 0.9F, 0.9F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(0.8F, 0.8F, 0.5F);
                GL11.glRotatef(150F, 0, 1F, 0);
                GL11.glScalef(0.7F, 0.7F, 0.7F);
                break;
            case EQUIPPED:
                GL11.glTranslatef(0.6F, 0.8F, 0.6F);
                GL11.glRotatef(150F, 0, 1F, 0);
                GL11.glScalef(0.9F, 0.9F, 0.9F);
                break;
            default:
                break;
        }

        GL11.glScalef(0.07F, 0.07F, 0.07F);
        GL11.glRotatef(90, 0, 0, -1);
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TileEntityArclampRenderer.lampTexture);
        TileEntityArclampRenderer.lampMetal.renderAll();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(TileEntityArclampRenderer.lightTexture);
        final Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA(255, 255, 255, 255);
        GL11.glDisable(GL11.GL_LIGHTING);
        ((WavefrontObject) TileEntityArclampRenderer.lampLight).tessellateAll(tessellator);
        tessellator.draw();

        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    /**
     * IItemRenderer implementation *
     */
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return switch (type) {
            case ENTITY -> true;
            case EQUIPPED -> true;
            case EQUIPPED_FIRST_PERSON -> true;
            case INVENTORY -> true;
            default -> false;
        };
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
            case EQUIPPED:
                this.renderArclamp(type);
                break;
            case EQUIPPED_FIRST_PERSON:
                this.renderArclamp(type);
                break;
            case INVENTORY:
                this.renderArclamp(type);
                break;
            case ENTITY:
                this.renderArclamp(type);
                break;
            default:
        }
    }
}
