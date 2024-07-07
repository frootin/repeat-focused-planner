package ru.sfu.planner.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import ru.sfu.planner.R
import ru.sfu.planner.ui.theme.AppTheme

@Composable
fun TaskContainer() {
    
}

@Composable
fun TaskContainerWithSubtasks(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(height = 72.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(height = 72.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 16.dp,
                        end = 24.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(weight = 1f)
                ) {
                    Text(
                        text = "List item",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 1.5.em,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(align = Alignment.CenterVertically))
                    Text(
                        text = "Supporting line text lorem ipsum dolor sit amet, consectetur.",
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 1.43.em,
                        style = TextStyle(
                            fontSize = 14.sp,
                            letterSpacing = 0.25.sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth())
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_caret_right),
                        contentDescription = "Icons/arrow_right_24px",
                        tint = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListItemTaskWithSubtasksPreview() {
    AppTheme {
        TaskContainerWithSubtasks()
    }
}