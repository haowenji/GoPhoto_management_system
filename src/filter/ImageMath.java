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
 * A class containing static math methods useful for filter processing.
 */
public class ImageMath {

    /**
     * The value of pi as a float.
     */
    final static float PI = (float) Math.PI;

    /**
     * The value of two pi as a float.
     */
    final static float TWO_PI = (float) Math.PI * 2.0f;

    /**
     * A smoothed step function. A cubic function is used to smooth the step between two thresholds.
     *
     * @param a the lower threshold position
     * @param b the upper threshold position
     * @param x the input parameter
     * @return the output value
     */
    public static float smoothStep(float a, float b, float x) {
        if (x < a)
            return 0;
        if (x >= b)
            return 1;
        x = (x - a) / (b - a);
        return x * x * (3 - 2 * x);
    }

    /**
     * Clamp a value to an interval.
     *
     * @param a the lower clamp threshold
     * @param b the upper clamp threshold
     * @param x the input parameter
     * @return the clamped value
     */
    public static float clamp(float x, float a, float b) {
        return (x < a) ? a : (x > b) ? b : x;
    }

    /**
     * Clamp a value to an interval.
     *
     * @param a the lower clamp threshold
     * @param b the upper clamp threshold
     * @param x the input parameter
     * @return the clamped value
     */
    public static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }

    /**
     * Return a mod b. This differs from the % operator with respect to negative numbers.
     *
     * @param a the dividend
     * @param b the divisor
     * @return a mod b
     */
    public static float mod(float a, float b) {
        int n = (int) (a / b);

        a -= n * b;
        if (a < 0)
            return a + b;
        return a;
    }

    /**
     * Return a mod b. This differs from the % operator with respect to negative numbers.
     *
     * @param a the dividend
     * @param b the divisor
     * @return a mod b
     */
    public static int mod(int a, int b) {
        int n = a / b;

        a -= n * b;
        if (a < 0)
            return a + b;
        return a;
    }

    /**
     * Linear interpolation.
     *
     * @param t the interpolation parameter
     * @param a the lower interpolation range
     * @param b the upper interpolation range
     * @return the interpolated value
     */
    static float lerp(float t, float a, float b) {
        return a + t * (b - a);
    }

    /**
     * Linear interpolation.
     *
     * @param t the interpolation parameter
     * @param a the lower interpolation range
     * @param b the upper interpolation range
     * @return the interpolated value
     */
    private static int lerp(float t, int a, int b) {
        return (int) (a + t * (b - a));
    }

    /**
     * Linear interpolation of ARGB values.
     *
     * @param t    the interpolation parameter
     * @param rgb1 the lower interpolation range
     * @param rgb2 the upper interpolation range
     * @return the interpolated value
     */
    static int mixColors(float t, int rgb1, int rgb2) {
        int a1 = (rgb1 >> 24) & 0xff;
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >> 8) & 0xff;
        int b1 = rgb1 & 0xff;
        int a2 = (rgb2 >> 24) & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >> 8) & 0xff;
        int b2 = rgb2 & 0xff;
        a1 = lerp(t, a1, a2);
        r1 = lerp(t, r1, r2);
        g1 = lerp(t, g1, g2);
        b1 = lerp(t, b1, b2);
        return (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
    }

    /**
     * Bilinear interpolation of ARGB values.
     *
     * @param x the X interpolation parameter 0..1
     * @param y the y interpolation parameter 0..1
     * @return the interpolated value
     */
    static int bilinearInterpolate(float x, float y, int nw, int ne, int sw, int se) {
        float m0, m1;
        int a0 = (nw >> 24) & 0xff;
        int r0 = (nw >> 16) & 0xff;
        int g0 = (nw >> 8) & 0xff;
        int b0 = nw & 0xff;
        int a1 = (ne >> 24) & 0xff;
        int r1 = (ne >> 16) & 0xff;
        int g1 = (ne >> 8) & 0xff;
        int b1 = ne & 0xff;
        int a2 = (sw >> 24) & 0xff;
        int r2 = (sw >> 16) & 0xff;
        int g2 = (sw >> 8) & 0xff;
        int b2 = sw & 0xff;
        int a3 = (se >> 24) & 0xff;
        int r3 = (se >> 16) & 0xff;
        int g3 = (se >> 8) & 0xff;
        int b3 = se & 0xff;

        float cx = 1.0f - x;
        float cy = 1.0f - y;

        m0 = cx * a0 + x * a1;
        m1 = cx * a2 + x * a3;
        int a = (int) (cy * m0 + y * m1);

        m0 = cx * r0 + x * r1;
        m1 = cx * r2 + x * r3;
        int r = (int) (cy * m0 + y * m1);

        m0 = cx * g0 + x * g1;
        m1 = cx * g2 + x * g3;
        int g = (int) (cy * m0 + y * m1);

        m0 = cx * b0 + x * b1;
        m1 = cx * b2 + x * b3;
        int b = (int) (cy * m0 + y * m1);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Premultiply a block of pixels
     */
    static void premultiply(int[] p, int length) {
        length += 0;
        for (int i = 0; i < length; i++) {
            int rgb = p[i];
            int a = (rgb >> 24) & 0xff;
            int r = (rgb >> 16) & 0xff;
            int g = (rgb >> 8) & 0xff;
            int b = rgb & 0xff;
            float f = a * (1.0f / 255.0f);
            r *= f;
            g *= f;
            b *= f;
            p[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
    }

    /**
     * Premultiply a block of pixels
     */
    static void unpremultiply(int[] p, int length) {
        length += 0;
        for (int i = 0; i < length; i++) {
            int rgb = p[i];
            int a = (rgb >> 24) & 0xff;
            int r = (rgb >> 16) & 0xff;
            int g = (rgb >> 8) & 0xff;
            int b = rgb & 0xff;
            if (a != 0 && a != 255) {
                float f = 255.0f / a;
                r *= f;
                g *= f;
                b *= f;
                if (r > 255)
                    r = 255;
                if (g > 255)
                    g = 255;
                if (b > 255)
                    b = 255;
                p[i] = (a << 24) | (r << 16) | (g << 8) | b;
            }
        }
    }
}
