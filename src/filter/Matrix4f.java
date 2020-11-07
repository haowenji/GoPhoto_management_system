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
public class Matrix4f {
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param m
     */
    private Matrix4f(Matrix4f m) {
        set(m);
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param m
     */
    public void set(Matrix4f m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m03 = m.m03;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m13 = m.m13;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        m23 = m.m23;
        m30 = m.m30;
        m31 = m.m31;
        m32 = m.m32;
        m33 = m.m33;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param m
     */
    public void set(float[] m) {
        m00 = m[0];
        m01 = m[1];
        m02 = m[2];
        m03 = m[3];
        m10 = m[4];
        m11 = m[5];
        m12 = m[6];
        m13 = m[7];
        m20 = m[8];
        m21 = m[9];
        m22 = m[10];
        m23 = m[11];
        m30 = m[12];
        m31 = m[13];
        m32 = m[14];
        m33 = m[15];
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param m
     */
    public void get(Matrix4f m) {
        m.m00 = m00;
        m.m01 = m01;
        m.m02 = m02;
        m.m03 = m03;
        m.m10 = m10;
        m.m11 = m11;
        m.m12 = m12;
        m.m13 = m13;
        m.m20 = m20;
        m.m21 = m21;
        m.m22 = m22;
        m.m23 = m23;
        m.m30 = m30;
        m.m31 = m31;
        m.m32 = m32;
        m.m33 = m33;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param m
     */
    public void get(float[] m) {
        m[0] = m00;
        m[1] = m01;
        m[2] = m02;
        m[3] = m03;
        m[4] = m10;
        m[5] = m11;
        m[6] = m12;
        m[7] = m13;
        m[8] = m20;
        m[9] = m21;
        m[10] = m22;
        m[11] = m23;
        m[12] = m30;
        m[13] = m31;
        m[14] = m32;
        m[15] = m33;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param a
     */
    public void set(AxisAngle4f a) {
        float halfTheta = a.angle * 0.5f;
        float cosHalfTheta = (float) Math.cos(halfTheta);
        float sinHalfTheta = (float) Math.sin(halfTheta);
        set(new Quat4f(a.x * sinHalfTheta, a.y * sinHalfTheta, a.z * sinHalfTheta, cosHalfTheta));
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param q
     */
    public void set(Quat4f q) {
        float wx, wy, wz, xx, yy, yz, xy, xz, zz, x2, y2, z2;
        x2 = q.x + q.x;
        y2 = q.y + q.y;
        z2 = q.z + q.z;
        xx = q.x * x2;
        xy = q.x * y2;
        xz = q.x * z2;
        yy = q.y * y2;
        yz = q.y * z2;
        zz = q.z * z2;
        wx = q.w * x2;
        wy = q.w * y2;
        wz = q.w * z2;
        m00 = 1.0f - (yy + zz);
        m01 = xy - wz;
        m02 = xz + wy;
        m03 = 0.0f;
        m10 = xy + wz;
        m11 = 1.0f - (xx + zz);
        m12 = yz - wx;
        m13 = 0.0f;
        m20 = xz - wy;
        m21 = yz + wx;
        m22 = 1.0f - (xx + yy);
        m23 = 0.0f;
        m30 = 0;
        m31 = 0;
        m32 = 0;
        m33 = 1;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param scale
     */
    public void set(float scale) {
        m00 = scale;
        m11 = scale;
        m22 = scale;
    }
}
