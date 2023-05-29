package brightspark.morebarriers;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MoreBarriers.MOD_ID)
public class MoreBarriers {
	public static final String MOD_ID = "morebarriers";

	public MoreBarriers() {
		MBBlocks.registerDeferredRegistries(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
