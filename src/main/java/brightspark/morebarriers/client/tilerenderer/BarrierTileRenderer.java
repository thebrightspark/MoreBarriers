package brightspark.morebarriers.client.tilerenderer;

import brightspark.morebarriers.MBBlocks;
import brightspark.morebarriers.tileentities.BarrierTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.EmptyModelData;

public class BarrierTileRenderer extends TileEntityRenderer<BarrierTileEntity> {
	public BarrierTileRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(
		BarrierTileEntity tileEntity,
		float partialTicks,
		MatrixStack matrixStack,
		IRenderTypeBuffer buffer,
		int combinedLight,
		int combinedOverlay
	) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;
		if (player != null && player.isHolding(MBBlocks::isBarrier)) {
			World world = tileEntity.getLevel();
			if (world != null) {
				// Basically mimicking what BlockRendererDispatcher#renderModel would be doing
				BlockRendererDispatcher blockRenderer = mc.getBlockRenderer();
				BlockState state = tileEntity.getBlockState();
				IVertexBuilder vertexBuffer = buffer.getBuffer(RenderTypeLookup.getRenderType(state, false));
				IBakedModel model = blockRenderer.getBlockModel(state);
				blockRenderer.getModelRenderer().renderModel(
					matrixStack.last(),
					vertexBuffer,
					state,
					model,
					1,
					1,
					1,
					combinedLight,
					combinedOverlay,
					EmptyModelData.INSTANCE
				);
			}
		}
	}
}
