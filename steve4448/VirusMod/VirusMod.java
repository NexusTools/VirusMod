package steve4448.VirusMod;

import java.io.IOException;

import steve4448.VirusMod.block.BlockVirusStub;
import steve4448.VirusMod.block.BlockEaterVirusController;
import steve4448.VirusMod.tileentity.TileEntityEaterVirus;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModTextureAnimation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "VirusMod", name = "Virus Mod", version = "0.2.9")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class VirusMod {
	public static final String[] virusBlockNames = { 
		"Eater Virus", "Replacer Virus", "Tool Virus"
	};
	
	public static final int TEXTURE_VIRUS_EATER = 0, TEXTURE_VIRUS_STUB = 1;
	public static int blockEaterVirusControllerID;
	public static int blockEaterVirusID;
	public static int[] uneatable;
	public static boolean useBlockResistance;
	public static int eaterStrengthMin;
	public static int eaterStrengthMax;
	public static int eaterTickRate;
	public static int eaterIterationsPerTick;
	public static double eaterDegradation;
	

	public static Block blockEaterVirusController;
	public static Block blockVirusStub;

	@PreInit
	public void preload(FMLPreInitializationEvent iEvent) {
		Configuration conf = new Configuration(iEvent.getSuggestedConfigurationFile());
		conf.load();
		blockEaterVirusControllerID = conf.getBlock("blockEaterVirusControllerID", 450).getInt();
		blockEaterVirusID = conf.getBlock("blockEaterVirusID", 451).getInt();
		uneatable = conf.get("Eater Virus", "uneatable", new int[]{0, Block.obsidian.blockID, blockEaterVirusControllerID, blockEaterVirusID}, "Blocks that are not to be \"aten\" by the eater virus.").getIntList();
		useBlockResistance = conf.get("Eater Virus", "useBlockResistance", true, "The virus will degrade more based on the blocks it destroys.").getBoolean(true);
		eaterStrengthMin = conf.get("Eater Virus", "eaterStrengthMin", 400).getInt();
		eaterStrengthMax = conf.get("Eater Virus", "eaterStrengthMax", 1000).getInt();
		eaterTickRate = conf.get("Eater Virus", "eaterTickRate", 1).getInt();
		eaterIterationsPerTick = conf.get("Eater Virus", "eaterIterationsPerTick", 5).getInt();
		eaterDegradation = conf.get("Eater Virus", "eaterDegradation", 1).getDouble(1);
		conf.save();
	}

	@Init
	public void load(FMLInitializationEvent IEvent) {
		MinecraftForgeClient.preloadTexture("/steve4448/images/virussheet.png");
		MinecraftForgeClient.preloadTexture("/steve4448/anim/eatervirusanim.png");
		
		blockEaterVirusController = new BlockEaterVirusController(blockEaterVirusControllerID);
		blockVirusStub = new BlockVirusStub(blockEaterVirusID);

		GameRegistry.registerBlock(blockEaterVirusController, "EaterVirusController");
		LanguageRegistry.addName(blockEaterVirusController, "Eater Virus");

		GameRegistry.addRecipe(new ItemStack(blockEaterVirusController), "ZCZ", "RSR", "ZCZ", Character.valueOf('Z'), new ItemStack(Item.rottenFlesh), Character.valueOf('R'), new ItemStack(Item.beefRaw), Character.valueOf('S'), new ItemStack(Item.slimeBall), Character.valueOf('C'), new ItemStack(Item.chickenRaw));
		GameRegistry.addRecipe(new ItemStack(blockEaterVirusController), "ZRZ", "CSC", "ZRZ", Character.valueOf('Z'), new ItemStack(Item.rottenFlesh), Character.valueOf('R'), new ItemStack(Item.beefRaw), Character.valueOf('S'), new ItemStack(Item.slimeBall), Character.valueOf('C'), new ItemStack(Item.chickenRaw));

		GameRegistry.registerBlock(blockVirusStub, "VirusStub");
		LanguageRegistry.addName(blockVirusStub, "Virus Stub");
		for (int i = 0; i < virusBlockNames.length; i++)
			LanguageRegistry.addName(new ItemStack(blockVirusStub, 1, i), virusBlockNames[i]);

		GameRegistry.registerTileEntity(TileEntityEaterVirus.class, "TileEntityEaterVirus");
		
		ModTextureAnimation virusAnim;
		try {
			virusAnim = new ModTextureAnimation(0, 1, "/steve4448/images/virussheet.png", TextureFXManager.instance().loadImageFromTexturePack(Minecraft.getMinecraft().renderEngine, "/steve4448/anim/eatervirusanim.png"), 2);
			virusAnim.setup();
			virusAnim.bindImage(Minecraft.getMinecraft().renderEngine);
			TextureFXManager.instance().addAnimation(virusAnim);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
