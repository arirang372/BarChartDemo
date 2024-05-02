
package com.github.mikephil.charting.data;

import android.graphics.Color;

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Fill;

import java.util.ArrayList;
import java.util.List;

public class BarDataSet extends DataSet<BarEntry> implements IBarDataSet {
    protected int mHighLightColor = Color.rgb(255, 187, 115);

    /**
     * the maximum number of bars that are stacked upon each other, this value
     * is calculated from the Entries that are added to the DataSet
     */
    private int mStackSize = 1;

    /**
     * the color used for drawing the bar shadows
     */
    private int mBarShadowColor = Color.rgb(215, 215, 215);

    private float mBarBorderWidth = 0.0f;

    private int mBarBorderColor = Color.BLACK;

    /**
     * the alpha value used to draw the highlight indicator bar
     */
    private int mHighLightAlpha = 120;


    /**
     * array of labels used to describe the different values of the stacked bars
     */
    private String[] mStackLabels = new String[]{};

    protected List<Fill> mFills = null;

    public BarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);

        mHighLightColor = Color.rgb(0, 0, 0);

        calcStackSize(yVals);
    }

    @Override
    public DataSet<BarEntry> copy() {
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < mEntries.size(); i++) {
            entries.add(mEntries.get(i).copy());
        }
        BarDataSet copied = new BarDataSet(entries, getLabel());
        copy(copied);
        return copied;
    }

    protected void copy(BarDataSet barDataSet) {
        super.copy(barDataSet);
        barDataSet.mStackSize = mStackSize;
        barDataSet.mBarShadowColor = mBarShadowColor;
        barDataSet.mBarBorderWidth = mBarBorderWidth;
        barDataSet.mStackLabels = mStackLabels;
        barDataSet.mHighLightAlpha = mHighLightAlpha;
    }

    @Override
    public List<Fill> getFills() {
        return mFills;
    }

    @Override
    public Fill getFill(int index) {
        return mFills.get(index % mFills.size());
    }

    /**
     * This method is deprecated.
     * Use getFills() instead.
     */
    @Deprecated
    public List<Fill> getGradients() {
        return mFills;
    }

    /**
     * This method is deprecated.
     * Use getFill(...) instead.
     *
     * @param index
     */
    @Deprecated
    public Fill getGradient(int index) {
        return getFill(index);
    }

    /**
     * This method is deprecated.
     * Use setFills(...) instead.
     *
     * @param gradientColors
     */
    @Deprecated
    public void setGradientColors(List<Fill> gradientColors) {
        this.mFills = gradientColors;
    }

    /**
     * Sets the fills for the bars in this dataset.
     *
     * @param fills
     */
    public void setFills(List<Fill> fills) {
        this.mFills = fills;
    }

    /**
     * calculates the maximum stacksize that occurs in the Entries array of this
     * DataSet
     */
    private void calcStackSize(List<BarEntry> yVals) {

        for (int i = 0; i < yVals.size(); i++) {

            float[] vals = yVals.get(i).getYVals();

            if (vals != null && vals.length > mStackSize)
                mStackSize = vals.length;
        }
    }

    @Override
    protected void calcMinMax(BarEntry e) {

        if (e != null && !Float.isNaN(e.getY())) {

            if (e.getYVals() == null) {

                if (e.getY() < mYMin)
                    mYMin = e.getY();

                if (e.getY() > mYMax)
                    mYMax = e.getY();
            } else {

                if (-e.getNegativeSum() < mYMin)
                    mYMin = -e.getNegativeSum();

                if (e.getPositiveSum() > mYMax)
                    mYMax = e.getPositiveSum();
            }

            calcMinMaxX(e);
        }
    }

    @Override
    public int getStackSize() {
        return mStackSize;
    }

    @Override
    public boolean isStacked() {
        return mStackSize > 1 ? true : false;
    }


    @Override
    public int getBarShadowColor() {
        return mBarShadowColor;
    }

    /**
     * Returns the width used for drawing borders around the bars.
     * If borderWidth == 0, no border will be drawn.
     *
     * @return
     */
    @Override
    public float getBarBorderWidth() {
        return mBarBorderWidth;
    }

    /**
     * Returns the color drawing borders around the bars.
     *
     * @return
     */
    @Override
    public int getBarBorderColor() {
        return mBarBorderColor;
    }


    /**
     * Sets labels for different values of bar-stacks, in case there are one.
     *
     * @param labels
     */
    public void setStackLabels(String[] labels) {
        mStackLabels = labels;
    }

    @Override
    public String[] getStackLabels() {
        return mStackLabels;
    }
}
