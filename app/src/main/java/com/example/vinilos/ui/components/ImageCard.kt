package com.example.vinilos.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.example.vinilos.R

@Composable
fun ImageCard(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier,
    imageHeight: Int = 162,
    imagePadding: Int = 0,
    textStyleTypography: TextStyle = MaterialTheme.typography.titleMedium,
    textFontWeight: FontWeight = FontWeight.Normal
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .memoryCacheKey(imageUrl)
                .diskCacheKey(imageUrl)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.loading_img),
            error = painterResource(R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight.dp)
                .padding(imagePadding.dp)
                .clip(RoundedCornerShape(8.dp)),
            imageLoader = customImageLoader(context = LocalContext.current)
        )
        Text(
            text = title,
            style = textStyleTypography,
            modifier = Modifier.padding(top = 8.dp),
            fontWeight = textFontWeight
        )
    }
}

fun customImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25) // Use 25% of app's memory for caching
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizeBytes(10L * 1024 * 1024) // 10 MB
                .build()
        }
        .logger(DebugLogger())
        .respectCacheHeaders(false)
        .build()
}