package com.tumult.mclu;

import com.tumult.mclu.client.gui.CustomAttributes;
import com.tumult.mclu.client.gui.icons.GuiSprite;
import com.tumult.mclu.client.gui.icons.SpriteUploader;
import com.tumult.mclu.events.ModEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import com.tumult.mclu.client.gui.icons.IconUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(McluConstants.MOD_ID)
public class MCLU
{
    private static final Logger LOGGER = LogManager.getLogger();

    //public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MODID);
    //public static final RegistryObject<Attribute> MCLU_ARMOR_ATTRIBUTE = ATTRIBUTES.register("mclu_armor_attribute", () -> new Attribute("attribute.name.mclu.mclu_armor_attribute", 0.0D) {
        //@Override
        //public double getDefaultValue() {
            //return 0.0D;
        //}
    //});
    public MCLU() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CustomAttributes.ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }

    @SubscribeEvent
    public void commonSetup(FMLClientSetupEvent event) {
        // Common setup code
    }

    @Mod.EventBusSubscriber(modid = McluConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class clientSetupEvent {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            IconUtils.initialize();
        }
    }
}
