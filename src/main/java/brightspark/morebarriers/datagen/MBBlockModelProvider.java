package brightspark.morebarriers.datagen;

import brightspark.morebarriers.MBBlocks;
import brightspark.morebarriers.MoreBarriers;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class MBBlockModelProvider extends BlockModelProvider {
	public MBBlockModelProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, MoreBarriers.MOD_ID, fileHelper);
	}

	@Override
	protected void registerModels() {
		cubeAll(MBBlocks.PAIN_BARRIER);
		cubeAll(MBBlocks.DEATH_BARRIER);
		cubeFront(MBBlocks.PUSH_BARRIER);

		ResourceLocation textureRL = mcLoc(ITEM_FOLDER + "/" + Items.BARRIER.getRegistryName().getPath());
		cubeAll(MBBlocks.WATER_BARRIER, textureRL);
		cubeAll(MBBlocks.LAVA_BARRIER, textureRL);
	}

	@SuppressWarnings("ConstantConditions")
	private String getPath(Supplier<Block> block) {
		return block.get().getRegistryName().getPath();
	}

	private void cubeAll(Supplier<Block> block) {
		String path = getPath(block);
		cubeAll(path, modLoc(BLOCK_FOLDER + "/" + path));
	}

	private void cubeAll(Supplier<Block> block, ResourceLocation texture) {
		cubeAll(getPath(block), texture);
	}

	private void cubeFront(Supplier<Block> block) {
		String path = getPath(block);
		ResourceLocation side = modLoc(BLOCK_FOLDER + "/" + path);
		orientable(path, side, modLoc(BLOCK_FOLDER + "/" + path + "_front"), side);
	}
}
