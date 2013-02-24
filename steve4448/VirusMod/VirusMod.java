package steve4448.VirusMod;

import net.minecraft.block.Block;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "VirusMod", name = "Virus Mod", version = "0.2.6")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class VirusMod {
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
	public static Block blockEaterVirus;

	@PreInit
	public void preload(FMLPreInitializationEvent iEvent) {
		Configuration conf = new Configuration(iEvent.getSuggestedConfigurationFile());
		conf.load();
		blockEaterVirusControllerID = conf.getBlock("blockEaterVirusControllerID", 450).getInt();
		blockEaterVirusID = conf.getBlock("blockEaterVirusID", 451).getInt();
		uneatable = conf.get("Eater Virus", "uneatable", new int[] { 0, Block.obsidian.blockID, blockEaterVirusControllerID, blockEaterVirusID }, "Blocks that are not to be \"aten\" by the eater virus.").getIntList();
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
		blockEaterVirusController = new BlockEaterVirusController(blockEaterVirusControllerID);
		blockEaterVirus = new BlockEaterVirus(blockEaterVirusID);

		GameRegistry.registerBlock(blockEaterVirusController, "EaterVirusController");
		LanguageRegistry.addName(blockEaterVirus, "Eater Virus");

		GameRegistry.registerBlock(blockEaterVirus, "EaterVirus");
		LanguageRegistry.addName(blockEaterVirus, "Eater Virus");

		GameRegistry.registerTileEntity(TileEntityEaterVirus.class, "TileEntityEaterVirus");
	}
}
