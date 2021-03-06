package com.replaymod.recording.gui;

import com.replaymod.core.SettingsRegistry;
import com.replaymod.core.versions.MCVer;
import com.replaymod.recording.Setting;
import de.johni0702.minecraft.gui.GuiRenderer;
import de.johni0702.minecraft.gui.MinecraftGuiRenderer;
import de.johni0702.minecraft.gui.utils.EventRegistrations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.resource.language.I18n;

//#if FABRIC>=1
import de.johni0702.minecraft.gui.versions.callbacks.PostRenderHudCallback;
//#else
//$$ import net.minecraftforge.client.event.RenderGameOverlayEvent;
//$$ import net.minecraftforge.common.MinecraftForge;
//$$ import net.minecraftforge.eventbus.api.SubscribeEvent;
//#endif

import static com.replaymod.core.ReplayMod.TEXTURE;
import static com.replaymod.core.ReplayMod.TEXTURE_SIZE;
import static com.replaymod.core.versions.MCVer.*;
import static com.mojang.blaze3d.platform.GlStateManager.*;

/**
 * Renders overlay during recording.
 */
public class GuiRecordingOverlay extends EventRegistrations {
    private final MinecraftClient mc;
    private final SettingsRegistry settingsRegistry;
    private final GuiRecordingControls guiControls;

    public GuiRecordingOverlay(MinecraftClient mc, SettingsRegistry settingsRegistry, GuiRecordingControls guiControls) {
        this.mc = mc;
        this.settingsRegistry = settingsRegistry;
        this.guiControls = guiControls;
    }

    /**
     * Render the recording icon and text in the top left corner of the screen.
     */
    //#if FABRIC>=1
    { on(PostRenderHudCallback.EVENT, partialTicks -> renderRecordingIndicator()); }
    private void renderRecordingIndicator() {
    //#else
    //$$ @SubscribeEvent
    //$$ public void renderRecordingIndicator(RenderGameOverlayEvent.Post event) {
    //$$     if (getType(event) != RenderGameOverlayEvent.ElementType.ALL) return;
    //#endif
        if (guiControls.isStopped()) return;
        if (settingsRegistry.get(Setting.INDICATOR)) {
            TextRenderer fontRenderer = mc.textRenderer;
            String text = guiControls.isPaused() ? I18n.translate("replaymod.gui.paused") : I18n.translate("replaymod.gui.recording");
            fontRenderer.draw(text.toUpperCase(), 30, 18 - (fontRenderer.fontHeight / 2), 0xffffffff);
            bindTexture(TEXTURE);
            enableAlphaTest();
            GuiRenderer renderer = new MinecraftGuiRenderer(MCVer.newScaledResolution(mc));
            renderer.drawTexturedRect(10, 10, 58, 20, 16, 16, 16, 16, TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }
}
