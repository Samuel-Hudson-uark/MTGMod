package com.jojoreference.mtgmod.card;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Card implements ICard, ICapabilitySerializable<INBT> {

    @CapabilityInject(ICard.class)
    public static Capability<ICard> CARD_CAPABILITY = null;
    private LazyOptional<ICard> holder = LazyOptional.of(CARD_CAPABILITY::getDefaultInstance);

    private String name = "";
    private String manaCost = "";
    private float convertedManaCost = 0;
    private String types = "";
    private String text = "";
    private String power = "";
    private String toughness = "";
    private String rarity = "";

    @Override
    public INBT serializeNBT() {
        return CARD_CAPABILITY.getStorage().writeNBT(CARD_CAPABILITY,
                this.holder.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CARD_CAPABILITY.getStorage().readNBT(CARD_CAPABILITY,
                this.holder.orElseThrow(() -> new IllegalArgumentException("Lazy Optional cannot be empty")), null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CARD_CAPABILITY ? holder.cast() : LazyOptional.empty();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public float getConvertedManaCost() {
        return convertedManaCost;
    }

    public void setConvertedManaCost(float convertedManaCost) {
        this.convertedManaCost = convertedManaCost;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}
