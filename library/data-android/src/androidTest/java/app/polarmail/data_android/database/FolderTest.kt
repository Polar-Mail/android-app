package app.polarmail.data_android.database

import app.polarmail.data.dao.AccountDao
import app.polarmail.data.dao.FolderDao
import app.polarmail.data.entitiy.AccountEntity
import app.polarmail.data.entitiy.FolderEntity
import app.polarmail.domain.model.FolderType
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.threeten.bp.Instant

@ExperimentalCoroutinesApi
class FolderTest : DatabaseTest() {

    private val accountId = 1L

    lateinit var accountDao: AccountDao
    lateinit var dao: FolderDao

    @Before
    override fun setUp() {
        super.setUp()
        accountDao = db.getAccountDao()
        dao = db.getFolderDao()

        runBlocking {
            insertAccount()
        }
    }

    override fun tearDown() {
        super.tearDown()
        testScope.cleanupTestCoroutines()
    }

    private suspend fun insertAccount() {
        val account = getAccount()
        accountDao.insert(account)
    }

    private fun getAccount(): AccountEntity = AccountEntity(
        accountId,
        Instant.parse("2007-12-23T10:15:30.000Z"),
        "user",
        "pass",
        "host",
        9000,
        "avatar",
        true
    )

    @Test
    fun testList() = testScope.runBlockingTest {
        // Given
        val folder = factoryFolder()
        dao.insert(folder)

        // When
        val result = dao.getFolders(accountId)

        // Then
        Truth.assertThat(result).isEqualTo(listOf(folder))
    }

    @Test
    fun testDeleteAccount() = testScope.runBlockingTest {
        // Given
        val folder = factoryFolder()
        dao.insert(folder)

        // When
        accountDao.deleteAccountById(accountId)
        val result = dao.getFolders(accountId)

        // Then
        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun testDeleteFolder() = testScope.runBlockingTest {
        // Given
        val folder = factoryFolder()
        dao.insert(folder)

        // When
        dao.deleteEntity(folder)
        val result = dao.getFolders(accountId)

        // Then
        Truth.assertThat(result).isEmpty()
    }

    private fun factoryFolder(): FolderEntity =
        FolderEntity(
            accountId,
            "folder",
            1L,
            FolderType.INBOX,
            10,
            Instant.parse("2007-12-23T10:15:30.000Z"),
            1
        )

}