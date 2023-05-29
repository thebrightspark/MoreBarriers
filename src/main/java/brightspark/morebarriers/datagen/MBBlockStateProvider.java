package brightspark.morebarriers.datagen;

import brightspark.morebarriers.MBBlocks;
import brightspark.morebarriers.MoreBarriers;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class MBBlockStateProvider extends BlockStateProvider {
	public MBBlockStateProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, MoreBarriers.MOD_ID, fileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(MBBlocks.PAIN_BARRIER);
		simpleBlock(MBBlocks.DEATH_BARRIER);
		simpleBlock(MBBlocks.WATER_BARRIER);
		simpleBlock(MBBlocks.LAVA_BARRIER);
		// Don't generate the Push Barrier blockstate, as have created it manually
	}

	private void simpleBlock(RegistryObject<Block> block) {
		simpleBlock(block.get(), models().getExistingFile(block.getId()));
	}
}
