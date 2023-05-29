package brightspark.morebarriers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

public class FluidBarrierBlock extends MBBarrierBlock {
	private final Lazy<Fluid> fluid;

	public FluidBarrierBlock(Supplier<Fluid> fluid, Properties properties) {
		super(properties);
		this.fluid = Lazy.of(fluid);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState pState, IBlockReader pReader, BlockPos pPos) {
		return false;
	}

	@Override
	public FluidState getFluidState(BlockState pState) {
		return fluid.get().defaultFluidState();
	}

	@Override
	public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
		return pAdjacentBlockState.getFluidState().getType().isSame(fluid.get());
	}

	@Override
	public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);

		Block newBlock = pNewState.getBlock();
		if (newBlock instanceof FlowingFluidBlock && ((FlowingFluidBlock) newBlock).getFluid() == fluid.get()) {
			pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), pLevel.isClientSide() ? 11 : 3);
		}
	}
}
