package com.example.screen_lake.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.screenlake.utils.Constants.StringConstants.EMPTY

@Composable
fun SelectableItem(
    modifier: Modifier,
    bitmap: ImageBitmap?=null,
    painter: Painter?=null,
    textTitle:String,
    isChecked:Boolean=false,
    imageContentScale: ContentScale=ContentScale.Crop,
    onClick:()->Unit
){
    Box(
        modifier = modifier
            .clickable {
                onClick()
            }
            .then(
                if (!isChecked) {
                    Modifier.background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else {
                    Modifier
                        .background(
                            color = MaterialTheme.colors.onPrimary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.onBackground,
                            shape = RoundedCornerShape(16.dp)
                        )
                }
            )
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(vertical = 6.dp, horizontal = 12.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                bitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = EMPTY,
                        contentScale = imageContentScale,
                        modifier = Modifier.then(
                            if (!isChecked){
                                Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        color = MaterialTheme.colors.onPrimary,
                                        shape = CircleShape
                                    )
                            }else{
                                Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                            }
                        )
                    )
                }
                painter?.let {
                    Image(
                        it,
                        contentDescription = EMPTY,
                        contentScale = imageContentScale,
                        modifier = Modifier.then(
                            if (!isChecked) {
                                Modifier
                                    .size(32.dp)
                                    .background(
                                        color = MaterialTheme.colors.onPrimary,
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape)
                            } else {
                                Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                            }
                        )
                    )
                }
                Text(
                    text = textTitle,
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    maxLines = 1,
                )
            }
            Box(modifier = Modifier.then(
                if (isChecked){
                    Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.onBackground, shape = CircleShape)
                }else{
                    Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .border(
                            1.dp,
                            color = MaterialTheme.colors.secondaryVariant,
                            shape = CircleShape
                        )
                }
            ),
                contentAlignment = Alignment.Center,
            ){
                if(isChecked) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.Done,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = EMPTY
                    )
                }
            }
        }

    }
}