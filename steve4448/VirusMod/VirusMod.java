package steve4448.VirusMod;

import java.io.IOException;

import steve4448.VirusMod.block.BlockReplacerVirusController;
import steve4448.VirusMod.block.BlockToolVirusController;
import steve4448.VirusMod.block.BlockVirusStub;
import steve4448.VirusMod.block.BlockEaterVirusController;
import steve4448.VirusMod.tileentity.TileEntityVirus;
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

@Mod(modid = "VirusMod", name = "Virus Mod", version = "0.3.7")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class VirusMod {
	public static final float VIRUS_DEGRADATION = 1;
	public static final String[] VIRUS_BLOCK_NAMES = { 
		"Eater Virus Stub", "Replacer Virus Stub", "Tool Virus Stub"
	};
	
	public static final int TEXTURE_EATER_VIRUS = 0, TEXTURE_REPLACER_VIRUS = 1, TEXTURE_TOOL_VIRUS = 2, TEXTURE_HACKY_STUB = 3;
	public static final int EATER_VIRUS = 0, REPLACER_VIRUS = 1, TOOL_VIRUS = 2;
	public static Block blockVirusStub;
	public static int blockVirusStubId;
	public static int[] untouchable;
	private int[] loadedUntouchable;
	public static boolean useBlockResistance;
	public static int virusTickRate;
	public static int virusIterationsPerTick;
	/* Eater Virus */
	public static Block blockEaterVirusController;
	public static int blockEaterVirusControllerId;
	public static boolean eaterVirusEnabled;
	public static int eaterVirusStrengthMin;
	public static int eaterVirusStrengthMax;
	/* Replacer Virus */
	public static Block blockReplacerVirusController;
	public static int blockReplacerVirusControllerId;
	public static boolean replacerVirusEnabled;
	public static int replacerVirusStrengthMin;
	public static int replacerVirusStrengthMax;
	/* Tool Virus */
	public static Block blockToolVirusController;
	public static int blockToolVirusControllerId;
	public static boolean toolVirusEnabled;
	public static int toolVirusStrengthMin;
	public static int toolVirusStrengthMax;

	@PreInit
	public void preload(FMLPreInitializationEvent iEvent) {
		Configuration conf = new Configuration(iEvent.getSuggestedConfigurationFile());
		conf.load();
		blockVirusStubId = conf.getBlock("blockVirusStubId", 450).getInt();
		blockEaterVirusControllerId = conf.getBlock("blockEaterVirusControllerId", 451).getInt();
		blockReplacerVirusControllerId = conf.getBlock("blockReplacerVirusControllerId", 452).getInt();
		blockToolVirusControllerId = conf.getBlock("blockToolVirusControllerId", 453).getInt();
		
		loadedUntouchable = conf.get("Viruses", "untouchable", new int[]{0, Block.obsidian.blockID}, "Blocks that are not to be \"aten\" by the eater virus.").getIntList();
		useBlockResistance = conf.get("Viruses", "useBlockResistance", true, "The virus will degrade more based on the blocks it destroys.").getBoolean(true);
		virusTickRate = conf.get("Viruses", "tickRate", 1).getInt();
		virusIterationsPerTick = conf.get("Viruses", "iterationsPerTick", 5).getInt();
		
		
		eaterVirusEnabled = conf.get("Eater Virus", "eaterVirusEnabled", true).getBoolean(true);
		eaterVirusStrengthMin = conf.get("Eater Virus", "eaterVirusStrengthMin", 400).getInt();
		eaterVirusStrengthMax = conf.get("Eater Virus", "eaterVirusStrengthMax", 1000).getInt();
		
		replacerVirusEnabled = conf.get("Replacer Virus", "replacerVirusEnabled", true).getBoolean(true);
		replacerVirusStrengthMin = conf.get("Replacer Virus", "replacerVirusStrengthMin", 200).getInt();
		replacerVirusStrengthMax = conf.get("Replacer Virus", "replacerVirusStrengthMax", 500).getInt();
		
		toolVirusEnabled = conf.get("Tool Virus", "toolVirusEnabled", true).getBoolean(true);
		toolVirusStrengthMin = conf.get("Tool Virus", "toolVirusStrengthMin", 400).getInt();
		toolVirusStrengthMax = conf.get("Tool Virus", "toolVirusStrengthMax", 1000).getInt();
		conf.save();
		int[] localUntouchable = new int[]{blockVirusStubId, blockEaterVirusControllerId, blockReplacerVirusControllerId, blockToolVirusControllerId};
		untouchable = new int[loadedUntouchable.length + localUntouchable.length];
		for(int i = 0; i < loadedUntouchable.length; i++)
			untouchable[i] = loadedUntouchable[i];
		for(int i = 0; i < localUntouchable.length; i++)
			untouchable[loadedUntouchable.length + i] = localUntouchable[i];
	}

	@Init
	public void load(FMLInitializationEvent IEvent) {
		MinecraftForgeClient.preloadTexture("/steve4448/VirusMod/images/virussheet.png");
		if(eaterVirusEnabled)
			MinecraftForgeClient.preloadTexture("/steve4448/VirusMod/anim/eatervirusanim.png");
		if(replacerVirusEnabled)
			MinecraftForgeClient.preloadTexture("/steve4448/VirusMod/anim/replacervirusanim.png");
		if(toolVirusEnabled)
			MinecraftForgeClient.preloadTexture("/steve4448/VirusMod/anim/toolvirusanim.png");
		
		blockVirusStub = new BlockVirusStub(blockVirusStubId);
		GameRegistry.registerBlock(blockVirusStub, "VirusStub");
		LanguageRegistry.addName(blockVirusStub, "Virus Stub");
		
		GameRegistry.registerTileEntity(TileEntityVirus.class, "TileEntityVirus");
		
		for (int i = 0; i < VIRUS_BLOCK_NAMES.length; i++)
			LanguageRegistry.addName(new ItemStack(blockVirusStub, 1, i), VIRUS_BLOCK_NAMES[i]);
		
		if(eaterVirusEnabled) {
			blockEaterVirusController = new BlockEaterVirusController(blockEaterVirusControllerId);
			GameRegistry.registerBlock(blockEaterVirusController, "EaterVirusController");
			LanguageRegistry.addName(blockEaterVirusController, "Eater Virus");
			GameRegistry.addRecipe(new ItemStack(blockEaterVirusController), "ZCZ", "RSR", "ZCZ", Character.valueOf('Z'), new ItemStack(Item.rottenFlesh), Character.valueOf('R'), new ItemStack(Item.beefRaw), Character.valueOf('S'), new ItemStack(Item.slimeBall), Character.valueOf('C'), new ItemStack(Item.chickenRaw));
			GameRegistry.addRecipe(new ItemStack(blockEaterVirusController), "ZRZ", "CSC", "ZRZ", Character.valueOf('Z'), new ItemStack(Item.rottenFlesh), Character.valueOf('R'), new ItemStack(Item.beefRaw), Character.valueOf('S'), new ItemStack(Item.slimeBall), Character.valueOf('C'), new ItemStack(Item.chickenRaw));
			
		}
		
		if(replacerVirusEnabled) {
			blockReplacerVirusController = new BlockReplacerVirusController(blockReplacerVirusControllerId);
			GameRegistry.registerBlock(blockReplacerVirusController, "ReplacerVirusController");
			LanguageRegistry.addName(blockReplacerVirusController, "Replacer Virus");
		}
		
		if(toolVirusEnabled) {
			blockToolVirusController = new BlockToolVirusController(blockToolVirusControllerId);
			GameRegistry.registerBlock(blockToolVirusController, "ToolVirusController");
			LanguageRegistry.addName(blockToolVirusController, "Tool Virus");
		}

		
		if(eaterVirusEnabled) {
			ModTextureAnimation virusEaterAnim;
			try {
				virusEaterAnim = new ModTextureAnimation(TEXTURE_EATER_VIRUS, 1, "/steve4448/VirusMod/images/virussheet.png", TextureFXManager.instance().loadImageFromTexturePack(Minecraft.getMinecraft().renderEngine, "/steve4448/VirusMod/anim/eatervirusanim.png"), 2);
				virusEaterAnim.setup();
				virusEaterAnim.bindImage(Minecraft.getMinecraft().renderEngine);
				TextureFXManager.instance().addAnimation(virusEaterAnim);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		if(replacerVirusEnabled) {
			ModTextureAnimation virusReplacerAnim;
			try {
				virusReplacerAnim = new ModTextureAnimation(TEXTURE_REPLACER_VIRUS, 1, "/steve4448/VirusMod/images/virussheet.png", TextureFXManager.instance().loadImageFromTexturePack(Minecraft.getMinecraft().renderEngine, "/steve4448/VirusMod/anim/replacervirusanim.png"), 2);
				virusReplacerAnim.setup();
				virusReplacerAnim.bindImage(Minecraft.getMinecraft().renderEngine);
				TextureFXManager.instance().addAnimation(virusReplacerAnim);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		if(toolVirusEnabled) {
			ModTextureAnimation virusToolAnim;
			try {
				virusToolAnim = new ModTextureAnimation(TEXTURE_TOOL_VIRUS, 1, "/steve4448/VirusMod/images/virussheet.png", TextureFXManager.instance().loadImageFromTexturePack(Minecraft.getMinecraft().renderEngine, "/steve4448/VirusMod/anim/toolvirusanim.png"), 2);
				virusToolAnim.setup();
				virusToolAnim.bindImage(Minecraft.getMinecraft().renderEngine);
				TextureFXManager.instance().addAnimation(virusToolAnim);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
