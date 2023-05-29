package brightspark.morebarriers.blocks;

import brightspark.morebarriers.damagesource.BarrierDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class DeathBarrierBlock extends NameableBarrierBlock {
	private static final VoxelShape COLLISION_SHAPE = Block.box(1D, 1D, 1D, 15D, 15D, 15D);
	private static final VoxelShape OUTLINE_SHAPE = Block.box(0D, 0D, 0D, 16D, 16D, 16D);

	public DeathBarrierBlock(Properties properties) {
		super(properties, new BarrierDamageSource("death"));
	}

	@Override
	public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
		return OUTLINE_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(
		BlockState pState,
		IBlockReader pLevel,
		BlockPos pPos,
		ISelectionContext pContext
	) {
		return COLLISION_SHAPE;
	}

	@Override
	public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
		if (canAffectEntity(pEntity))
			pEntity.hurt(createDamageSource(pLevel, pPos), Float.MAX_VALUE);
	}
}
