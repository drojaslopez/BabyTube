package com.babytube.player.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.babytube.player.data.model.VideoItem
import com.babytube.player.ui.player.PlayerScreen
import com.babytube.player.ui.videolist.VideoListScreen

@Composable
fun BabyTubeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "video_list",
        modifier = modifier
    ) {
        composable("video_list") {
            VideoListScreen(
                onVideoSelected = { video ->
                    navController.navigate("player/${video.id}/${video.uri}/${video.name}/${video.duration}/${video.size}/${video.mimeType}")
                }
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
            val mimeType = backStackEntry.arguments?.getString("mimeType") ?: ""
            
            val video = VideoItem(id, uri, name, duration, size, mimeType)
            
            PlayerScreen(
                video = video,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
