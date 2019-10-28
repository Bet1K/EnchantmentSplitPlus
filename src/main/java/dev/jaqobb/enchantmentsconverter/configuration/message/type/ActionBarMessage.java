package dev.jaqobb.enchantmentsconverter.configuration.message.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarMessage {

	private String message;
	private Collection<? extends Player> targets = new ArrayList<>(0);

	public ActionBarMessage(String message) {
		this.message = message;
	}

	public ActionBarMessage color() {
		return this.transform(message -> ChatColor.translateAlternateColorCodes('&', message));
	}

	public ActionBarMessage transform(Function<String, String> transformer) {
		this.message = transformer.apply(this.message);
		return this;
	}

	public ActionBarMessage target(Player target) {
		this.targets = Arrays.asList(target);
		return this;
	}

	public ActionBarMessage targets(Collection<? extends Player> targets) {
		this.targets = targets;
		return this;
	}

	public void send() {
		this.targets.forEach(target -> target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(this.message)));
	}
}
