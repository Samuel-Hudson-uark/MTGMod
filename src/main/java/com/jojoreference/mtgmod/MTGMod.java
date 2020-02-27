package com.jojoreference.mtgmod;

import com.jojoreference.mtgmod.blocks.ModBlocks;
import com.jojoreference.mtgmod.blocks.RubyBlock;
import com.jojoreference.mtgmod.blocks.RubyOre;
import com.jojoreference.mtgmod.card.Card;
import com.jojoreference.mtgmod.card.CardStorage;
import com.jojoreference.mtgmod.card.ICard;
import com.jojoreference.mtgmod.items.MagicCardItem;
import com.jojoreference.mtgmod.items.RubyItem;
import com.jojoreference.mtgmod.setup.ClientProxy;
import com.jojoreference.mtgmod.setup.IProxy;
import com.jojoreference.mtgmod.setup.ModSetup;
import com.jojoreference.mtgmod.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("mtgmod")
public class MTGMod {

    public static final String MODID = "mtgmod";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ModSetup setup = new ModSetup();

    private static final Logger LOGGER = LogManager.getLogger();

    public MTGMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(EventRegister.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        setup.init();
        proxy.init();
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICard.class, new CardStorage(), Card::new);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void BlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new RubyBlock());
            event.getRegistry().register(new RubyOre());
        }

        @SubscribeEvent
        public static void ItemsRegistry(final RegistryEvent.Register<Item> event) throws IOException {
            Item.Properties properties = new Item.Properties().group(setup.itemGroup);
            event.getRegistry().register(new BlockItem(ModBlocks.RUBYBLOCK, properties).setRegistryName("rubyblock"));
            event.getRegistry().register(new BlockItem(ModBlocks.RUBYORE, properties).setRegistryName("rubyore"));

            event.getRegistry().register(new RubyItem());
            event.getRegistry().register(new MagicCardItem());
        }
    }
}