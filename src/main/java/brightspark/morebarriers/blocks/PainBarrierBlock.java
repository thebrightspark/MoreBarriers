package brightspark.morebarriers.blocks;

import brightspark.morebarriers.damagesource.BarrierDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PainBarrierBlock extends NameableBarrierBlock {
	public PainBarrierBlock(Properties properties) {
		super(properties, new BarrierDamageSource("pain"));
	}

	@Override
	public void entityInside(BlockState pState, World pLevel, BlockPos pPos, Entity pEntity) {
		if (canAffectEntity(pEntity))
			pEntity.hurt(createDamageSource(pLevel, pPos), 1F);
	}
}
