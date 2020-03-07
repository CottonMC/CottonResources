/*
 * MIT License
 *
 * Copyright (c) 2018-2020 The Cotton Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.cottonmc.resources;

import io.github.cottonmc.resources.type.GemResourceType;
import io.github.cottonmc.resources.type.MetalResourceType;
import io.github.cottonmc.resources.type.RadioactiveResourceType;
import io.github.cottonmc.resources.type.ResourceType;

public final class ResourceTemplates {
	/**
	 * Creates a template MetalResourceType builder.
	 *
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
	 *
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
	 *
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
	 *
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
