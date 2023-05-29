package brightspark.morebarriers.blocks;

import brightspark.morebarriers.MBBlocks;
import brightspark.morebarriers.damagesource.BarrierDamageSource;
import brightspark.morebarriers.tileentities.NameableBarrierTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class NameableBarrierBlock extends MBBarrierBlock {
	private final DamageSource defaultDamageSource;

	public NameableBarrierBlock(Properties properties, DamageSource defaultDamageSource) {
		super(properties);
		this.defaultDamageSource = defaultDamageSource;
	}

	protected DamageSource createDamageSource(World world, BlockPos pos) {
		TileEntity te = world.getBlockEntity(pos);
		if (te instanceof NameableBarrierTileEntity && ((NameableBarrierTileEntity) te).hasCustomName()) {
			return new BarrierDamageSource(((NameableBarrierTileEntity) te).getCustomName());
		} else {
			return defaultDamageSource;
		}
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return MBBlocks.TILE_NAMEABLE_BARRIER.get().create();
	}

	@Override
	public void setPlacedBy(
		World pLevel,
		BlockPos pPos,
		BlockState pState,
		@Nullable LivingEntity pPlacer,
		ItemStack pStack
	) {
		if (pStack.hasCustomHoverName()) {
			TileEntity te = pLevel.getBlockEntity(pPos);
			if (te instanceof NameableBarrierTileEntity) {
				((NameableBarrierTileEntity) te).setCustomName(pStack.getHoverName());
			}
		}
	}

	@Override
	public ItemStack getCloneItemStack(IBlockReader pLevel, BlockPos pPos, BlockState pState) {
		ItemStack stack = super.getCloneItemStack(pLevel, pPos, pState);
		TileEntity te = pLevel.getBlockEntity(pPos);
		if (te instanceof NameableBarrierTileEntity) {
			NameableBarrierTileEntity barrierTe = (NameableBarrierTileEntity) te;
			if (barrierTe.hasCustomName()) {
				stack.setHoverName(barrierTe.getCustomName());
			}
		}
		return stack;
	}
}
