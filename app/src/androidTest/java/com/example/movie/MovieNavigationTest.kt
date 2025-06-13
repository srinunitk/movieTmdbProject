package com.example.movie

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import okhttp3.internal.wait
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

private const val APP_PACKAGE = "com.example.movie"
private const val LAUNCH_TIMEOUT = 5000L
private const val UI_TIMEOUT = 150000L

// Test Tags (content descriptions)
private const val MOVIE_LIST_SCREEN_TAG = "movie_list_screen"
private const val MOVIE_ITEM_TAG_PREFIX = "movie_item_" // e.g., movie_item_123
private const val MOVIE_DETAILS_SCREEN_TAG = "movie_details_screen"
private const val MOVIE_DETAILS_TITLE_TAG = "movie_details_title"
private const val WATCH_TRAILER_BUTTON_TAG = "Watch Trailer Button"
private const val VIDEO_PLAYER_VIEW_TAG = "videoPlayerView"
@RunWith(AndroidJUnit4::class)
class MovieNavigationTest {

    private lateinit var device: UiDevice


    @Before
    fun startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        Assert.assertNotNull(launcherPackage)
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT)

        // Launch the app
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = context.packageManager.getLaunchIntentForPackage(APP_PACKAGE)
        Assert.assertNotNull(
            "Failed to get launch intent for package ${APP_PACKAGE}. Is the app installed and does it have a launcher activity?",
            intent
        )
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT)
    }

    /**
     * Helper function to wait for a UI element with a specific testTag (content description).
     */
    private fun waitForTag(tag: String, timeout: Long = UI_TIMEOUT): UiObject2 {
        val element = device.wait(Until.findObject(By.desc(tag)), timeout)
        Assert.assertNotNull("Element with tag '$tag' not found.", element)
        return element
    }

    /**
     * Helper function to click a UI element identified by its testTag.
     */
    private fun clickByTag(tag: String, timeout: Long = UI_TIMEOUT) {
        waitForTag(tag, timeout).click()
    }

    /**
     * Helper function to scroll until a UI element with a specific testTag is visible.
     * This assumes the scrollable container allows vertical scrolling.
     */
    private fun scrollToTag(tag: String, scrollableContainerTag: String = MOVIE_LIST_SCREEN_TAG, maxSwipes: Int = 10): UiObject2 {
        val scrollable =
            UiScrollable(UiSelector().description(scrollableContainerTag)) // Match by content description
        scrollable.setAsVerticalList() // Assume vertical scrolling

        val targetSelector = UiSelector().description(tag)

        // First, try to find it without scrolling
        var element = device.findObject(By.desc(tag))
        if (element != null && !element.visibleBounds.isEmpty) {
            return element
        }

        // If not found or not visible, try scrolling
        Assert.assertTrue(
            "Could not scroll to element with tag '$tag'",
            scrollable.scrollIntoView(targetSelector)
        )

        element = device.wait(Until.findObject(By.desc(tag)), UI_TIMEOUT)
        Assert.assertNotNull("Element with tag '$tag' not found after scrolling.", element)
        // Explicitly cast element to UiObject2 to help compiler resolve isDisplayed()
        Assert.assertTrue(
            "Element with tag '$tag' is not displayed after scrolling.",
            !(element as UiObject2).visibleBounds.isEmpty
        )
        return element
    }


    @Test
    fun testMovieNavigationFlow() {
        // 1. Wait until the Movie List screen is visible
        waitForTag(MOVIE_LIST_SCREEN_TAG)
        println("Movie List screen is visible.")

        // 2. Scroll to and click on a specific movie item
        //    For this example, let's assume a movie with ID "123" exists
        //    and its testTag is "movie_item_123".
        //    In a real scenario, you might get this ID from test data.
        val specificMovieId = "1376434" // Example movie ID
        val specificMovieTag = "${MOVIE_ITEM_TAG_PREFIX}$specificMovieId"

        println("Scrolling to movie item with tag: $specificMovieTag")
        val movieItem = scrollToTag(specificMovieTag, MOVIE_LIST_SCREEN_TAG)
        movieItem.click()
        println("Clicked on movie item: $specificMovieTag")

        // 3. Verify that the Movie Details screen is shown
        waitForTag(MOVIE_DETAILS_SCREEN_TAG)
        println("Movie Details screen is visible.")

        // 4. Verify the correct title is shown on the Movie Details screen.
        //    This assumes the title element also has a specific testTag.
        //    The actual title text verification might require getting the text content.
        val movieDetailsTitle = waitForTag(MOVIE_DETAILS_TITLE_TAG)
        // For a more robust check, you'd compare movieDetailsTitle.text with expected title
        Assert.assertNotNull("Movie details title not found", movieDetailsTitle)
        println("Movie Details title is present: ${movieDetailsTitle.text}") // Example: Log the title

        // 5. Click the back button
        device.pressBack()
        println("Pressed back button.")

        // 6. Confirm the Movie List screen is displayed again
        waitForTag(MOVIE_LIST_SCREEN_TAG)
        println("Returned to Movie List screen.")
    }

    @Test
    fun testWatchTrailer_LaunchesAndDisplaysVideoPlayer() {
        // 1. Wait until the Movie List screen is visible
        waitForTag(MOVIE_LIST_SCREEN_TAG)
        println("Movie List screen is visible.")

        // 2. Scroll to and click on a specific movie item
        val specificMovieId = "1376434" // Example movie ID, ensure this movie has a trailer
        val specificMovieTag = "${MOVIE_ITEM_TAG_PREFIX}$specificMovieId"

        println("Scrolling to movie item with tag: $specificMovieTag")
        val movieItem = scrollToTag(specificMovieTag, MOVIE_LIST_SCREEN_TAG)
        movieItem.click()
        println("Clicked on movie item: $specificMovieTag")

        // 3. Verify that the Movie Details screen is shown
        waitForTag(MOVIE_DETAILS_SCREEN_TAG)
        println("Movie Details screen is visible.")

        // 4. Click the "Watch Trailer" button
        println("Clicking 'Watch Trailer' button with tag: $WATCH_TRAILER_BUTTON_TAG")
        clickByTag(WATCH_TRAILER_BUTTON_TAG)
        println("Clicked 'Watch Trailer' button.")

        // 5. Verify that the Video Player view is visible
        println("Waiting for video player with tag: $VIDEO_PLAYER_VIEW_TAG")
        val videoPlayerView = waitForTag(VIDEO_PLAYER_VIEW_TAG, UI_TIMEOUT * 2) // Longer timeout for video loading
        Assert.assertNotNull("Video player view not found.", videoPlayerView)
        Assert.assertTrue("Video player view is not displayed.", !videoPlayerView.visibleBounds.isEmpty)
        println("Video player view is visible.")

        // Optional: Add a small delay to visually confirm playback if running manually,
        // or implement more sophisticated playback state checks if possible.
         Thread.sleep(5000) // Example: wait 3 seconds

        // 6. Press back to close the video player (assuming it's an overlay or new screen)
        device.pressBack()
        println("Pressed back to close video player.")

        // 7. Verify we are back on the Movie Details screen
        waitForTag(MOVIE_LIST_SCREEN_TAG)
        println("Returned to Movie Details screen.")
    }
}
