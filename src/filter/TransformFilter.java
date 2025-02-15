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
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * An abstract superclass for filters which distort images in some way. The subclass only needs to override
 * two methods to provide the mapping between source and destination pixels.
 */
public abstract class TransformFilter extends AbstractBufferedImageOp {

    private int srcWidth;
    private int srcHeight;

    /**
     * Treat pixels off the edge as zero.
     */
    private final static int ZERO = 0;

    /**
     * Clamp pixels to the filter edges.
     */
    final static int CLAMP = 1;

    /**
     * Wrap pixels off the edge onto the oppsoite edge.
     */
    private final static int WRAP = 2;

    /**
     * Clamp pixels RGB to the filter edges, but zero the alpha. This prevents gray borders on your filter.
     */
    private final static int RGB_CLAMP = 3;

    /**
     * Use nearest-neighbout interpolation.
     */
    private final static int NEAREST_NEIGHBOUR = 0;

    /**
     * Use bilinear interpolation.
     */
    private final static int BILINEAR = 1;

    /**
     * The action to take for pixels off the filter edge.
     */
    private int edgeAction = RGB_CLAMP;
    /**
     * The type of interpolation to use.
     */
    private int interpolation = BILINEAR;


    /**
     * Set the action to perform for pixels off the edge of the filter.
     *
     * @param edgeAction one of ZERO, CLAMP or WRAP
     */
    void setEdgeAction(int edgeAction) {
        this.edgeAction = edgeAction;
    }

    /**
     * Inverse transform a point. This method needs to be overriden by all subclasses.
     *
     * @param x   the X position of the pixel in the output filter
     * @param y   the Y position of the pixel in the output filter
     * @param out the position of the pixel in the input filter
     */
    protected abstract void transformInverse(int x, int y, float[] out);

    /**
     * Forward transform a rectangle. Used to determine the size of the output filter.
     *
     * @param rect the rectangle to transform
     */
    private void transformSpace(Rectangle rect) {
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param src
     * @param dst
     * @return
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dst) {
        int width = src.getWidth();
        int height = src.getHeight();
        int type = src.getType();
        WritableRaster srcRaster = src.getRaster();
        // The output filter rectangle.
        Rectangle transformedSpace;
        // The input filter rectangle.
        Rectangle originalSpace;

        originalSpace = new Rectangle(0, 0, width, height);
        transformedSpace = new Rectangle(0, 0, width, height);
        transformSpace(transformedSpace);

        if (dst == null) {
            ColorModel dstCM = src.getColorModel();
            dst = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(transformedSpace.width, transformedSpace.height), dstCM.isAlphaPremultiplied(), null);
        }
        WritableRaster dstRaster = dst.getRaster();

        int[] inPixels = getRGB(src, 0, 0, width, height, null);

        if (interpolation == NEAREST_NEIGHBOUR)
            return filterPixelsNN(dst, width, height, inPixels, transformedSpace);

        srcWidth = width;
        srcHeight = height;
        int srcWidth1 = width - 1;
        int srcHeight1 = height - 1;
        int outWidth = transformedSpace.width;
        int outHeight = transformedSpace.height;
        int outX, outY;
        int index = 0;
        int[] outPixels = new int[outWidth];

        outX = transformedSpace.x;
        outY = transformedSpace.y;
        float[] out = new float[2];

        for (int y = 0; y < outHeight; y++) {
            for (int x = 0; x < outWidth; x++) {
                transformInverse(outX + x, outY + y, out);
                int srcX = (int) Math.floor(out[0]);
                int srcY = (int) Math.floor(out[1]);
                float xWeight = out[0] - srcX;
                float yWeight = out[1] - srcY;
                int nw, ne, sw, se;

                if (srcX >= 0 && srcX < srcWidth1 && srcY >= 0 && srcY < srcHeight1) {
                    // Easy case, all corners are in the filter
                    int i = srcWidth * srcY + srcX;
                    nw = inPixels[i];
                    ne = inPixels[i + 1];
                    sw = inPixels[i + srcWidth];
                    se = inPixels[i + srcWidth + 1];
                } else {
                    // Some of the corners are off the filter
                    nw = getPixel(inPixels, srcX, srcY, srcWidth, srcHeight);
                    ne = getPixel(inPixels, srcX + 1, srcY, srcWidth, srcHeight);
                    sw = getPixel(inPixels, srcX, srcY + 1, srcWidth, srcHeight);
                    se = getPixel(inPixels, srcX + 1, srcY + 1, srcWidth, srcHeight);
                }
                outPixels[x] = ImageMath.bilinearInterpolate(xWeight, yWeight, nw, ne, sw, se);
            }
            setRGB(dst, 0, y, transformedSpace.width, 1, outPixels);
        }
        return dst;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param pixels
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    private int getPixel(int[] pixels, int x, int y, int width, int height) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            switch (edgeAction) {
                case ZERO:
                default:
                    return 0;
                case WRAP:
                    return pixels[(ImageMath.mod(y, height) * width) + ImageMath.mod(x, width)];
                case CLAMP:
                    return pixels[(ImageMath.clamp(y, 0, height - 1) * width) + ImageMath.clamp(x, 0, width - 1)];
                case RGB_CLAMP:
                    return pixels[(ImageMath.clamp(y, 0, height - 1) * width) + ImageMath.clamp(x, 0, width - 1)] & 0x00ffffff;
            }
        }
        return pixels[y * width + x];
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param dst
     * @param width
     * @param height
     * @param inPixels
     * @param transformedSpace
     * @return
     */
    private BufferedImage filterPixelsNN(BufferedImage dst, int width, int height, int[] inPixels, Rectangle transformedSpace) {
        srcWidth = width;
        srcHeight = height;
        int outWidth = transformedSpace.width;
        int outHeight = transformedSpace.height;
        int outX, outY, srcX, srcY;
        int[] outPixels = new int[outWidth];

        outX = transformedSpace.x;
        outY = transformedSpace.y;
        int[] rgb = new int[4];
        float[] out = new float[2];

        for (int y = 0; y < outHeight; y++) {
            for (int x = 0; x < outWidth; x++) {
                transformInverse(outX + x, outY + y, out);
                srcX = (int) out[0];
                srcY = (int) out[1];
                // int casting rounds towards zero, so we check out[0] < 0, not srcX < 0
                if (out[0] < 0 || srcX >= srcWidth || out[1] < 0 || srcY >= srcHeight) {
                    int p;
                    switch (edgeAction) {
                        case ZERO:
                        default:
                            p = 0;
                            break;
                        case WRAP:
                            p = inPixels[(ImageMath.mod(srcY, srcHeight) * srcWidth) + ImageMath.mod(srcX, srcWidth)];
                            break;
                        case CLAMP:
                            p = inPixels[(ImageMath.clamp(srcY, 0, srcHeight - 1) * srcWidth) + ImageMath.clamp(srcX, 0, srcWidth - 1)];
                            break;
                        case RGB_CLAMP:
                            p = inPixels[(ImageMath.clamp(srcY, 0, srcHeight - 1) * srcWidth) + ImageMath.clamp(srcX, 0, srcWidth - 1)] & 0x00ffffff;
                    }
                    outPixels[x] = p;
                } else {
                    int i = srcWidth * srcY + srcX;
                    rgb[0] = inPixels[i];
                    outPixels[x] = inPixels[i];
                }
            }
            setRGB(dst, 0, y, transformedSpace.width, 1, outPixels);
        }
        return dst;
    }

}

