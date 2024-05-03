package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp Jahoda on 21/10/15.
 * This is the base dataset of all DataSets. It's purpose is to implement critical methods
 * provided by the IDataSet interface.
 */
public abstract class BaseDataSet<T extends Entry> implements IDataSet<T> {

    /**
     * List representing all colors that are used for this DataSet
     */
    protected List<Integer> mColors = null;

    /**
     * List representing all colors that are used for drawing the actual values for this DataSet
     */
    protected List<Integer> mValueColors = null;

    /**
     * label that describes the DataSet or the data the DataSet represents
     */
    private String mLabel = "DataSet";

    /**
     * this specifies which axis this DataSet should be plotted against
     */
    protected YAxis.AxisDependency mAxisDependency = YAxis.AxisDependency.LEFT;

    /**
     * if true, value highlightning is enabled
     */
    protected boolean mHighlightEnabled = true;

    /**
     * custom formatter that is used instead of the auto-formatter if set
     */
    protected transient IValueFormatter mValueFormatter;

    /**
     * the typeface used for the value text
     */
    protected Typeface mValueTypeface;

   // private Legend.LegendForm mForm = Legend.LegendForm.DEFAULT;
    private float mFormSize = Float.NaN;
    private float mFormLineWidth = Float.NaN;
    private DashPathEffect mFormLineDashEffect = null;

    /**
     * if true, y-values are drawn on the chart
     */
    protected boolean mDrawValues = true;

    /**
     * if true, y-icons are drawn on the chart
     */
    protected boolean mDrawIcons = true;

    /**
     * the offset for drawing icons (in dp)
     */
    protected MPPointF mIconsOffset = new MPPointF();

    /**
     * the size of the value-text labels
     */
    protected float mValueTextSize = 17f;

    /**
     * flag that indicates if the DataSet is visible or not
     */
    protected boolean mVisible = true;

    /**
     * Default constructor.
     */
    public BaseDataSet() {
        mColors = new ArrayList<>();
        mValueColors = new ArrayList<>();

        // default color
        mColors.add(Color.rgb(140, 234, 255));
        mValueColors.add(Color.BLACK);
    }

    /**
     * Constructor with label.
     *
     * @param label
     */
    public BaseDataSet(String label) {
        this();
        this.mLabel = label;
    }

    /**
     * Use this method to tell the data set that the underlying data has changed.
     */
    public void notifyDataSetChanged() {
        calcMinMax();
    }


    /**
     * ###### ###### COLOR GETTING RELATED METHODS ##### ######
     */

    @Override
    public List<Integer> getColors() {
        return mColors;
    }


    @Override
    public int getColor() {
        return mColors.get(0);
    }

    @Override
    public int getColor(int index) {
        return mColors.get(index % mColors.size());
    }

    /**
     * Sets the one and ONLY color that should be used for this DataSet.
     * Internally, this recreates the colors array and adds the specified color.
     *
     * @param color
     */
    public void setColor(int color) {
        resetColors();
        mColors.add(color);
    }

    /**
     * Resets all colors of this DataSet and recreates the colors array.
     */
    public void resetColors() {
        if (mColors == null) {
            mColors = new ArrayList<>();
        }
        mColors.clear();
    }

    /**
     * ###### ###### OTHER STYLING RELATED METHODS ##### ######
     */

    @Override
    public void setLabel(String label) {
        mLabel = label;
    }

    @Override
    public String getLabel() {
        return mLabel;
    }

    @Override
    public void setValueFormatter(IValueFormatter f) {

        if (f == null)
            return;
        else
            mValueFormatter = f;
    }

    @Override
    public IValueFormatter getValueFormatter() {
        if (needsFormatter())
            return Utils.getDefaultValueFormatter();
        return mValueFormatter;
    }

    @Override
    public boolean needsFormatter() {
        return mValueFormatter == null;
    }


    @Override
    public void setValueTextSize(float size) {
        mValueTextSize = Utils.convertDpToPixel(size);
    }

    @Override
    public int getValueTextColor(int index) {
        return mValueColors.get(index % mValueColors.size());
    }

    @Override
    public Typeface getValueTypeface() {
        return mValueTypeface;
    }

    @Override
    public float getValueTextSize() {
        return mValueTextSize;
    }

    @Override
    public boolean isDrawValuesEnabled() {
        return mDrawValues;
    }

    @Override
    public void setDrawIcons(boolean enabled) {
        mDrawIcons = enabled;
    }

    @Override
    public boolean isDrawIconsEnabled() {
        return mDrawIcons;
    }

    @Override
    public MPPointF getIconsOffset() {
        return mIconsOffset;
    }

    @Override
    public boolean isVisible() {
        return mVisible;
    }

    @Override
    public YAxis.AxisDependency getAxisDependency() {
        return mAxisDependency;
    }

    @Override
    public boolean contains(T e) {

        for (int i = 0; i < getEntryCount(); i++) {
            if (getEntryForIndex(i).equals(e))
                return true;
        }

        return false;
    }

    protected void copy(BaseDataSet baseDataSet) {
        baseDataSet.mAxisDependency = mAxisDependency;
        baseDataSet.mColors = mColors;
        baseDataSet.mDrawIcons = mDrawIcons;
        baseDataSet.mDrawValues = mDrawValues;
        baseDataSet.mFormLineDashEffect = mFormLineDashEffect;
        baseDataSet.mFormLineWidth = mFormLineWidth;
        baseDataSet.mFormSize = mFormSize;
        baseDataSet.mHighlightEnabled = mHighlightEnabled;
        baseDataSet.mIconsOffset = mIconsOffset;
        baseDataSet.mValueColors = mValueColors;
        baseDataSet.mValueFormatter = mValueFormatter;
        baseDataSet.mValueColors = mValueColors;
        baseDataSet.mValueTextSize = mValueTextSize;
        baseDataSet.mVisible = mVisible;
    }
}
