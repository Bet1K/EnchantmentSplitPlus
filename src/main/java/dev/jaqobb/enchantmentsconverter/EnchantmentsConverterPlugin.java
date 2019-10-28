package dev.jaqobb.enchantmentsconverter;

import com.bgsoftware.wildchests.api.WildChestsAPI;
import com.bgsoftware.wildchests.api.objects.chests.Chest;
import com.bgsoftware.wildchests.api.objects.chests.StorageChest;
import dev.jaqobb.enchantmentsconverter.command.ConvertEnchantmentsCommand;
import dev.jaqobb.enchantmentsconverter.command.SortEnchantmentsCommand;
import dev.jaqobb.enchantmentsconverter.configuration.message.Messages;
import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantmentsConverterPlugin extends JavaPlugin {

	private Messages messages;

	@Override
	public void onEnable() {
		this.messages = new Messages(this, 1);
		this.getCommand("convertenchantments").setExecutor(new ConvertEnchantmentsCommand(this));
		this.getCommand("sortenchantments").setExecutor(new SortEnchantmentsCommand(this));
	}

	public Messages getMessages() {
		return this.messages;
	}

	public void convertEnchantments(Player player, Inventory inventory) {
		boolean successful = true;
		int freeSpaces = this.getFreeSpaces(inventory);
		for (int index = 0; index < inventory.getSize(); index++) {
			ItemStack item = inventory.getItem(index);
			if (item == null) {
				continue;
			}
			Map<Enchantment, Integer> itemEnchantments = item.getEnchantments();
			if (item.getType() == Material.ENCHANTED_BOOK) {
				itemEnchantments = ((EnchantmentStorageMeta) item.getItemMeta()).getStoredEnchants();
			} else if (itemEnchantments.isEmpty()) {
				continue;
			}
			if (freeSpaces + 1 < itemEnchantments.size()) {
				successful = false;
				continue;
			}
			inventory.setItem(index, null);
			freeSpaces++;
			for (Entry<Enchantment, Integer> itemEnchantment : itemEnchantments.entrySet()) {
				ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
				EnchantmentStorageMeta enchantedBookMeta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
				enchantedBookMeta.addStoredEnchant(itemEnchantment.getKey(), itemEnchantment.getValue(), true);
				enchantedBook.setItemMeta(enchantedBookMeta);
				inventory.addItem(enchantedBook);
				freeSpaces--;
			}
		}
		if (successful) {
			this.messages.getChatMessage("enchantments-converting-successful")
				.color()
				.target(player)
				.send();
		} else {
			this.messages.getChatMessage("enchantments-converting-not-successful")
				.color()
				.target(player)
				.send();
		}
	}

	public void sortEnchantments(Player player, Inventory inventory) {
		boolean successful = true;
		inventory:
		for (int index = 0; index < inventory.getSize(); index++) {
			ItemStack item = inventory.getItem(index);
			if (item == null) {
				continue;
			}
			if (item.getType() != Material.ENCHANTED_BOOK) {
				continue;
			}
			EnchantmentStorageMeta itemMeta = (EnchantmentStorageMeta) item.getItemMeta();
			if (itemMeta.getStoredEnchants().size() != 1) {
				continue;
			}
			for (Chest chest : WildChestsAPI.getInstance().getChestsManager().getChests()) {
				if (chest instanceof StorageChest) {
					StorageChest storageChest = (StorageChest) chest;
					if (storageChest.getItemStack().isSimilar(item)) {
						storageChest.setAmount(storageChest.getExactAmount().add(BigInteger.ONE));
						inventory.setItem(index, null);
						continue inventory;
					}
				}
			}
			successful = false;
		}
		if (successful) {
			this.messages.getChatMessage("enchantments-sorting-successful")
				.color()
				.target(player)
				.send();
		} else {
			this.messages.getChatMessage("enchantments-sorting-not-successful")
				.color()
				.target(player)
				.send();
		}
	}

	private int getFreeSpaces(Inventory inventory) {
		int freeSpaces = 0;
		for (ItemStack item : inventory) {
			if (item == null) {
				freeSpaces++;
			}
		}
		return freeSpaces;
	}
}
