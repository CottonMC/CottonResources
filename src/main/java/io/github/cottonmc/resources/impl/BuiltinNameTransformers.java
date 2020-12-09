/*
 * Copyright 2020 The Cotton Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.cottonmc.resources.impl;

import io.github.cottonmc.resources.api.NameTransformer;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BuiltinNameTransformers {
    public static final NameTransformer NO_CHANGE = (Identifier id) -> id;
    public static final NameTransformer INGOT = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_ingot"));
    public static final NameTransformer ORE = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_ore"));
    public static final NameTransformer STORAGE_BLOCK = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_block"));
    public static final NameTransformer NUGGET = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_nugget"));
    public static final NameTransformer DUST = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_dust"));
    public static final NameTransformer END_ORE = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_end_ore"));
    public static final NameTransformer NETHER_ORE = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_nether_ore"));
    public static final NameTransformer GEAR = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_gear"));
    public static final NameTransformer PLATE = (Identifier id) ->
            new Identifier(id.getNamespace(), id.getPath().concat("_plate"));
}
