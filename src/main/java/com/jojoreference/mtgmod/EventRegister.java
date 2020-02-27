package com.jojoreference.mtgmod;

import com.jojoreference.mtgmod.card.Card;
import com.jojoreference.mtgmod.items.MagicCardItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventRegister {
    @SubscribeEvent
    public static void AttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() instanceof MagicCardItem) {
            event.addCapability(new ResourceLocation(MTGMod.MODID, "card_data"), new Card());
        }
    }
}
