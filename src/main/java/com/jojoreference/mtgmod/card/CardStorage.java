package com.jojoreference.mtgmod.card;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CardStorage implements Capability.IStorage<ICard> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<ICard> capability, ICard instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("name", instance.getName());
        tag.putString("manaCost", instance.getManaCost());
        tag.putString("type", instance.getTypes());
        tag.putString("text", instance.getText());
        tag.putString("power", instance.getPower());
        tag.putString("toughness", instance.getToughness());
        tag.putString("rarity", instance.getRarity());
        tag.putFloat("convertedManaCost", instance.getConvertedManaCost());
        return tag;
    }

    @Override
    public void readNBT(Capability<ICard> capability, ICard instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setName(tag.getString("name"));
        instance.setManaCost(tag.getString("manaCost"));
        instance.setTypes(tag.getString("type"));
        instance.setText(tag.getString("text"));
        instance.setPower(tag.getString("power"));
        instance.setToughness(tag.getString("toughness"));
        instance.setRarity(tag.getString("rarity"));
        instance.setConvertedManaCost(tag.getFloat("convertedManaCost"));
    }
}
