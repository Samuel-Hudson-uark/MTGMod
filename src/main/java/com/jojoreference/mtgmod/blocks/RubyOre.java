package com.jojoreference.mtgmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class RubyOre extends Block {

    public RubyOre() {
        super(Properties.create(Material.ROCK)
                .sound(SoundType.STONE)
                .hardnessAndResistance(5.0f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(3));
        setRegistryName("rubyore");
    }
}
