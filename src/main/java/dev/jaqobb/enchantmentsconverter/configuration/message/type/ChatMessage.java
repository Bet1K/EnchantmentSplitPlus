package dev.jaqobb.enchantmentsconverter.configuration.message.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatMessage {

	private List<String> messages;
	private Collection<? extends CommandSender> targets = new ArrayList<>(0);

	public ChatMessage(String message) {
		this.messages = Arrays.asList(message);
	}

	public ChatMessage(List<String> messages) {
		this.messages = new ArrayList<>(messages);
	}

	public ChatMessage color() {
		return this.transform(message -> ChatColor.translateAlternateColorCodes('&', message));
	}

	public ChatMessage transform(Function<String, String> transformer) {
		this.messages = this.messages.stream()
			.map(transformer)
			.collect(Collectors.toList());
		return this;
	}

	public ChatMessage target(CommandSender target) {
		this.targets = Arrays.asList(target);
		return this;
	}

	public ChatMessage targets(Collection<? extends CommandSender> targets) {
		this.targets = targets;
		return this;
	}

	public void send() {
		this.targets.forEach(target -> {
			this.messages.forEach(message -> {
				target.sendMessage(message);
			});
		});
	}
}
