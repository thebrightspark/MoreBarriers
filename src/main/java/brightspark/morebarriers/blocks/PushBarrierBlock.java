package brightspark.morebarriers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PushBarrierBlock extends MBBarrierBlock {
	private static final DirectionProperty FACING = BlockStateProperties.FACING;
	private final double force;

	public PushBarrierBlock(double force, Properties properties) {
		super(properties);
		this.force = force;
		registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP));
	}

	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		if (canAffectEntity(entity)) {
			Direction direction = state.getValue(FACING);
			entity.push(direction.getStepX() * force, direction.getStepY() * force, direction.getStepZ() * force);
		}
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		return player != null
			? defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite())
			: super.getStateForPlacement(context);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
