package filter;


/**
 * Some more useful math functions for filter processing.
 * These are becoming obsolete as we move to Java2D. Use MiscComposite instead.
 */
public class PixelUtils {


    /**
     * Clamp a value to the range 0..255
     */
    public static int clamp(int c) {
        if (c < 0)
            return 0;
        if (c > 255)
            return 255;
        return c;
    }

    /**
     * Used on-line open-sourced code. Don't know the implementation detail.
     * @param rgb
     * @return
     */
    static int brightness(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        return (r + g + b) / 3;
    }
}
