package com.babytube.player.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babytube.player.data.model.VideoItem
import android.net.Uri
import com.babytube.player.ui.player.PlayerScreen
import com.babytube.player.ui.videolist.VideoListScreen

import com.babytube.player.ui.permissions.PermissionScreen
import com.babytube.player.ui.parent.ParentSelectionScreen

@Composable
fun BabyTubeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "permissions",
        modifier = modifier
    ) {
        composable("permissions") {
            PermissionScreen(
                onPermissionGranted = {
                    navController.navigate("video_list") {
                        popUpTo("permissions") { inclusive = true }
                    }
                }
            )
        }
        
        composable("video_list") {
            VideoListScreen(
                onVideoSelected = { video ->
                    navController.navigate("player/${video.id}/${Uri.encode(video.uri)}/${Uri.encode(video.name)}/${video.duration}/${video.size}/${video.mimeType.replace("/", "_")}")
                },
                onParentModeClick = {
                    navController.navigate("parent_selection")
                }
            )
        }
        
        composable("parent_selection") {
            ParentSelectionScreen(
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            "player/{id}/{uri}/{name}/{duration}/{size}/{mimeType}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLong() ?: 0L
            val uri = backStackEntry.arguments?.getString("uri") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val duration = backStackEntry.arguments?.getString("duration")?.toLong() ?: 0L
            val size = backStackEntry.arguments?.getString("size")?.toLong() ?: 0L
            val mimeType = backStackEntry.arguments?.getString("mimeType")?.replace("_", "/") ?: ""
            
            val video = VideoItem(id, uri, name, duration, size, mimeType)
            
            PlayerScreen(
                video = video,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
