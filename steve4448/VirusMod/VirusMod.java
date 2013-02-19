package steve4448.VirusMod;

import java.util.ArrayList;

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

import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.MLProp;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.modloader.ModLoaderBlockRendererHandler;
import cpw.mods.fml.client.modloader.ModLoaderClientHelper;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.modloader.ModLoaderNetworkHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "VirusMod", name = "Virus Mod", version = "0.1") 
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class VirusMod {
	public static final int TEXTURE_VIRUS_EATER = 0;
	@MLProp(name = "blockEaterVirus", min = 125, max = 4096)
	public static int blockEaterVirusID;
	
	public static Block blockEaterVirus;
	
	@PreInit
	public void preload(FMLPreInitializationEvent iEvent) {
	    Configuration conf = new Configuration(iEvent.getSuggestedConfigurationFile());
	    conf.load();
	    blockEaterVirusID = conf.getBlock("blockEaterVirusID", 204).getInt();
	    conf.save();
	}
	
	@Init
	public void load(FMLInitializationEvent IEvent) {
		MinecraftForgeClient.preloadTexture("/steve4448/images/virussheet.png");
		blockEaterVirus = new BlockEaterVirus(blockEaterVirusID);
		
		GameRegistry.registerBlock(blockEaterVirus, "Eater Virus");
		
		LanguageRegistry.addName(blockEaterVirus, "Eater Virus");
		
		GameRegistry.registerTileEntity(TileEntityVirusController.class, "virusController");
	}
}
