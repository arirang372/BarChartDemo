
package com.github.mikephil.charting.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds predefined color integer arrays (e.g.
 * ColorTemplate.VORDIPLOM_COLORS) and convenience methods for loading colors
 * from resources.
 *
 * @author Philipp Jahoda
 */
public class ColorTemplate {

    /**
     * an "invalid" color that indicates that no color is set
     */
    public static final int COLOR_NONE = 0x00112233;

    /**
     * this "color" is used for the Legend creation and indicates that the next
     * form should be skipped
     */
    public static final int COLOR_SKIP = 0x00112234;

    /**
     * Turns an array of colors (integer color values) into an ArrayList of
     * colors.
     *
     * @param colors
     * @return
     */
    public static List<Integer> createColors(int[] colors) {

        List<Integer> result = new ArrayList<>();

        for (int i : colors) {
            result.add(i);
        }

        return result;
    }
}
