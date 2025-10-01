package me.millen.utils.management.managers;
/*
 *  created by millen on 31/05/2020
 */

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import me.millen.utils.core.Base;
import me.millen.utils.management.Manager;
import me.millen.utils.module.Module;
import me.millen.utils.module.modules.blockglitch.Blockglitch;
import me.millen.utils.module.modules.giveaways.Giveaways;

public class ModuleManager extends Manager{

	private List<Module> modules = new CopyOnWriteArrayList<>();

	public void setup(){
		add(new Giveaways());
		add(new Blockglitch());
	}

	public void add(Module module){
		modules.add(module);
		Base.get().getServer().getPluginManager().registerEvents(module, Base.get());
	}

	public Module get(Class<? extends Module> module){
		for(Module m : modules)
			if(m.getClass().equals(module))
				return m;

		return null;
	}
}
