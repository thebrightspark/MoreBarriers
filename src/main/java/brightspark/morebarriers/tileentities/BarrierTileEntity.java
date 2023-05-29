package brightspark.morebarriers.tileentities;

import brightspark.morebarriers.MBBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BarrierTileEntity extends TileEntity {
	public BarrierTileEntity() {
		super(MBBlocks.TILE_BARRIER.get());
	}

	protected BarrierTileEntity(TileEntityType<?> teType) {
		super(teType);
	}
}
