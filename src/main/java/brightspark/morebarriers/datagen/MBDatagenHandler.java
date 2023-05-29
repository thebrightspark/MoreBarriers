package brightspark.morebarriers.datagen;

import brightspark.morebarriers.MoreBarriers;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = MoreBarriers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MBDatagenHandler {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		if (event.includeClient()) {
			DataGenerator generator = event.getGenerator();
			ExistingFileHelper fileHelper = event.getExistingFileHelper();

			generator.addProvider(new MBLangProvider(generator));
			generator.addProvider(new MBBlockModelProvider(generator, fileHelper));
			generator.addProvider(new MBItemModelProvider(generator, fileHelper));
			generator.addProvider(new MBBlockStateProvider(generator, fileHelper));
		}
	}
}
