package brightspark.morebarriers;

import brightspark.morebarriers.blocks.DeathBarrierBlock;
import brightspark.morebarriers.blocks.FluidBarrierBlock;
import brightspark.morebarriers.blocks.PainBarrierBlock;
import brightspark.morebarriers.blocks.PushBarrierBlock;
import brightspark.morebarriers.tileentities.BarrierTileEntity;
import brightspark.morebarriers.tileentities.NameableBarrierTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.function.Supplier;

/*
・Water Barrier block, is not "Waterlogged" and should not contribute to flowing water
・Lava Barrier block, same as water, but lava
・Pain Barrier, similar to spending time in The Void, I would like a block that you can pass through (and any other entity, like the ineffable glass from Extra Utilities) that hurts like cactus. Death msg: "was killed by The Guardians."
・Death Barrier, when walked into, you die. Death msg: "died trying to pierce the veil."
・Push Barrier, a block that can be passed through but pushes entities in a single direction (preferably towards the player who places it; including up or down)
 */
public class MBBlocks {
	public static final DeferredRegister<Block> BLOCKS =
		DeferredRegister.create(ForgeRegistries.BLOCKS, MoreBarriers.MOD_ID);
	public static final DeferredRegister<Item> ITEMS =
		DeferredRegister.create(ForgeRegistries.ITEMS, MoreBarriers.MOD_ID);
	public static final DeferredRegister<TileEntityType<?>> TILES =
		DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MoreBarriers.MOD_ID);

	private static final ItemGroup GROUP = new ItemGroup(MoreBarriers.MOD_ID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(Blocks.BARRIER);
		}
	};

	public static final RegistryObject<Block> WATER_BARRIER =
		regBlock("barrier_water", () -> new FluidBarrierBlock(() -> Fluids.WATER, blockProps()));
	public static final RegistryObject<Block> LAVA_BARRIER =
		regBlock("barrier_lava", () -> new FluidBarrierBlock(() -> Fluids.LAVA, blockProps().lightLevel(s -> 15)));
	public static final RegistryObject<Block> PAIN_BARRIER =
		regBlock("barrier_pain", () -> new PainBarrierBlock(blockProps().noCollission()));
	public static final RegistryObject<Block> DEATH_BARRIER =
		regBlock("barrier_death", () -> new DeathBarrierBlock(blockProps()));
	public static final RegistryObject<Block> PUSH_BARRIER =
		regBlock("barrier_push", () -> new PushBarrierBlock(blockProps().noCollission()));

	public static final RegistryObject<TileEntityType<BarrierTileEntity>> TILE_BARRIER =
		regTE("barrier", BarrierTileEntity::new, WATER_BARRIER, LAVA_BARRIER, PUSH_BARRIER);
	public static final RegistryObject<TileEntityType<NameableBarrierTileEntity>> TILE_NAMEABLE_BARRIER =
		regTE("barrier_nameable", NameableBarrierTileEntity::new, DEATH_BARRIER, PAIN_BARRIER);

	public static void registerDeferredRegistries(IEventBus modBus) {
		BLOCKS.register(modBus);
		ITEMS.register(modBus);
		TILES.register(modBus);
	}

	private static RegistryObject<Block> regBlock(String name, Supplier<Block> blockSupplier) {
		RegistryObject<Block> block = BLOCKS.register(name, blockSupplier);
		ITEMS.register(name, () -> new BlockItem(block.get(), itemProps()));
		return block;
	}

	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> regTE(
		String name,
		Supplier<T> factory,
		Supplier<Block>... blocks
	) {
		//noinspection DataFlowIssue
		return TILES.register(
			name,
			() -> TileEntityType.Builder.of(factory, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new))
				.build(null)
		);
	}

	private static AbstractBlock.Properties blockProps() {
		return AbstractBlock.Properties.of(Material.BARRIER).strength(-1F, 3600000F).noDrops().noOcclusion()
			.isValidSpawn((state, world, pos, entityType) -> false);
	}

	private static Item.Properties itemProps() {
		return new Item.Properties().tab(GROUP);
	}

	public static boolean isBarrier(Item item) {
		return item == Items.BARRIER || isRegistryObject(item, WATER_BARRIER) ||
			isRegistryObject(item, LAVA_BARRIER) || isRegistryObject(item, PAIN_BARRIER) ||
			isRegistryObject(item, DEATH_BARRIER) || isRegistryObject(item, PUSH_BARRIER);
	}

	private static boolean isRegistryObject(Item item, RegistryObject<Block> registryObject) {
		return registryObject.map(block -> block.asItem() == item).orElse(false);
	}
}
