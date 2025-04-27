package com.example.vinilos

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.example.vinilos.data.datasources.mock.MockAlbumesApiService
import com.example.vinilos.data.datasources.mock.mockAlbums
import com.example.vinilos.data.repositories.AlbumesRepository
import com.example.vinilos.ui.screens.AlbumesScreen
import com.example.vinilos.ui.viewmodels.AlbumesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class AlbumesScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: AlbumesViewModel

    @Before
    fun setUp() {
        viewModel = AlbumesViewModel(
            albumesRepository = AlbumesRepository(MockAlbumesApiService)
        )
        composeTestRule.setContent {
            val uiState = viewModel.uiState.collectAsState().value
            AlbumesScreen(
                viewModel = viewModel,
                albumesUiState = uiState,
            )
        }
        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("AlbumesSuccessScreen"),
            8000L
        )
    }

    @Test
    fun albumesScreen_verifyContent() {
        for (album in mockAlbums) {
            composeTestRule.onNodeWithText(album.name).assertIsDisplayed()
        }
    }

    @Test
    fun albumesScreen_searchForAlbum_findsAlbum() {
        val searchInput = composeTestRule.onNodeWithTag("AlbumesSearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpAlbum = mockAlbums[0]
        searchInput.performTextInput(lookedUpAlbum.name)
        Espresso.closeSoftKeyboard()
        composeTestRule.onNodeWithTag("AlbumCard-${lookedUpAlbum.id}").assertIsDisplayed()

        val remainingAlbums = mockAlbums.subList(1, mockAlbums.size)
        for (album in remainingAlbums) {
            composeTestRule.onNodeWithText(album.name).assertDoesNotExist()
        }
    }

    @Test
    fun albumesScreen_searchForAlbum_findNoAlbum() {
        val searchInput = composeTestRule.onNodeWithTag("AlbumesSearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpAlbum = "No album"
        searchInput.performTextInput(lookedUpAlbum)
        Espresso.closeSoftKeyboard()
        // Validate only text field has this text
        composeTestRule.onAllNodes(hasText(lookedUpAlbum)).assertCountEquals(1)
    }

    @Test
    fun albumesScreen_searchForAlbum_clearSearch() {
        val searchInput = composeTestRule.onNodeWithTag("AlbumesSearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpAlbum = mockAlbums[0]
        searchInput.performTextInput(lookedUpAlbum.name)
        Espresso.closeSoftKeyboard()
        composeTestRule.onNodeWithTag("AlbumCard-${lookedUpAlbum.id}").assertIsDisplayed()

        val clearButton = composeTestRule.onNodeWithTag("ClearButton")
        clearButton.assertIsDisplayed()
        clearButton.performClick()
        for (album in mockAlbums) {
            composeTestRule.onNodeWithText(album.name).assertIsDisplayed()
        }
    }
}