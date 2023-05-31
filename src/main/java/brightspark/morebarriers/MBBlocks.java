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
		regBlock("barrier_push", () -> new PushBarrierBlock(0.12D, blockProps().noCollission()));
	public static final RegistryObject<Block> LAUNCH_BARRIER =
		regBlock("barrier_launch", () -> new PushBarrierBlock(1D, blockProps().noCollission()));

	public static final RegistryObject<TileEntityType<BarrierTileEntity>> TILE_BARRIER =
		regTE("barrier", BarrierTileEntity::new, WATER_BARRIER, LAVA_BARRIER, PUSH_BARRIER, LAUNCH_BARRIER);
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
		return item == Items.BARRIER || BLOCKS.getEntries().stream().anyMatch(block -> isMBBarrier(item, block));
	}

	private static boolean isMBBarrier(Item item, RegistryObject<Block> registryObject) {
		return registryObject.map(block -> block.asItem() == item).orElse(false);
	}
}
