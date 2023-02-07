package com.example.testyoutubeapi.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.testyoutubeapi.screens.internalStoreScreen.InternalStoreScreenViewModel
import com.example.testyoutubeapi.screens.youTubeScreen.YouTubeScreenViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "NewApi")
@Composable
fun MainScreenView(
    youTubeScreenViewModel: YouTubeScreenViewModel,
    internalStoreScreenViewModel: InternalStoreScreenViewModel,
    context: Context
) {

    val navController = rememberNavController()
    val permissions: Array<String> =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            arrayOf(  Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else{
           arrayOf( Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.POST_NOTIFICATIONS)
        }


    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        SideEffect {
            checkAndRequestPermissions(
                context,
                permissions,
                launcherMultiplePermissions
            )
        }
        NavigationGraph(
            navController = navController,
            youTubeScreenViewModel, internalStoreScreenViewModel
        )
    }
}

fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {

    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}