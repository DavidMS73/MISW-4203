package com.example.vinilos

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.vinilos.data.datasources.mock.MockAlbumesApiService
import com.example.vinilos.data.datasources.mock.mockAlbums
import com.example.vinilos.data.repositories.AlbumesRepository
import com.example.vinilos.ui.screens.AlbumDetailScreen
import com.example.vinilos.ui.viewmodels.AlbumDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat

@OptIn(ExperimentalTestApi::class)
class AlbumDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var viewModel: AlbumDetailViewModel
    private val albumId = 100

    @Before
    fun setUp() {
        viewModel = AlbumDetailViewModel(
            albumesRepository = AlbumesRepository(MockAlbumesApiService),
            id = albumId
        )

        composeTestRule.setContent {
            val uiState = viewModel.uiState.collectAsState().value
            AlbumDetailScreen(
                viewModel = viewModel,
                albumesUiState = uiState,
                albumId = albumId,
                navController = rememberNavController()
            )
        }

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("AlbumDetailCard-${albumId}"),
            8000L
        )
    }

    @Test
    fun albumDetailScreen_verifyImageCardIsDisplayed() {
        val album = mockAlbums[0]

        composeTestRule.onNodeWithTag("AlbumDetailCard-${album.id}").assertIsDisplayed()
        composeTestRule.onNodeWithText(album.genre).assertIsDisplayed()
        composeTestRule.onNodeWithText(album.recordLabel).assertIsDisplayed()
        composeTestRule.onNodeWithText(album.description).assertIsDisplayed()
    }

    @Test
    fun albumDetailScreen_verifyReleaseDate_IsCorrect(){
        val album = mockAlbums[0]

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val formattedDate = formatter.format(parser.parse(album.releaseDate))

        composeTestRule.onNodeWithTag("ReleaseDateText").assertTextEquals(formattedDate)
    }
}