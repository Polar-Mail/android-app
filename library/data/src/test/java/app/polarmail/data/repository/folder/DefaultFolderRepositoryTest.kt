package app.polarmail.data.repository.folder

import app.polarmail.core.util.AccountId
import app.polarmail.core.util.FolderId
import app.polarmail.data.maper.toFolderEntity
import app.polarmail.domain.model.Folder
import app.polarmail.domain.model.FolderType
import app.polarmail.domain.repository.FolderRepository
import com.google.common.truth.Truth
import dev.olog.flow.test.observer.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.threeten.bp.Instant

@ExperimentalCoroutinesApi
class DefaultFolderRepositoryTest {

    lateinit var folderRepository: FolderRepository

    private val folderLocalDataSource: FolderLocalDataSource = mockk(relaxed = true)

    private val testScope = TestCoroutineScope()

    @Before
    fun setUp() {
        folderRepository = DefaultFolderRepository(folderLocalDataSource)
    }

    @After
    fun tearDown() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `Test observe folders`() = testScope.runBlockingTest {
        // Given
        val accountId = AccountId(10L)
        val folders = factoryFolders(accountId)
        every { folderLocalDataSource.observeFolders(accountId.id) } returns flow {
            emit(folders.map { it.toFolderEntity() })
        }

        // When
        val result = folderRepository.observeFolders(accountId)

        // Then
        result.test(this) {
            assertValue(folders)
        }
    }

    @Test
    fun `Test get folder`() = testScope.runBlockingTest {
        // Given
        val accountId = AccountId(10L)
        val folders = factoryFolders(accountId)
        coEvery { folderLocalDataSource.getFolders(accountId.id) } returns folders.map { it.toFolderEntity() }

        // When
        val result = folderRepository.getFolders(accountId)

        // Then
        Truth.assertThat(result).isEqualTo(folders)
    }

    private fun factoryFolders(accountId: AccountId): List<Folder> = listOf(
        Folder(
            FolderId(1L),
            "Inbox",
            accountId.id,
            FolderType.INBOX,
            10,
            Instant.now(),
            1
        ),
        Folder(
            FolderId(2L),
            "Sent",
            accountId.id,
            FolderType.OUTBOX,
            0,
            Instant.now(),
            2
        )
    )

}