package com.jojoreference.mtgmod.items;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jojoreference.mtgmod.MTGMod;
import com.jojoreference.mtgmod.card.Card;
import com.jojoreference.mtgmod.card.ICard;
import com.jojoreference.mtgmod.card.SetData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class MagicCardItem extends Item {

    public static SetData data;

    public MagicCardItem() throws IOException {
        super(new Item.Properties().group(MTGMod.setup.itemGroup));
        setRegistryName("magiccard");
        data = new SetData();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT tag = stack.getTag();
        if ((tag != null) && !(tag.getString("name").equals(""))) {
            //Name
            tooltip.add(new StringTextComponent(tag.getString("name")).applyTextStyle(TextFormatting.GRAY));
            //Mana Cost
            StringTextComponent manaCost = new StringTextComponent("");
            TextFormatting color = TextFormatting.GRAY;
            for (int i = 0; i < tag.getString("manaCost").length(); i++) {
                char c = tag.getString("manaCost").charAt(i);
                if (c != '{' && c != '}') {
                    switch (c) {
                        case 'W':
                            color = TextFormatting.WHITE;
                            break;
                        case 'U':
                            color = TextFormatting.BLUE;
                            break;
                        case 'B':
                            color = TextFormatting.BLACK;
                            break;
                        case 'R':
                            color = TextFormatting.RED;
                            break;
                        case 'G':
                            color = TextFormatting.DARK_GREEN;
                            break;
                        default:
                            color = TextFormatting.GRAY;
                            break;
                    }
                    manaCost.appendText(String.valueOf(c));
                }
            }
            tooltip.add(manaCost.applyTextStyle(color));
            //Typeline
            tooltip.add(new StringTextComponent(tag.getString("type")).applyTextStyle(TextFormatting.GRAY));
            //Text
            tooltip.add(new StringTextComponent(tag.getString("text"))
                    .applyTextStyle(TextFormatting.GRAY));
            //P/T
            if(tag.getString("type").contains("Creature") || tag.getString("type").contains("Vehicle")) {
                tooltip.add(new StringTextComponent(tag.getString("power")
                        + "/" + tag.getString("toughness"))
                        .applyTextStyle(TextFormatting.GRAY));
            }
            //Rarity
            switch (tag.getString("rarity")) {
                case "uncommon":
                    color = TextFormatting.GRAY;
                    break;
                case "rare":
                    color = TextFormatting.GOLD;
                    break;
                case "mythic rare":
                    color = TextFormatting.DARK_RED;
                    break;
                default:
                    color = TextFormatting.WHITE;
                    break;
            }
            tooltip.add(new StringTextComponent(tag.getString("rarity")).applyTextStyle(color));
        } else {
            tooltip.add(new StringTextComponent("This card is blank. Right click to transform into a random card.")
                    .applyTextStyle(TextFormatting.GRAY));
        }
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        ICard cap = stack.getCapability(Card.CARD_CAPABILITY, null).orElse(null);
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("name", cap.getName());
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (nbt != null) {
            ICard cap = stack.getCapability(Card.CARD_CAPABILITY, null).orElse(null);
            cap.setName(nbt.getString("name"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (itemStack.getTag() == null) {
            itemStack.setTag(getShareTag(itemStack));
        }
        if (!itemStack.getTag().getString("name").equals("")) {
            return new ActionResult<>(ActionResultType.FAIL, itemStack);
        } else {
            Random random = new Random();
            JsonObject randomCard = data.RandomCard();
            initCard(itemStack, randomCard);
            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        }
    }

    public void initCard(ItemStack stack, JsonObject card) {
        CompoundNBT tag = stack.getTag();
        safeGetString(tag, "name", card);
        safeGetString(tag, "type", card);
        safeGetString(tag, "text", card);
        safeGetString(tag, "power", card);
        safeGetString(tag, "toughness", card);
        safeGetString(tag, "rarity", card);
        safeGetString(tag, "manaCost", card);
        tag.putFloat("cmc", card.get("convertedManaCost").getAsFloat());
        readShareTag(stack, tag);
    }

    private void safeGetString(CompoundNBT tag, String key, JsonObject obj) {
        if(obj.has(key)) {
            tag.putString(key, obj.get(key).getAsString());
        } else {
            tag.putString(key, "");
        }
    }
}