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
 * An filter histogram.
 */
public class Histogram {

    static final int RED = 0;
    static final int GREEN = 1;
    static final int BLUE = 2;
    private static final int GRAY = 3;

    private int[][] histogram;
    private int numSamples;
    private int[] minValue;
    private int[] maxValue;
    private int[] minFrequency;
    private int[] maxFrequency;
    private float[] mean;
    private boolean isGray;

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param pixels
     * @param w
     * @param h
     * @param offset
     * @param stride
     */
    Histogram(int[] pixels, int w, int h, int offset, int stride) {
        histogram = new int[3][256];
        minValue = new int[4];
        maxValue = new int[4];
        minFrequency = new int[3];
        maxFrequency = new int[3];
        mean = new float[3];

        numSamples = w * h;
        isGray = true;

        int index = 0;
        for (int y = 0; y < h; y++) {
            index = offset + y * stride;
            for (int x = 0; x < w; x++) {
                int rgb = pixels[index++];
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                histogram[RED][r]++;
                histogram[GREEN][g]++;
                histogram[BLUE][b]++;
            }
        }

        for (int i = 0; i < 256; i++) {
            if (histogram[RED][i] != histogram[GREEN][i] || histogram[GREEN][i] != histogram[BLUE][i]) {
                isGray = false;
                break;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 256; j++) {
                if (histogram[i][j] > 0) {
                    minValue[i] = j;
                    break;
                }
            }

            for (int j = 255; j >= 0; j--) {
                if (histogram[i][j] > 0) {
                    maxValue[i] = j;
                    break;
                }
            }

            minFrequency[i] = Integer.MAX_VALUE;
            maxFrequency[i] = 0;
            for (int j = 0; j < 256; j++) {
                minFrequency[i] = Math.min(minFrequency[i], histogram[i][j]);
                maxFrequency[i] = Math.max(maxFrequency[i], histogram[i][j]);
                mean[i] += (float) (j * histogram[i][j]);
            }
            mean[i] /= (float) numSamples;
        }
        minValue[GRAY] = Math.min(Math.min(minValue[RED], minValue[GREEN]), minValue[BLUE]);
        maxValue[GRAY] = Math.max(Math.max(maxValue[RED], maxValue[GREEN]), maxValue[BLUE]);
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @return
     */
    int getNumSamples() {
        return numSamples;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     *
     * @param channel
     * @param value
     * @return
     */
    int getFrequency(int channel, int value) {
        if (numSamples < 1 || channel < 0 || channel > 2 ||
                value < 0 || value > 255)
            return -1;
        return histogram[channel][value];
    }
}
