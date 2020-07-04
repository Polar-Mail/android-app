package app.polarmail.core.net

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatcherProvider(testDispatcher: TestCoroutineDispatcher) : DispatcherProvider {
    override val io: CoroutineDispatcher = testDispatcher
    override val main: CoroutineDispatcher = testDispatcher
    override val computation: CoroutineDispatcher = testDispatcher
}