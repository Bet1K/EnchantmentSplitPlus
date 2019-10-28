package dev.jaqobb.enchantmentsconverter.command;

import dev.jaqobb.enchantmentsconverter.EnchantmentsConverterPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConvertEnchantmentsCommand implements CommandExecutor {

	private EnchantmentsConverterPlugin plugin;

	public ConvertEnchantmentsCommand(EnchantmentsConverterPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		if (!(sender instanceof Player)) {
			this.plugin.getMessages().getChatMessage("not-player")
				.color()
				.target(sender)
				.send();
			return true;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("enchantmentsconverter.command.convertenchantments")) {
			this.plugin.getMessages().getChatMessage("no-permissions")
				.color()
				.target(player)
				.send();
			return true;
		}
		Block block = player.getTargetBlockExact(5);
		if (block.getType() != Material.CHEST) {
			this.plugin.getMessages().getChatMessage("not-looking-at-chest")
				.color()
				.target(player)
				.send();
			return true;
		}
		this.plugin.convertEnchantments(player, ((Chest) block.getState()).getInventory());
		return true;
	}
}
