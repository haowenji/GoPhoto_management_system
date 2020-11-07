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
 * Vector math package, converted to look similar to javax.vecmath.
 */
public class Tuple4f {
    float x, y, z, w;

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     */
    Tuple4f() {
        this(0, 0, 0, 0);
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param x
     * @param y
     * @param z
     * @param w
     */
    private Tuple4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param min
     * @param max
     */
    public void clamp(float min, float max) {
        if (x < min)
            x = min;
        else if (x > max)
            x = max;
        if (y < min)
            y = min;
        else if (y > max)
            y = max;
        if (z < min)
            z = min;
        else if (z > max)
            z = max;
        if (w < min)
            w = min;
        else if (w > max)
            w = max;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param x
     * @param y
     * @param z
     * @param w
     */
    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param x
     */
    public void set(float[] x) {
        this.x = x[0];
        this.y = x[1];
        this.z = x[2];
        this.w = x[2];
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param t
     */
    public void set(Tuple4f t) {
        x = t.x;
        y = t.y;
        z = t.z;
        w = t.w;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param t
     */
    public void get(Tuple4f t) {
        t.x = x;
        t.y = y;
        t.z = z;
        t.w = w;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param t
     */
    public void get(float[] t) {
        t[0] = x;
        t[1] = y;
        t[2] = z;
        t[3] = w;
    }


    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param t
     */
    public void add(Tuple4f t) {
        x += t.x;
        y += t.y;
        z += t.z;
        w += t.w;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param t1
     * @param t2
     */
    public void add(Tuple4f t1, Tuple4f t2) {
        x = t1.x + t2.x;
        y = t1.y + t2.y;
        z = t1.z + t2.z;
        w = t1.w + t2.w;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @return
     */
    public String toString() {
        return "[" + x + ", " + y + ", " + z + ", " + w + "]";
    }

}
