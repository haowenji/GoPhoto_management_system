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

import java.awt.Color;

/**
 * A Colormap implemented using Catmull-Rom colour splines. The map has a variable number
 * of knots with a minimum of four. The first and last knots give the tangent at the end
 * of the spline, and colours are interpolated from the second to the second-last knots.
 * Each knot can be given a type of interpolation. These are:
 * <UL>
 * <LI>LINEAR - linear interpolation to next knot
 * <LI>SPLINE - spline interpolation to next knot
 * <LI>CONSTANT - no interpolation - the colour is constant to the next knot
 * <LI>HUE_CW - interpolation of hue clockwise to next knot
 * <LI>HUE_CCW - interpolation of hue counter-clockwise to next knot
 * </UL>
 */
public class Gradient extends ArrayColormap implements Cloneable {

    /**
     * Interpolate in RGB space.
     */
    private final static int RGB = 0x00;

    /**
     * Interpolate hue clockwise.
     */
    private final static int HUE_CW = 0x01;

    /**
     * Interpolate hue counter clockwise.
     */
    private final static int HUE_CCW = 0x02;


    /**
     * Interpolate linearly.
     */
    private final static int LINEAR = 0x10;

    /**
     * Interpolate using a spline.
     */
    private final static int SPLINE = 0x20;

    /**
     * Interpolate with a rising circle shape curve.
     */
    private final static int CIRCLE_UP = 0x30;

    /**
     * Interpolate with a falling circle shape curve.
     */
    private final static int CIRCLE_DOWN = 0x40;

    /**
     * Don't tnterpolate - just use the starting value.
     */
    private final static int CONSTANT = 0x50;

    private final static int COLOR_MASK = 0x03;
    private final static int BLEND_MASK = 0x70;

    private int numKnots = 4;
    private int[] xKnots = {
            -1, 0, 255, 256
    };
    private int[] yKnots = {
            0xff000000, 0xff000000, 0xffffffff, 0xffffffff,
    };
    private byte[] knotTypes = {
            SPLINE, SPLINE, SPLINE, SPLINE
    };

    /**
     * Construct a Gradient.
     */
    public Gradient() {
        rebuildGradient();
    }

    /**
     *
     * @return
     */
    public Object clone() {
        Gradient g = (Gradient) super.clone();
        g.map = map.clone();
        g.xKnots = xKnots.clone();
        g.yKnots = yKnots.clone();
        g.knotTypes = knotTypes.clone();
        return g;
    }

    /**
     * Get a knot type.
     *
     * @param n the knot index
     * @return the knot type
     */
    public int getKnotType(int n) {
        return (byte) (knotTypes[n] & COLOR_MASK);
    }

    /**
     * Get a knot blend type.
     *
     * @param n the knot index
     * @return the knot blend type
     */
    public byte getKnotBlend(int n) {
        return (byte) (knotTypes[n] & BLEND_MASK);
    }

    /**
     * Add a new knot.
     *
     * @param x     the knot position
     * @param color the color
     * @param type  the knot type
     */
    public void addKnot(int x, int color, int type) {
        int[] nx = new int[numKnots + 1];
        int[] ny = new int[numKnots + 1];
        byte[] nt = new byte[numKnots + 1];
        System.arraycopy(xKnots, 0, nx, 0, numKnots);
        System.arraycopy(yKnots, 0, ny, 0, numKnots);
        System.arraycopy(knotTypes, 0, nt, 0, numKnots);
        xKnots = nx;
        yKnots = ny;
        knotTypes = nt;
        // Insert one position before the end so the sort works correctly
        xKnots[numKnots] = xKnots[numKnots - 1];
        yKnots[numKnots] = yKnots[numKnots - 1];
        knotTypes[numKnots] = knotTypes[numKnots - 1];
        xKnots[numKnots - 1] = x;
        yKnots[numKnots - 1] = color;
        knotTypes[numKnots - 1] = (byte) type;
        numKnots++;
        sortKnots();
        rebuildGradient();
    }

    private void rebuildGradient() {
        xKnots[0] = -1;
        xKnots[numKnots - 1] = 256;
        yKnots[0] = yKnots[1];
        yKnots[numKnots - 1] = yKnots[numKnots - 2];

        int knot = 0;
        for (int i = 1; i < numKnots - 1; i++) {
            float spanLength = xKnots[i + 1] - xKnots[i];
            int end = xKnots[i + 1];
            if (i == numKnots - 2)
                end++;
            for (int j = xKnots[i]; j < end; j++) {
                int rgb1 = yKnots[i];
                int rgb2 = yKnots[i + 1];
                float hsb1[] = Color.RGBtoHSB((rgb1 >> 16) & 0xff, (rgb1 >> 8) & 0xff, rgb1 & 0xff, null);
                float hsb2[] = Color.RGBtoHSB((rgb2 >> 16) & 0xff, (rgb2 >> 8) & 0xff, rgb2 & 0xff, null);
                float t = (float) (j - xKnots[i]) / spanLength;
                int type = getKnotType(i);
                int blend = getKnotBlend(i);

                if (j >= 0 && j <= 255) {
                    switch (blend) {
                        case CONSTANT:
                            t = 0;
                            break;
                        case LINEAR:
                            break;
                        case SPLINE:
                            t = ImageMath.smoothStep(0.15f, 0.85f, t);
                            break;
                        case CIRCLE_UP:
                            t = t - 1;
                            t = (float) Math.sqrt(1 - t * t);
                            break;
                        case CIRCLE_DOWN:
                            t = 1 - (float) Math.sqrt(1 - t * t);
                            break;
                    }
                    switch (type) {
                        case RGB:
                            map[j] = ImageMath.mixColors(t, rgb1, rgb2);
                            break;
                        case HUE_CW:
                        case HUE_CCW:
                            if (type == HUE_CW) {
                                if (hsb2[0] <= hsb1[0])
                                    hsb2[0] += 1.0f;
                            } else {
                                if (hsb1[0] <= hsb2[1])
                                    hsb1[0] += 1.0f;
                            }
                            float h = ImageMath.lerp(t, hsb1[0], hsb2[0]) % (ImageMath.TWO_PI);
                            float s = ImageMath.lerp(t, hsb1[1], hsb2[1]);
                            float b = ImageMath.lerp(t, hsb1[2], hsb2[2]);
                            map[j] = 0xff000000 | Color.HSBtoRGB(h, s, b);//FIXME-alpha
                            break;
                    }
                }
            }
        }
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     */
    private void sortKnots() {
        for (int i = 1; i < numKnots - 1; i++) {
            for (int j = 1; j < i; j++) {
                if (xKnots[i] < xKnots[j]) {
                    int t = xKnots[i];
                    xKnots[i] = xKnots[j];
                    xKnots[j] = t;
                    t = yKnots[i];
                    yKnots[i] = yKnots[j];
                    yKnots[j] = t;
                    byte bt = knotTypes[i];
                    knotTypes[i] = knotTypes[j];
                    knotTypes[j] = bt;
                }
            }
        }
    }
}
