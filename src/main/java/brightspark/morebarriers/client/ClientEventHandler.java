package brightspark.morebarriers.client;

import brightspark.morebarriers.MBBlocks;
import brightspark.morebarriers.MoreBarriers;
import brightspark.morebarriers.client.tilerenderer.BarrierTileRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MoreBarriers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
	@SubscribeEvent
	public static void clientSetupEvent(FMLClientSetupEvent event) {
		cutoutRenderType(MBBlocks.WATER_BARRIER);
		cutoutRenderType(MBBlocks.LAVA_BARRIER);
		cutoutRenderType(MBBlocks.PAIN_BARRIER);
		cutoutRenderType(MBBlocks.DEATH_BARRIER);
		cutoutRenderType(MBBlocks.PUSH_BARRIER);

		ClientRegistry.bindTileEntityRenderer(MBBlocks.TILE_BARRIER.get(), BarrierTileRenderer::new);
		// FIXME: This one isn't rendering
		ClientRegistry.bindTileEntityRenderer(MBBlocks.TILE_NAMEABLE_BARRIER.get(), BarrierTileRenderer::new);
	}

	private static void cutoutRenderType(RegistryObject<Block> block) {
		block.ifPresent(b -> RenderTypeLookup.setRenderLayer(b, RenderType.cutout()));
	}
}
