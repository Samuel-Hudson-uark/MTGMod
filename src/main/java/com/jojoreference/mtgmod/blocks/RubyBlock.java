package com.jojoreference.mtgmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RubyBlock extends Block {

    public RubyBlock() {
        super(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5.0f));
        setRegistryName("rubyblock");
    }
}
