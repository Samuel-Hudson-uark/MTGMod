package com.jojoreference.mtgmod.card;

public interface ICard {
    void setName(String name);
    String getName();

    String getManaCost();
    void setManaCost(String manaCost);

    float getConvertedManaCost();
    void setConvertedManaCost(float convertedManaCost);

    String getTypes();
    void setTypes(String types);

    String getText();
    void setText(String text);

    String getPower();
    void setPower(String power);

    String getToughness();
    void setToughness(String toughness);

    String getRarity();
    void setRarity(String rarity);
}
