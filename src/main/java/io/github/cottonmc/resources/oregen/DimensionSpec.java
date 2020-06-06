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

package io.github.cottonmc.resources.oregen;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import com.google.common.collect.ImmutableSet;
import blue.endless.jankson.JsonElement;
import io.github.cottonmc.resources.CottonResources;
import io.github.cottonmc.resources.tag.DimensionTypeTags;
import org.spongepowered.asm.util.PrettyPrinter;

import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;

public class DimensionSpec extends TaggableSpec<DimensionType> {
	public DimensionSpec allowTag(Identifier tag) {
		allow.addAll(resolveTag(tag));
		return this;
	}

	public DimensionSpec denyTag(Identifier tag) {
		deny.addAll(resolveTag(tag));
		return this;
	}

	@Override
	public boolean test(DimensionType dim) {
		return true; // FIXME: Reimplement me! - Force for now

		//Identifier id = DimensionType.getId(dim.getType());

		//if (deny.contains(id)) return false; //nulls (unregistered dims) will always pass this test

		//if (allow.isEmpty() || allow.contains(ANY)) return true; //'*' will accept nulls

		//return allow.contains(id); //null-id dimensions will always fail this check
	}

	/**
	 * Can't be directly used as a TypeAdapter because of array polymorphism, but can be used by a parent TypeAdapter.
	 */
	public static DimensionSpec deserialize(JsonElement elem) {
		return TaggableSpec.deserialize(new DimensionSpec(), elem, DimensionSpec::resolveTag);
	}

	public static Set<Identifier> resolveTag(Identifier tagName) {
		new PrettyPrinter(80)
				.add("Warning, Dimension Tags have been temporary disabled due to 1.16 snapshot changes.")
				.hr()
				.add("Affected tag: " + tagName)
				.log(CottonResources.LOGGER);
		if (true) {
			return Collections.emptySet(); // FIXME Reimplement
		}

		TagContainer<DimensionType> tagContainer = DimensionTypeTags.getContainer();
		Tag<DimensionType> tag = tagContainer.get(tagName);

		if (tag == null) return ImmutableSet.of();

		HashSet<Identifier> result = new HashSet<>();

		for (DimensionType dimension : tag.values()) {
			// FIXME Reimplement
			//Identifier id = Registry.DIMENSION_TYPE.getId(dimension);

			//if (id != null) result.add(id);
		}

		return result;
	}
}
