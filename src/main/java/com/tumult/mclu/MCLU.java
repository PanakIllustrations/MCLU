package com.tumult.mclu;

import com.tumult.mclu.client.CustomAttributes;
import com.tumult.mclu.events.ModEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(MCLU.MODID)
public class MCLU
{
    public static final String MODID = "mclu";
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
        /ATTRIBUTES.register(modEventBus);
        CustomAttributes.ATTRIBUTES.register(FMLJavaModLoadingContext.get().getModEventBus());
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }


    private void commonSetup(FMLCommonSetupEvent event) {
        // Common setup code
    }

    @Mod.EventBusSubscriber(modid = MCLU.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
