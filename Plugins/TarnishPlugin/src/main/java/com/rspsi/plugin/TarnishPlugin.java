package com.rspsi.plugin;

import com.rspsi.plugins.core.ClientPlugin;
import com.displee.cache.index.archive.Archive;

import com.jagex.Client;
import com.jagex.cache.loader.anim.AnimationDefinitionLoader;
import com.jagex.cache.loader.anim.FrameBaseLoader;
import com.jagex.cache.loader.anim.FrameLoader;
import com.jagex.cache.loader.anim.GraphicLoader;
import com.jagex.cache.loader.config.VariableBitLoader;
import com.jagex.cache.loader.floor.FloorDefinitionLoader;
import com.jagex.cache.loader.map.MapIndexLoader;
import com.jagex.cache.loader.object.ObjectDefinitionLoader;
import com.jagex.cache.loader.textures.TextureLoader;
import com.jagex.net.ResourceResponse;
import com.rspsi.plugin.loader.AnimationDefinitionLoaderTarnish;
import com.rspsi.plugin.loader.FloorDefinitionLoaderTarnish;
import com.rspsi.plugin.loader.FrameBaseLoaderTarnish;
import com.rspsi.plugin.loader.FrameLoaderOSRS;
import com.rspsi.plugin.loader.GraphicLoaderOSRS;
import com.rspsi.plugin.loader.MapIndexLoaderTarnish;
import com.rspsi.plugin.loader.ObjectDefinitionLoaderTarnish;
import com.rspsi.plugin.loader.TextureLoaderOSRS;
import com.rspsi.plugin.loader.VarbitLoaderOSRS;

public class TarnishPlugin implements ClientPlugin {

	private FrameLoaderOSRS frameLoader;
	
	@Override
	public void initializePlugin() {
		ObjectDefinitionLoader.instance = new ObjectDefinitionLoaderTarnish();
		FloorDefinitionLoader.instance = new FloorDefinitionLoaderTarnish();
		AnimationDefinitionLoader.instance = new AnimationDefinitionLoaderTarnish();
		MapIndexLoader.instance = new MapIndexLoaderTarnish();
		TextureLoader.instance = new TextureLoaderOSRS();
		frameLoader = new FrameLoaderOSRS();
		FrameLoader.instance = frameLoader;
		FrameBaseLoader.instance = new FrameBaseLoaderTarnish();
		GraphicLoader.instance = new GraphicLoaderOSRS();
		VariableBitLoader.instance = new VarbitLoaderOSRS();
	}

	@Override
	public void onGameLoaded(Client client) {
			frameLoader.init(2500);
			Archive config = client.getCache().createArchive(2, "config");
			ObjectDefinitionLoader.instance.init(config);
			FloorDefinitionLoader.instance.init(config);
			AnimationDefinitionLoader.instance.init(config);
			GraphicLoader.instance.init(config);
			VariableBitLoader.instance.init(config);

			Archive version = client.getCache().createArchive(5, "update list");
			MapIndexLoader.instance.init(version);


			Archive textures = client.getCache().createArchive(6, "textures");
			TextureLoader.instance.init(textures);
		
	}

	@Override
	public void onResourceDelivered(ResourceResponse arg0) {
		// TODO Auto-generated method stub
		
	}

}
