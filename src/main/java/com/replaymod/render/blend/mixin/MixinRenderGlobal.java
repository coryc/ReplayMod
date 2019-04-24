//#if MC>=10800
package com.replaymod.render.blend.mixin;

import com.replaymod.render.blend.BlendState;
import com.replaymod.render.blend.exporters.EntityExporter;
import com.replaymod.render.blend.exporters.TileEntityExporter;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC>=11300
import net.minecraft.client.renderer.WorldRenderer;
//#else
//$$ import net.minecraft.client.renderer.RenderGlobal;
//#endif

//#if MC>=11300
@Mixin(WorldRenderer.class)
//#else
//$$ @Mixin(RenderGlobal.class)
//#endif
public abstract class MixinRenderGlobal {

    // FIXME wither skull ._. mojang pls

    @Inject(method = "renderEntities",
            at = @At(value = "INVOKE",
                    //#if MC>=10904
                    target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderEntityStatic(Lnet/minecraft/entity/Entity;FZ)V"))
                    //#else
                    //$$ target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderEntitySimple(Lnet/minecraft/entity/Entity;F)Z"))
                    //#endif
    public void preEntityRender(Entity view, ICamera camera, float renderPartialTicks, CallbackInfo ci) {
        BlendState blendState = BlendState.getState();
        if (blendState != null) {
            blendState.get(EntityExporter.class).preEntitiesRender();
        }
    }

    @Inject(method = "renderEntities",
            at = @At(value = "INVOKE",
                    //#if MC>=10904
                    target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderEntityStatic(Lnet/minecraft/entity/Entity;FZ)V",
                    //#else
                    //$$ target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderEntitySimple(Lnet/minecraft/entity/Entity;F)Z",
                    //#endif
                    shift = At.Shift.AFTER))
    public void postEntityRender(Entity view, ICamera camera, float renderPartialTicks, CallbackInfo ci) {
        BlendState blendState = BlendState.getState();
        if (blendState != null) {
            blendState.get(EntityExporter.class).postEntitiesRender();
        }
    }

    @Inject(method = "renderEntities", at = @At(
            value = "INVOKE",
            //#if MC>=11300
            target = "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;render(Lnet/minecraft/tileentity/TileEntity;FI)V"
            //#else
            //$$ target = "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;renderTileEntity(Lnet/minecraft/tileentity/TileEntity;FI)V"
            //#endif
    ))
    public void preTileEntityRender(Entity view, ICamera camera, float renderPartialTicks, CallbackInfo ci) {
        BlendState blendState = BlendState.getState();
        if (blendState != null) {
            blendState.get(TileEntityExporter.class).preTileEntitiesRender();
        }
    }

    @Inject(method = "renderEntities", at = @At(
            value = "INVOKE",
            //#if MC>=11300
            target = "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;render(Lnet/minecraft/tileentity/TileEntity;FI)V",
            //#else
            //$$ target = "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;renderTileEntity(Lnet/minecraft/tileentity/TileEntity;FI)V",
            //#endif
            shift = At.Shift.AFTER
    ))
    public void postTileEntityRender(Entity view, ICamera camera, float renderPartialTicks, CallbackInfo ci) {
        BlendState blendState = BlendState.getState();
        if (blendState != null) {
            blendState.get(TileEntityExporter.class).postTileEntitiesRender();
        }
    }
}
//#endif