package dev.jaqobb.enchantmentsconverter.configuration.message;

import dev.jaqobb.enchantmentsconverter.configuration.Configuration;
import dev.jaqobb.enchantmentsconverter.configuration.message.type.ActionBarMessage;
import dev.jaqobb.enchantmentsconverter.configuration.message.type.ChatMessage;
import dev.jaqobb.enchantmentsconverter.configuration.message.type.TitleMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages extends Configuration {

	public Messages(JavaPlugin plugin) {
		super(plugin, "messages");
	}

	public Messages(JavaPlugin plugin, int currentVersion) {
		super(plugin, currentVersion, "messages");
	}

	public Messages(JavaPlugin plugin, String fileName) {
		super(plugin, fileName);
	}

	public Messages(JavaPlugin plugin, int currentVersion, String fileName) {
		super(plugin, currentVersion, fileName);
	}

	public ChatMessage getChatMessage(String path) {
		if (!this.isSet(path)) {
			return new ChatMessage("Could not find anything at the provided path (" + path + ").");
		}
		if (this.isString(path)) {
			return new ChatMessage(this.getString(path));
		} else if (this.isList(path)) {
			return new ChatMessage(this.getStringList(path));
		} else {
			return new ChatMessage("Value at the provided path is not a string or a list of strings (" + path + ").");
		}
	}

	public ActionBarMessage getActionBarMessage(String path) {
		if (!this.isSet(path)) {
			return new ActionBarMessage("Could not find anything at the provided path (" + path + ").");
		}
		if (!this.isString(path)) {
			return new ActionBarMessage("Value at the provided path is not a string (" + path + ").");
		}
		return new ActionBarMessage(this.getString(path));
	}

	public TitleMessage getTitleMessage(String titlePath, String subtitlePath, String fadeInPath, String stayPath, String fadeOutPath) {
		if (!this.isSet(titlePath)) {
			return new TitleMessage("Could not find anything at the provided path (" + titlePath + ").", "", 10, 70, 20);
		}
		if (!this.isString(titlePath)) {
			return new TitleMessage("Value at the provided path has to be a string (" + titlePath + ").", "", 10, 70, 20);
		}
		if (!this.isSet(subtitlePath)) {
			return new TitleMessage("Could not find anything at the provided path (" + subtitlePath + ").", "", 10, 70, 20);
		}
		if (!this.isString(subtitlePath)) {
			return new TitleMessage("Value at the provided path has to be a string (" + subtitlePath + ").", "", 10, 70, 20);
		}
		if (!this.isSet(fadeInPath)) {
			return new TitleMessage("Could not find anything at the provided path (" + fadeInPath + ").", "", 10, 70, 20);
		}
		if (!this.isInt(fadeInPath)) {
			return new TitleMessage("Value at the provided path has to be an integer (" + fadeInPath + ").", "", 10, 70, 20);
		}
		if (!this.isSet(stayPath)) {
			return new TitleMessage("Could not find anything at the provided path (" + stayPath + ").", "", 10, 70, 20);
		}
		if (!this.isInt(stayPath)) {
			return new TitleMessage("Value at the provided path has to be an integer (" + stayPath + ").", "", 10, 70, 20);
		}
		if (!this.isSet(fadeOutPath)) {
			return new TitleMessage("Could not find anything at the provided path (" + fadeOutPath + ").", "", 10, 70, 20);
		}
		if (!this.isInt(fadeOutPath)) {
			return new TitleMessage("Value at the provided path has to be an integer (" + fadeOutPath + ").", "", 10, 70, 20);
		}
		return new TitleMessage(this.getString(titlePath), this.getString(subtitlePath), this.getInt(fadeInPath), this.getInt(stayPath), this.getInt(fadeOutPath));
	}
}
