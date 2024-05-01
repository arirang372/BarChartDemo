package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.components.YAxis;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    //LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
