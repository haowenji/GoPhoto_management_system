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
public class AxisAngle4f {
    public float x, y, z, angle;

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param x
     * @param y
     * @param z
     * @param angle
     */
    public void set(float x, float y, float z, float angle) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.angle = angle;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param t
     */
    public void set(AxisAngle4f t) {
        x = t.x;
        y = t.y;
        z = t.z;
        angle = t.angle;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param t
     */
    public void get(AxisAngle4f t) {
        t.x = x;
        t.y = y;
        t.z = z;
        t.angle = angle;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param t
     */
    public void get(float[] t) {
        t[0] = x;
        t[1] = y;
        t[2] = z;
        t[3] = angle;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @return
     */
    public String toString() {
        return "[" + x + ", " + y + ", " + z + ", " + angle + "]";
    }

}
