package dev.jaqobb.enchantmentsconverter.configuration.message.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class TitleMessage {

	private String title;
	private String subtitle;
	private int fadeIn;
	private int stay;
	private int fadeOut;
	private Collection<? extends Player> targets = new ArrayList<>(0);

	public TitleMessage(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
	}

	public TitleMessage colorTitle() {
		return this.transformTitle(message -> ChatColor.translateAlternateColorCodes('&', message));
	}

	public TitleMessage transformTitle(Function<String, String> transformer) {
		this.title = transformer.apply(this.title);
		return this;
	}

	public TitleMessage colorSubtitle() {
		return this.transformSubtitle(message -> ChatColor.translateAlternateColorCodes('&', message));
	}

	public TitleMessage transformSubtitle(Function<String, String> transformer) {
		this.subtitle = transformer.apply(this.subtitle);
		return this;
	}

	public TitleMessage target(Player target) {
		this.targets = Arrays.asList(target);
		return this;
	}

	public TitleMessage targets(Collection<? extends Player> targets) {
		this.targets = targets;
		return this;
	}

	public void send() {
		this.targets.forEach(target -> target.sendTitle(this.title, this.subtitle, this.fadeIn, this.stay, this.fadeOut));
	}
}
