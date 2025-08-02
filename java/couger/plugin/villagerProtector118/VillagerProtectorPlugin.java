package couger.plugin.villagerProtector118;

import org.bukkit.plugin.java.JavaPlugin;

public class
VillagerProtectorPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        new Core(this);
        getLogger().info("✅ Villager Protector enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("❌ Villager Protector disabled.");
    }
}
