package com.vitoksmile.movieslist

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.vitoksmile.movieslist.ui.theme.MoviesListTheme
import kotlinx.coroutines.test.runTest
import org.junit.Rule

open class BaseTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    fun runUiTest(
        content: @Composable () -> Unit,
        testBody: ComposeContentTestRule.() -> Unit
    ) = runTest {
        composeTestRule.setContent {
            MoviesListTheme {
                content()
            }
        }

        testBody(composeTestRule)
    }

    fun stringResource(@StringRes resId: Int): String {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.getString(resId)
    }

    fun stringResource(@StringRes resId: Int, vararg formatArgs: Any): String {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        return context.getString(resId, *formatArgs)
    }
}
