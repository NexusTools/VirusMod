package steve4448.ExpandedTNT;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import steve4448.ExpandedTNT.ArrowsTNT.BlockArrowsTNT;
import steve4448.ExpandedTNT.ArrowsTNT.EntityArrowTNTPrimed;
import steve4448.ExpandedTNT.ArrowsTNT.RenderArrowTNTPrimed;
import steve4448.ExpandedTNT.EggTNT.BlockEggTNT;
import steve4448.ExpandedTNT.EggTNT.EntityEggTNTPrimed;
import steve4448.ExpandedTNT.EggTNT.RenderEggTNTPrimed;
import steve4448.ExpandedTNT.TNTTNT.BlockTNTTNT;
import steve4448.ExpandedTNT.TNTTNT.EntityTNTTNTPrimed;
import steve4448.ExpandedTNT.TNTTNT.RenderTNTTNTPrimed;
import steve4448.ExpandedTNT.TNTTNTTNT.BlockTNTTNTTNT;
import steve4448.ExpandedTNT.TNTTNTTNT.EntityTNTTNTTNTPrimed;
import steve4448.ExpandedTNT.TNTTNTTNT.RenderTNTTNTTNTPrimed;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ExpandedTNT", name = "Expanded TNT", version = "0.9")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ExpandedTNT {
	public static final int TNT_TEXTURE_TOP = 16, TNT_TEXTURE_BOTTOM = 17, TNT_ARROWS_SIDES = 0, TNT_EGGS_SIDES = 1, TNT_TNT_SIDES = 2, TNT_TNT_TNT_SIDES = 3;

	public static int blockArrowsTNTID, blockEggTNTID, blockTNTTNTID, blockTNTTNTNTTID;

	public static boolean blockArrowsTNTEnabled, blockEggTNTEnabled, blockTNTTNTEnabled, blockTNTTNTTNTEnabled;

	public static Block blockArrowsTNT, blockEggTNT, blockTNTTNT, blockTNTTNTTNT;

	@PreInit
	public void preload(FMLPreInitializationEvent iEvent) {
		Configuration conf = new Configuration(iEvent.getSuggestedConfigurationFile());
		conf.load();
		blockArrowsTNTEnabled = conf.get("Enabled Blocks", "blockArrowsTNTEnabled", true).getBoolean(true);
		blockEggTNTEnabled = conf.get("Enabled Blocks", "blockEggTNTEnabled", true).getBoolean(true);
		blockTNTTNTEnabled = conf.get("Enabled Blocks", "blockTNTTNTEnabled", true).getBoolean(true);
		blockTNTTNTTNTEnabled = conf.get("Enabled Blocks", "blockTNTTNTTNTEnabled", true).getBoolean(true);

		blockArrowsTNTID = conf.getBlock("blockArrowsTNTID", 408).getInt();
		blockEggTNTID = conf.getBlock("blockEggTNTID", 409).getInt();
		blockTNTTNTID = conf.getBlock("blockTNTTNTID", 410).getInt();
		blockTNTTNTNTTID = conf.getBlock("blockTNTTNTNTTID", 411).getInt();
		conf.save();
	}

	@Init
	public void load(FMLInitializationEvent iEvent) {
		MinecraftForgeClient.preloadTexture("/steve4448/images/tntsheet.png");
		blockArrowsTNT = new BlockArrowsTNT(blockArrowsTNTID);
		blockEggTNT = new BlockEggTNT(blockEggTNTID);
		blockTNTTNT = new BlockTNTTNT(blockTNTTNTID);
		blockTNTTNTTNT = new BlockTNTTNTTNT(blockTNTTNTNTTID);

		if(blockArrowsTNTEnabled) GameRegistry.registerBlock(blockArrowsTNT, "Arrow TNT");
		if(blockEggTNTEnabled) GameRegistry.registerBlock(blockEggTNT, "Egg TNT");
		if(blockTNTTNTEnabled) GameRegistry.registerBlock(blockTNTTNT, "TNT TNT");
		if(blockTNTTNTEnabled && blockTNTTNTTNTEnabled) GameRegistry.registerBlock(blockTNTTNTTNT, "TNT TNT TNT");

		if(blockArrowsTNTEnabled) LanguageRegistry.addName(blockArrowsTNT, "Arrow TNT");
		if(blockEggTNTEnabled) LanguageRegistry.addName(blockEggTNT, "Egg TNT");
		if(blockTNTTNTEnabled) LanguageRegistry.addName(blockTNTTNT, "TNT TNT");
		if(blockTNTTNTEnabled && blockTNTTNTTNTEnabled) LanguageRegistry.addName(blockTNTTNTTNT, "TNT TNT TNT");

		if(blockArrowsTNTEnabled) GameRegistry.addRecipe(new ItemStack(blockArrowsTNT), "AAA", "ATA", "AAA", Character.valueOf('A'), new ItemStack(Item.arrow), Character.valueOf('T'), new ItemStack(Block.tnt));
		if(blockEggTNTEnabled) GameRegistry.addRecipe(new ItemStack(blockEggTNT), "EEE", "ETE", "EEE", Character.valueOf('E'), new ItemStack(Item.egg), Character.valueOf('T'), new ItemStack(Block.tnt));
		if(blockTNTTNTEnabled) GameRegistry.addRecipe(new ItemStack(blockTNTTNT), "TTT", "TTT", "TTT", Character.valueOf('T'), new ItemStack(Block.tnt));
		if(blockTNTTNTEnabled && blockTNTTNTTNTEnabled) GameRegistry.addRecipe(new ItemStack(blockTNTTNTTNT), "EEE", "ETE", "EEE", Character.valueOf('E'), new ItemStack(blockTNTTNT), Character.valueOf('T'), new ItemStack(Block.tnt));

		if(blockArrowsTNTEnabled) RenderingRegistry.registerEntityRenderingHandler(EntityArrowTNTPrimed.class, new RenderArrowTNTPrimed());
		if(blockEggTNTEnabled) RenderingRegistry.registerEntityRenderingHandler(EntityEggTNTPrimed.class, new RenderEggTNTPrimed());
		if(blockTNTTNTEnabled) RenderingRegistry.registerEntityRenderingHandler(EntityTNTTNTPrimed.class, new RenderTNTTNTPrimed());
		if(blockTNTTNTEnabled && blockTNTTNTTNTEnabled) RenderingRegistry.registerEntityRenderingHandler(EntityTNTTNTTNTPrimed.class, new RenderTNTTNTTNTPrimed());

		if(blockArrowsTNTEnabled) EntityRegistry.registerModEntity(EntityArrowTNTPrimed.class, "EntityArrowTNTPrimed", 1, this, 160, 3, true);
		if(blockEggTNTEnabled) EntityRegistry.registerModEntity(EntityEggTNTPrimed.class, "EntityEggTNTPrimed", 2, this, 160, 3, true);
		if(blockTNTTNTEnabled) EntityRegistry.registerModEntity(EntityTNTTNTPrimed.class, "EntityTNTTNTPrimed", 3, this, 160, 3, true);
		if(blockTNTTNTEnabled && blockTNTTNTTNTEnabled) EntityRegistry.registerModEntity(EntityTNTTNTTNTPrimed.class, "EntityTNTTNTTNTPrimed", 4, this, 160, 3, true);
	}
}
