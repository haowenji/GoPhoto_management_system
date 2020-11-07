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
public class Quat4f extends Tuple4f {

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param x
     * @param y
     * @param z
     * @param w
     */
    Quat4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param a
     */
    public void set(AxisAngle4f a) {
        float halfTheta = a.angle * 0.5f;
        float cosHalfTheta = (float) Math.cos(halfTheta);
        float sinHalfTheta = (float) Math.sin(halfTheta);
        x = a.x * sinHalfTheta;
        y = a.y * sinHalfTheta;
        z = a.z * sinHalfTheta;
        w = cosHalfTheta;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param m
     */
    public void set(Matrix4f m) {
        float s;
        int i;

        float tr = m.m00 + m.m11 + m.m22;

        if (tr > 0.0) {
            s = (float) Math.sqrt(tr + 1.0f);
            w = s / 2.0f;
            s = 0.5f / s;
            x = (m.m12 - m.m21) * s;
            y = (m.m20 - m.m02) * s;
            z = (m.m01 - m.m10) * s;
        } else {
            i = 0;
            if (m.m11 > m.m00) {
                i = 1;
                if (m.m22 > m.m11) {
                    i = 2;
                }
            } else {
                if (m.m22 > m.m00) {
                    i = 2;
                }
            }

            switch (i) {
                case 0:
                    s = (float) Math.sqrt((m.m00 - (m.m11 + m.m22)) + 1.0f);
                    x = s * 0.5f;
                    if (s != 0.0)
                        s = 0.5f / s;
                    w = (m.m12 - m.m21) * s;
                    y = (m.m01 + m.m10) * s;
                    z = (m.m02 + m.m20) * s;
                    break;
                case 1:
                    s = (float) Math.sqrt((m.m11 - (m.m22 + m.m00)) + 1.0f);
                    y = s * 0.5f;
                    if (s != 0.0)
                        s = 0.5f / s;
                    w = (m.m20 - m.m02) * s;
                    z = (m.m12 + m.m21) * s;
                    x = (m.m10 + m.m01) * s;
                    break;
                case 2:
                    s = (float) Math.sqrt((m.m00 - (m.m11 + m.m22)) + 1.0f);
                    z = s * 0.5f;
                    if (s != 0.0)
                        s = 0.5f / s;
                    w = (m.m01 - m.m10) * s;
                    x = (m.m20 + m.m02) * s;
                    y = (m.m21 + m.m12) * s;
                    break;
            }

        }
    }

}
