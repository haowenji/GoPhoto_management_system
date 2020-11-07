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
 * A filter which produces a rubber-stamp type of effect by performing a thresholded blur.
 */
public class StampFilter extends PointFilter implements Filter {

    private float threshold;
    private float lowerThreshold3;
    private float upperThreshold3;


    /**
     * Construct a StampFilter.
     */
    public StampFilter() {
        this(0.5f);
    }

    /**
     * Construct a StampFilter.
     *
     * @param threshold the threshold value
     */
    private StampFilter(float threshold) {
        setThreshold(threshold);
    }

    /**
     * Set the threshold value.
     *
     * @param threshold the threshold value
     */
    private void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        float softness = 0;
        float radius = 5;
        dst = new GaussianFilter((int) radius).filter(src, null);
        lowerThreshold3 = 255 * 3 * (threshold - softness * 0.5f);
        upperThreshold3 = 255 * 3 * (threshold + softness * 0.5f);
        return super.filter(dst, dst);
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param x
     * @param y
     * @param rgb
     * @return
     */
    public int filterRGB(int x, int y, int rgb) {
        int white = 0xffffffff;
        int black = 0xff000000;
        int a = rgb & 0xff000000;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        int l = r + g + b;
        float f = ImageMath.smoothStep(lowerThreshold3, upperThreshold3, l);
        return ImageMath.mixColors(f, black, white);
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @return
     */
    public String toString() {
        return "Stylize/Stamp...";
    }
}
