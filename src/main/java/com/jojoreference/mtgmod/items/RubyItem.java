package com.jojoreference.mtgmod.items;

import com.jojoreference.mtgmod.MTGMod;
import net.minecraft.item.Item;

public class RubyItem extends Item {
    public RubyItem() {
        super(new Item.Properties().group(MTGMod.setup.itemGroup));
        setRegistryName("rubyitem");
    }
}
