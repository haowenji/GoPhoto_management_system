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

import java.awt.*;

/**
 * A class to emboss an filter.
 */
public class EmbossFilter extends WholeImageFilter implements Filter {

    private final static float pixelScale = 255.9f;

    private boolean emboss = false;

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     */
    public EmbossFilter() {
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param width            the filter width
     * @param height           the filter height
     * @param inPixels         the filter pixels
     * @param transformedSpace the output bounds
     * @return
     */
    protected int[] filterPixels(int width, int height, int[] inPixels, Rectangle transformedSpace) {
        int index = 0;
        int[] outPixels = new int[width * height];

        int[] bumpPixels;
        int bumpMapWidth, bumpMapHeight;

        bumpMapWidth = width;
        bumpMapHeight = height;
        bumpPixels = new int[bumpMapWidth * bumpMapHeight];
        for (int i = 0; i < inPixels.length; i++)
            bumpPixels[i] = PixelUtils.brightness(inPixels[i]);

        int Nx, Ny, Nz, Lx, Ly, Lz, Nz2, NzLz, NdotL;
        int shade, background;

        float azimuth = 135.0f * ImageMath.PI / 180.0f;
        float elevation = 30.0f * ImageMath.PI / 180f;
        Lx = (int) (Math.cos(azimuth) * Math.cos(elevation) * pixelScale);
        Ly = (int) (Math.sin(azimuth) * Math.cos(elevation) * pixelScale);
        Lz = (int) (Math.sin(elevation) * pixelScale);

        float width45 = 3.0f;
        Nz = (int) (6 * 255 / width45);
        Nz2 = Nz * Nz;
        NzLz = Nz * Lz;

        background = Lz;

        int bumpIndex = 0;

        for (int y = 0; y < height; y++, bumpIndex += bumpMapWidth) {
            int s1 = bumpIndex;
            int s2 = s1 + bumpMapWidth;
            int s3 = s2 + bumpMapWidth;

            for (int x = 0; x < width; x++, s1++, s2++, s3++) {
                if (y != 0 && y < height - 2 && x != 0 && x < width - 2) {
                    Nx = bumpPixels[s1 - 1] + bumpPixels[s2 - 1] + bumpPixels[s3 - 1] - bumpPixels[s1 + 1] - bumpPixels[s2 + 1] - bumpPixels[s3 + 1];
                    Ny = bumpPixels[s3 - 1] + bumpPixels[s3] + bumpPixels[s3 + 1] - bumpPixels[s1 - 1] - bumpPixels[s1] - bumpPixels[s1 + 1];

                    if (Nx == 0 && Ny == 0)
                        shade = background;
                    else if ((NdotL = Nx * Lx + Ny * Ly + NzLz) < 0)
                        shade = 0;
                    else
                        shade = (int) (NdotL / Math.sqrt(Nx * Nx + Ny * Ny + Nz2));
                } else
                    shade = background;

                if (emboss) {
                    int rgb = inPixels[index];
                    int a = rgb & 0xff000000;
                    int r = (rgb >> 16) & 0xff;
                    int g = (rgb >> 8) & 0xff;
                    int b = rgb & 0xff;
                    r = (r * shade) >> 8;
                    g = (g * shade) >> 8;
                    b = (b * shade) >> 8;
                    outPixels[index++] = a | (r << 16) | (g << 8) | b;
                } else
                    outPixels[index++] = 0xff000000 | (shade << 16) | (shade << 8) | shade;
            }
        }

        return outPixels;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @return
     */
    public String toString() {
        return "Stylize/Emboss...";
    }

}
