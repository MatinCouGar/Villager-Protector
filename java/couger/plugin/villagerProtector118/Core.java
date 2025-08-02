package couger.plugin.villagerProtector118;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashSet;
import java.util.Set;

public class Core implements Listener, CommandExecutor {

    private final VillagerProtectorPlugin plugin;
    private final Set<String> protectedPlayers = new HashSet<>();

    public Core(VillagerProtectorPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getCommand("villagerprotector").setExecutor(this);
        plugin.getCommand("vp").setExecutor(this);
    }

    @EventHandler
    public void onVillagerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.VILLAGER) return;
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        if (protectedPlayers.contains(player.getName())) {
            event.setCancelled(true);
            player.sendMessage("§aTo hit villagers, type /vp off.");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        protectedPlayers.add(player.getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1 || (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))) {
            player.sendMessage("§eUsage: /" + label + " <on|off>");
            return true;
        }

        if (args[0].equalsIgnoreCase("on")) {
            protectedPlayers.add(player.getName());
            player.sendMessage("§aVillager protection enabled.");
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1f, 1.5f);
        } else {
            protectedPlayers.remove(player.getName());
            player.sendMessage("§cVillager protection disabled.");
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1f, 0.5f);
        }

        return true;
    }
}
