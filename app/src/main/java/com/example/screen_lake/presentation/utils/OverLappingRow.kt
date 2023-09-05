package com.example.screen_lake.presentation.utils

import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import com.example.screen_lake.appUtils.Constants
import com.example.screen_lake.appUtils.Constants.FloatConstants.POINT_FIVE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ONE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO

@Composable
fun OverlappingRow(
    modifier: Modifier = Modifier,
    @FloatRange(from = Constants.DoubleConstants.ZERO,to =Constants.DoubleConstants.ONE) overlapFactor:Float = POINT_FIVE,
    content: @Composable () -> Unit,
){
    val measurePolicy = overlappingRowMeasurePolicy(overlapFactor)
    Layout(measurePolicy = measurePolicy,
        content = content,
        modifier = modifier )
}

private fun overlappingRowMeasurePolicy(overlapFactor: Float) = MeasurePolicy { measurables, constraints ->
    val placeables = measurables.map { measurable -> measurable.measure(constraints)}
    val height = placeables.maxOf { it.height }
    val width = (placeables.subList(ONE,placeables.size).sumOf { it.width  } * overlapFactor + placeables[ZERO].width).toInt()
    layout(width,height) {
        var xPos = ZERO
        for (placeable in placeables) {
            placeable.placeRelative(xPos, ZERO, Constants.FloatConstants.ZERO)
            xPos += (placeable.width * overlapFactor).toInt()
        }
    }
}