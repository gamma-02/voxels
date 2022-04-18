package gamma02.voxels;

import gamma02.voxels.client.ClientVoxelTracker;
import gamma02.voxels.common.Items.ModItems;
import gamma02.voxels.common.ModCommands;
import gamma02.voxels.common.Network.Networking;
import gamma02.voxels.common.blocks.BlockEntities.ModBlockEntities;
import gamma02.voxels.common.blocks.ModBlocks;
import gamma02.voxels.common.registry.Matrix;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("voxels")
public class Voxels {



    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static MinecraftServer server;

    public Voxels() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModBlockEntities.register(MinecraftForge.EVENT_BUS);



        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


    }

    private void setup(final FMLCommonSetupEvent event) {
        Networking.init();
    }

    @SubscribeEvent
    public static void onServerStartingEvent(ServerStartingEvent evt){
        server = evt.getServer();
    }

    @SubscribeEvent
    public static void onItemStackTooltip(ItemTooltipEvent evt){

    }


    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeRegistryEvents {
        @SubscribeEvent
        public static void onCommandsRegistry(RegisterCommandsEvent evt) {
            evt.getDispatcher().register(ModCommands.getArgumentBuilder());

        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModRegistryEvents{
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> evt){
            Matrix.register(ModItems.class, evt);
        }
        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<Block> evt){
            Matrix.register(ModBlocks.class, evt);
        }

    }
}
