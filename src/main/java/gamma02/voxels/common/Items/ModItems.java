package gamma02.voxels.common.Items;

import gamma02.voxels.common.registry.Registrar;
import gamma02.voxels.common.registry.RegistryDeclaration;
import gamma02.voxels.common.registry.RegistryEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.lwjgl.system.CallbackI;

@Registrar(element = Item.class, modid = "voxels")
public class ModItems {

    @RegistryDeclaration
    public static final IForgeRegistry<Item> ITEMS = ForgeRegistries.ITEMS;

    @RegistryEntry("voxel_item_1")
    public static final Item VOXEL_ITEM_1 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_2")
    public static final Item VOXEL_ITEM_2 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_3")
    public static final Item VOXEL_ITEM_3 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_4")
    public static final Item VOXEL_ITEM_4 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_5")
    public static final Item VOXEL_ITEM_5 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_6")
    public static final Item VOXEL_ITEM_6 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_7")
    public static final Item VOXEL_ITEM_7 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_8")
    public static final Item VOXEL_ITEM_8 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_9")
    public static final Item VOXEL_ITEM_9 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_10")
    public static final Item VOXEL_ITEM_10 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_11")
    public static final Item VOXEL_ITEM_11 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_12")
    public static final Item VOXEL_ITEM_12 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_13")
    public static final Item VOXEL_ITEM_13 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_14")
    public static final Item VOXEL_ITEM_14 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_15")
    public static final Item VOXEL_ITEM_15 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_16")
    public static final Item VOXEL_ITEM_16 = new Item(new Item.Properties());

    @RegistryEntry("voxel_item_17")
    public static final Item VOXEL_ITEM_17 = new Item(new Item.Properties());

}
