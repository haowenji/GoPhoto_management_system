/*
Copyright 2006 Jerry Huxtable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package filter;

/**
 * A colormap implemented with an array of colors. This corresponds to the IndexColorModel class.
 */
public class ArrayColormap implements Colormap, Cloneable {

    /**
     * The array of colors.
     */
    protected int[] map;

    /**
     * Construct an all-black colormap.
     */
    public ArrayColormap() {
        this.map = new int[256];
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @return
     */
    public Object clone() {
        try {
            ArrayColormap g = (ArrayColormap) super.clone();
            g.map = (int[]) map.clone();
            return g;
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }

    /**
     * Convert a value in the range 0..1 to an RGB color.
     *
     * @param v a value in the range 0..1
     * @return an RGB color
     */
    public int getColor(float v) {
        int n = (int) (v * 255);
        if (n < 0)
            n = 0;
        else if (n > 255)
            n = 255;
        return map[n];
    }
}
