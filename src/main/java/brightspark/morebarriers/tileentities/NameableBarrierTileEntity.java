package brightspark.morebarriers.tileentities;

import brightspark.morebarriers.MBBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;

public class NameableBarrierTileEntity extends BarrierTileEntity implements INameable {
	private Lazy<ITextComponent> defaultName =
		Lazy.of(() -> new TranslationTextComponent(getBlockState().getBlock().getDescriptionId()));
	private ITextComponent customName = null;

	public NameableBarrierTileEntity() {
		super(MBBlocks.TILE_NAMEABLE_BARRIER.get());
	}

	@Override
	public ITextComponent getName() {
		return customName != null ? customName : defaultName.get();
	}

	@Nullable
	@Override
	public ITextComponent getCustomName() {
		return customName;
	}

	public void setCustomName(ITextComponent name) {
		customName = name;
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.contains("CustomName", Constants.NBT.TAG_STRING)) {
			customName = ITextComponent.Serializer.fromJson(nbt.getString("CustomName"));
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT nbt) {
		if (customName != null) {
			nbt.putString("CustomName", ITextComponent.Serializer.toJson(customName));
		}
		return super.save(nbt);
	}
}
