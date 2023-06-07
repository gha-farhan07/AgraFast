package com.agrafast.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.agrafast.R

@Composable
fun SimpleTopBar(title: String) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(48.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = stringResource(id = R.string.my_plant),
      style = MaterialTheme.typography.titleLarge,
      maxLines = 1,
    )
  }
}