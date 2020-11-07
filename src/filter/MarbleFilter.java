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

import java.awt.image.*;

/**
 * This filter applies a marbling effect to an filter, displacing pixels by random amounts.
 */
public class MarbleFilter extends TransformFilter implements Filter {

    private float[] sinTable, cosTable;

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     */
    public MarbleFilter() {
        setEdgeAction(CLAMP);
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     */
    private void initialize() {
        sinTable = new float[256];
        cosTable = new float[256];
        for (int i = 0; i < 256; i++) {
            float turbulence = 1;
            float angle = ImageMath.TWO_PI * i / 256f * turbulence;
            float yScale = 4;
            sinTable[i] = (float) (-yScale * Math.sin(angle));
            cosTable[i] = (float) (yScale * Math.cos(angle));
        }
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param x
     * @param y
     * @return
     */
    private int displacementMap(int x, int y) {
        float xScale = 4;
        return PixelUtils.clamp((int) (127 * (1 + Noise.noise2(x / xScale, y / xScale))));
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param x   the X position of the pixel in the output filter
     * @param y   the Y position of the pixel in the output filter
     * @param out the position of the pixel in the input filter
     */
    protected void transformInverse(int x, int y, float[] out) {
        int displacement = displacementMap(x, y);
        out[0] = x + sinTable[displacement];
        out[1] = y + cosTable[displacement];
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param src An original BufferedImage without filter effect.
     * @param dst Usually null at the beginning.
     *            At the end, dst is processed to be an BufferedImage with the filter effect.
     * @return
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        initialize();
        return super.filter(src, dst);
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
      * @return
     */
    public String toString() {
        return "Distort/Marble...";
    }
}
