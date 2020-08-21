package app.polarmail.data_android.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException

abstract class DatabaseTest {

    protected lateinit var db: PolarMailDatabase

    @get:Rule()
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected val testScope = TestCoroutineScope()

    @Before
    open fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PolarMailDatabase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    open fun tearDown() {
        db.close()
    }

}