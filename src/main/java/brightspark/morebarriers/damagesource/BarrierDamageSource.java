package brightspark.morebarriers.damagesource;

import brightspark.morebarriers.MoreBarriers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BarrierDamageSource extends DamageSource {
	private final ITextComponent barrierName;

	public BarrierDamageSource(String messageId) {
		super(MoreBarriers.MOD_ID + "." + messageId);
		this.barrierName = null;
		bypassArmor();
		bypassInvul();
	}

	public BarrierDamageSource(ITextComponent barrierName) {
		super(MoreBarriers.MOD_ID + ".named");
		this.barrierName = barrierName;
		bypassArmor();
		bypassInvul();
	}

	@Override
	public ITextComponent getLocalizedDeathMessage(LivingEntity pLivingEntity) {
		String langId = "death.attack." + msgId;
		ITextComponent entityName = pLivingEntity.getDisplayName();
		return barrierName != null
			? new TranslationTextComponent(langId, entityName, barrierName)
			: new TranslationTextComponent(langId, entityName);
	}
}
