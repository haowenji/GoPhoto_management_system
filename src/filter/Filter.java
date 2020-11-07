package filter;

import java.awt.image.BufferedImage;

/**
 * This interface contains a filter method. Takes an original BufferedImage to add filter effect
 * and then returns a BufferedImage with the effect.
 *
 * @author Wenjie, Hao
 */
public interface Filter {
    /**
     * Takes an original BufferedImage, src, to add filter effect. Then, return
     * and then returns a BufferedImage with the filter effect.
     *
     * @param src An original BufferedImage without filter effect.
     * @param dst Usually null at the beginning.
     *            At the end, dst is processed to be an BufferedImage with the filter effect.
     * @return An after-processed BufferedImage with the filter effect.
     */
    BufferedImage filter(BufferedImage src, BufferedImage dst);
}
