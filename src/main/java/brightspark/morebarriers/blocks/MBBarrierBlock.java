package brightspark.morebarriers.blocks;

import brightspark.morebarriers.MBBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class MBBarrierBlock extends Block {
	public MBBarrierBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockRenderType getRenderShape(BlockState pState) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
		return Collections.emptyList();
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return MBBlocks.TILE_BARRIER.get().create();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		return 1.0F;
	}

	protected boolean canAffectEntity(Entity entity) {
		return !(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative();
	}
}
