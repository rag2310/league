package com.rago.league.ui.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.rago.league.R

@Composable
fun SplashScreen() {
    SplashScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SplashScreenContent() {
    val scaleAnimation: Animatable<Float, AnimationVector1D> =
        remember {
            Animatable(initialValue = 0f)
        }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        durationMillisAnimation = 1500,
        success = false,
        onNav = {

        }
    )

    Scaffold(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            DesignSplashScreen(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                imagePainter = painterResource(id = R.drawable.logo),
                scaleAnimation = scaleAnimation
            )
        }
    }
}


@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    durationMillisAnimation: Int,
    success: Boolean,
    onNav: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )
    }
    LaunchedEffect(key1 = success) {
        if (success) {
            onNav()
        }
    }
}

@Composable
fun DesignSplashScreen(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    scaleAnimation: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = imagePainter, contentDescription = "Logo Splash Screen",
                modifier = modifier
                    .scale(scale = scaleAnimation.value)
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Text(text = "League", style = MaterialTheme.typography.titleMedium)
        }
    }
}

