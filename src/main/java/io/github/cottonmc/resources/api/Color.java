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
package io.github.cottonmc.resources.api;

import com.sun.istack.internal.Nullable;

public final class Color {
    public static Color fromRgb(int color) {
        final int red = color & 0xFF;
        final int green = color >> 8 & 0xFF;
        final int blue = color >> 16 & 0xFF;
        final int alpha = color >> 24 & 0xFF;

        return new Color(red, green, blue);
    }

    private final int red;
    private final int green;
    private final int blue;

    private Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public int getColor() {
        return this.red & 0xFF | (this.green & 0xFF) << 8 | (this.blue & 0xFF) << 16;
    }

    @Override
    public String toString() {
        return "Color{red=" + this.getRed() +
                "blue=" + this.getBlue() +
                "green=" + this.getGreen() + "}";
    }
    @Nullable
    public static Color parseHex(String name) {
        try {
            int i = Integer.parseInt(name.substring(1), 16);
            return fromRgb(i);
        } catch (NumberFormatException var2) {
            return null;
        }
    }
}
