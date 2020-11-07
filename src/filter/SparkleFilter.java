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

import java.util.*;

/**
 * A filter to perform sparkle effect on an filter.
 */
public class SparkleFilter extends PointFilter implements Filter {

    private int rays = 50;
    private int radius = 25;
    private int width, height;
    private int centreX, centreY;
    private float[] rayLengths;
    private Random randomNumbers = new Random();

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     */
    public SparkleFilter() {
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param width
     * @param height
     */
    public void setDimensions(int width, int height) {
        long seed = 371;
        int randomness = 25;
        this.width = width;
        this.height = height;
        centreX = width / 2;
        centreY = height / 2;
        super.setDimensions(width, height);
        randomNumbers.setSeed(seed);
        rayLengths = new float[rays];
        for (int i = 0; i < rays; i++)
            rayLengths[i] = radius + randomness / 100.0f * radius * (float) randomNumbers.nextGaussian();
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param x
     * @param y
     * @param rgb
     * @return
     */
    public int filterRGB(int x, int y, int rgb) {
        int color = 0xffffffff;
        int amount = 50;
        float dx = x - centreX;
        float dy = y - centreY;
        float distance = dx * dx + dy * dy;
        float angle = (float) Math.atan2(dy, dx);
        float d = (angle + ImageMath.PI) / (ImageMath.TWO_PI) * rays;
        int i = (int) d;
        float f = d - i;

        if (radius != 0) {
            float length = ImageMath.lerp(f, rayLengths[i % rays], rayLengths[(i + 1) % rays]);
            float g = length * length / (distance + 0.0001f);
            g = (float) Math.pow(g, (100 - amount) / 50.0);
            f -= 0.5f;
//			f *= amount/50.0f;
            f = 1 - f * f;
            f *= g;
        }
        f = ImageMath.clamp(f, 0, 1);
        return ImageMath.mixColors(f, rgb, color);
    }

    public String toString() {
        return "Stylize/Sparkle...";
    }
}
