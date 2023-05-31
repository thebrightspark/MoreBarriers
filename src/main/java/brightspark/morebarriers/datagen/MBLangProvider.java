package brightspark.morebarriers.datagen;

import brightspark.morebarriers.MBBlocks;
import brightspark.morebarriers.MoreBarriers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class MBLangProvider extends LanguageProvider {
	public MBLangProvider(DataGenerator gen) {
		super(gen, MoreBarriers.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + MoreBarriers.MOD_ID, "More Barriers");

		addDamageSource("pain", "%1$s was killed by The Guardians");
		addDamageSource("death", "%1$s died trying to pierce the veil");
		addDamageSource("named", "%1$s was killed by %2$s");

		addBlock(MBBlocks.WATER_BARRIER, "Water Barrier");
		addBlock(MBBlocks.LAVA_BARRIER, "Lava Barrier");
		addBlock(MBBlocks.PAIN_BARRIER, "Pain Barrier");
		addBlock(MBBlocks.DEATH_BARRIER, "Death Barrier");
		addBlock(MBBlocks.PUSH_BARRIER, "Push Barrier");
		addBlock(MBBlocks.LAUNCH_BARRIER, "Launch Barrier");
	}

	private void addDamageSource(String key, String name) {
		add("death.attack." + MoreBarriers.MOD_ID + "." + key, name);
	}
}
