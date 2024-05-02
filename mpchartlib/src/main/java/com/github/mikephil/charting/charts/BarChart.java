package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;

/**
 * Chart that draws bars.
 *
 * @author Philipp Jahoda
 */
public class BarChart extends BarLineChartBase<BarData> implements BarDataProvider {

    /**
     * flag that indicates whether the highlight should be full-bar oriented, or single-value?
     */
    protected boolean mHighlightFullBarEnabled = false;

    /**
     * if set to true, all values are drawn above their bars, instead of below their top
     */
    private boolean mDrawValueAboveBar = true;

    /**
     * if set to true, a grey area is drawn behind each bar that indicates the maximum value
     */
    private boolean mDrawBarShadow = false;

    private boolean mFitBars = false;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new BarChartRenderer(this, mViewPortHandler);

        setHighlighter(new BarHighlighter(this));

        getXAxis().setSpaceMin(0.5f);
        getXAxis().setSpaceMax(0.5f);
    }

    @Override
    protected void calcMinMax() {

        if (mFitBars) {
            mXAxis.calculate(mData.getXMin() - mData.getBarWidth() / 2f, mData.getXMax() + mData.getBarWidth() / 2f);
        } else {
            mXAxis.calculate(mData.getXMin(), mData.getXMax());
        }

        // calculate axis range (min / max) according to provided data
        mAxisLeft.calculate(mData.getYMin(YAxis.AxisDependency.LEFT), mData.getYMax(YAxis.AxisDependency.LEFT));
        mAxisRight.calculate(mData.getYMin(YAxis.AxisDependency.RIGHT), mData.getYMax(YAxis.AxisDependency
                .RIGHT));
    }

    /**
     * Returns the Highlight object (contains x-index and DataSet index) of the selected value at the given touch
     * point
     * inside the BarChart.
     *
     * @param x
     * @param y
     * @return
     */
    public Highlight getHighlightByTouchPoint(float x, float y) {

        if (mData == null) {
            Log.e(LOG_TAG, "Can't select by touch. No data set.");
            return null;
        } else {
            Highlight h = getHighlighter().getHighlight(x, y);
            if (h == null || !isHighlightFullBarEnabled()) return h;

            // For isHighlightFullBarEnabled, remove stackIndex
            return new Highlight(h.getX(), h.getY(),
                    h.getXPx(), h.getYPx(),
                    h.getDataSetIndex(), -1, h.getAxis());
        }
    }

    /**
     * Returns the bounding box of the specified Entry in the specified DataSet. Returns null if the Entry could not be
     * found in the charts data.  Performance-intensive code should use void getBarBounds(BarEntry, RectF) instead.
     *
     * @param e
     * @return
     */
    public RectF getBarBounds(BarEntry e) {

        RectF bounds = new RectF();
        getBarBounds(e, bounds);

        return bounds;
    }

    /**
     * The passed outputRect will be assigned the values of the bounding box of the specified Entry in the specified DataSet.
     * The rect will be assigned Float.MIN_VALUE in all locations if the Entry could not be found in the charts data.
     *
     * @param e
     * @return
     */
    public void getBarBounds(BarEntry e, RectF outputRect) {

        RectF bounds = outputRect;

        IBarDataSet set = mData.getDataSetForEntry(e);

        if (set == null) {
            bounds.set(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
            return;
        }

        float y = e.getY();
        float x = e.getX();

        float barWidth = mData.getBarWidth();

        float left = x - barWidth / 2f;
        float right = x + barWidth / 2f;
        float top = y >= 0 ? y : 0;
        float bottom = y <= 0 ? y : 0;

        bounds.set(left, top, right, bottom);

        getTransformer(set.getAxisDependency()).rectValueToPixel(outputRect);
    }

    /**
     * If set to true, all values are drawn above their bars, instead of below their top.
     *
     * @param enabled
     */
    public void setDrawValueAboveBar(boolean enabled) {
        mDrawValueAboveBar = enabled;
    }

    /**
     * returns true if drawing values above bars is enabled, false if not
     *
     * @return
     */
    public boolean isDrawValueAboveBarEnabled() {
        return mDrawValueAboveBar;
    }

    /**
     * If set to true, a grey area is drawn behind each bar that indicates the maximum value. Enabling his will reduce
     * performance by about 50%.
     *
     * @param enabled
     */
    public void setDrawBarShadow(boolean enabled) {
        mDrawBarShadow = enabled;
    }

    /**
     * returns true if drawing shadows (maxvalue) for each bar is enabled, false if not
     *
     * @return
     */
    public boolean isDrawBarShadowEnabled() {
        return mDrawBarShadow;
    }

    /**
     * Set this to true to make the highlight operation full-bar oriented, false to make it highlight single values (relevant
     * only for stacked). If enabled, highlighting operations will highlight the whole bar, even if only a single stack entry
     * was tapped.
     * Default: false
     *
     * @param enabled
     */
    public void setHighlightFullBarEnabled(boolean enabled) {
        mHighlightFullBarEnabled = enabled;
    }

    /**
     * @return true the highlight operation is be full-bar oriented, false if single-value
     */
    @Override
    public boolean isHighlightFullBarEnabled() {
        return mHighlightFullBarEnabled;
    }

    @Override
    public BarData getBarData() {
        return mData;
    }
}
