package com.jojoreference.mtgmod.setup;

import com.jojoreference.mtgmod.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModSetup {
    public ItemGroup itemGroup = new ItemGroup("mtgmod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.RUBYBLOCK);
        }
    };

    public void init() {
    }
}
