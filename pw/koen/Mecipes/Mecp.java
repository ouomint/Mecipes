package pw.koen.Mecipes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class MethaneGas extends JavaPlugin implements Listener {
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		ConfigurationSection cs = this.getConfig().getConfigurationSection("Recipes");
		for (String k:cs.getKeys(false)) {
			String n = k;
			// 레시피 이름 RQ //
			String t = this.getConfig().getString("Recipes."+k+".type").toLowerCase();
			// 레시피 형태 RQ; SHAPED,SHAPELESS //
			int r = this.getConfig().getInt("Recipes."+k+".result");
			// 레시피 결과물 RQ //
			int q = this.getConfig().getInt("Recipes."+k+".qty");
			// 레시피 결과물 수량 RQ //
			List<?> i = this.getConfig().getList("Recipes."+k+".ingredients");
			// 레시피 성분 RQ; SHAPED시'CHAR;ITEM ID'SHAPELESS시'ITEM ID' //
			List<?> f = this.getConfig().getList("Recipes."+k+".form");
			// 레시피 형태; SHAPED RQ //
			if(t.contains("shapeless")) {
				ShapelessRecipe sr = new ShapelessRecipe(new ItemStack(Material.getMaterial(r), q));
				Iterator<?> er = i.iterator();
				while(er.hasNext()){
					sr.addIngredient(Material.getMaterial(Integer.parseInt((String) er.next())));
				}
				Bukkit.getServer().addRecipe(sr);
				System.out.println("Mecipes: Added -"+t+" "+n+"="+q+"x"+r);
			}
			if(t.contains("shaped")) {
				String[] s = new String [f.size()];
				f.toArray(s);
				System.out.println("Mecipes: / Form: "+Arrays.asList(s));
				ShapedRecipe sr = new ShapedRecipe(new ItemStack(Material.getMaterial(r), q));
				sr.shape(s);
				Iterator<?> er = i.iterator();
				while(er.hasNext()){
					String[] ex = ((String) er.next()).split(";");
					char c = ex[0].charAt(0);
					sr.setIngredient(c, Material.getMaterial(Integer.parseInt(ex[1])));
					System.out.println("Mecipes: / Ingredient: "+ex[1]+" = "+c);
				}
				Bukkit.getServer().addRecipe(sr);
				System.out.println("Mecipes: Added -"+t+" "+n+"="+q+"x"+r);
			}
		}
	}
	@Override
	public void onDisable() {
		Bukkit.getServer().clearRecipes();
	}
}
