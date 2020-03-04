package io.github.cottonmc.resources;

import io.github.cottonmc.resources.type.GemResourceType;
import io.github.cottonmc.resources.type.MetalResourceType;
import io.github.cottonmc.resources.type.RadioactiveResourceType;
import io.github.cottonmc.resources.type.ResourceType;

public final class ResourceTemplates {
	/**
	 * Creates a template MetalResourceType builder.
	 * @param resourceName The name of the resource.
	 * @return a new metal resource.
	 */
	public static MetalResourceType.Builder fullMetalType(String resourceName) {
		return ResourceType.builder(resourceName)
			.metal()
			.allOres()
			.withMachineAffixes()
			.withItemAffixes();
	}

	/**
	 * Creates a template MetalResourceType builder.
	 * @param resourceName The name of the resource.
	 * @return a new metal resource.
	 */
	public static MetalResourceType.Builder metalTypeNoOre(String resourceName) {
		return ResourceType.builder(resourceName)
			.metal()
			.withMachineAffixes()
			.withItemAffixes();
	}

	/**
	 * Creates a template GemResourceType builder.
	 * @param resourceName The name of the resource.
	 * @return a new gem resource.
	 */
	public static GemResourceType.Builder fullGemType(String resourceName) {
		return ResourceType.builder(resourceName)
			.gem()
			.allOres()
			.withMachineAffixes()
			.withDustAffix()
			.withGemAffix();
	}

	/**
	 * Creates a template RadioactiveResourceType along with ores.
	 * @param resourceName The name of the resource.
	 * @return a new gem resource.
	 */
	public static RadioactiveResourceType.Builder radioactiveTypeNoOre(String resourceName) {
		return ResourceType.builder(resourceName)
			.radioactive()
			.withMachineAffixes()
			.withItemAffixes();
	}
}
