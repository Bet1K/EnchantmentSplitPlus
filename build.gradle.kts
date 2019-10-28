import net.mcparkour.migle.attributes.ApiVersion
import net.mcparkour.migle.attributes.Command

plugins {
	`java-library`
	id("net.mcparkour.migle.migle-bukkit") version "1.1.0"
}

group = "dev.jaqobb"
version = "1.0.0"
description = "Minecraft plugin that allows to convert enchanted items into enchanted books"

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

defaultTasks("clean", "build")

migleBukkit {
	var newName = ""
	for (newNamePart in project.name.split("-")) {
		var first = true
		for (newNamePartCharacter in newNamePart.toCharArray()) {
			if (first) {
				first = false
				newName += newNamePartCharacter.toUpperCase()
			} else {
				newName += newNamePartCharacter.toLowerCase()
			}
		}
	}
	name = newName
	main = "${project.group}.${project.name.toLowerCase().replace("-", "")}.${newName}Plugin"
	version = project.version as String
	apiVersion = ApiVersion.VERSION_1_14
	depend = listOf("WildChests")
	description = project.description
	author = "jaqobb"
	website = "https://jaqobb.dev"
	commands = mapOf(
		"convertenchantments" to Command(
			description = "Converts all enchanted items inside a chest into enchanted books",
			aliases = listOf("disenchant")
		),
		"sortenchantments" to Command(
			description = "Sends all possible enchanted books inside a chest to their respective storage units"
		)
	)
}

repositories {
	jcenter()
	maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
		content {
			includeGroup("org.spigotmc")
		}
	}
	maven("https://oss.sonatype.org/content/repositories/snapshots/") {
		content {
			includeGroup("net.md-5")
		}
	}
	maven("https://jitpack.io/") {
		content {
			includeGroup("com.github.OmerBenGera")
		}
	}
}

dependencies {
	compileOnly("org.spigotmc:spigot-api:1.14.4-R0.1-SNAPSHOT")
	compileOnly("com.github.OmerBenGera:WildChestsAPI:b2")
}
