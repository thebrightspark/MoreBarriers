package brightspark.morebarriers.datagen;

import brightspark.morebarriers.MBBlocks;
import brightspark.morebarriers.MoreBarriers;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class MBItemModelProvider extends ItemModelProvider {
	private final ResourceLocation PARENT = mcLoc("item/generated");

	public MBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, MoreBarriers.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ResourceLocation barrier = mcLoc("item/barrier");
		simpleTextures(MBBlocks.WATER_BARRIER, mcLoc("block/water_still"), barrier);
		simpleTextures(MBBlocks.LAVA_BARRIER, mcLoc("block/lava_still"), barrier);
		itemModel(MBBlocks.PAIN_BARRIER);
		itemModel(MBBlocks.DEATH_BARRIER);
		itemModel(MBBlocks.PUSH_BARRIER);
		itemModel(MBBlocks.LAUNCH_BARRIER);
	}

	@SuppressWarnings("ConstantConditions")
	private String getPath(Supplier<Block> block) {
		return block.get().getRegistryName().getPath();
	}

	private void itemModel(Supplier<Block> block) {
		String path = getPath(block);
		withExistingParent(path, modLoc("block/" + path));
	}

	private void simpleTextures(Supplier<Block> block, ResourceLocation... textures) {
		ItemModelBuilder builder = withExistingParent(getPath(block), PARENT);
		for (int i = 0; i < textures.length; i++) {
			builder.texture("layer" + i, textures[i]);
		}
	}
}
