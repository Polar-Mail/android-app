package app.polarmail.domain.interactor

import app.polarmail.core.util.AccountId
import app.polarmail.domain.model.Folder
import app.polarmail.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ObserveFoldersInteractor @Inject constructor(
    private val folderRepository: FolderRepository
) : ObserveInteractor<ObserveFoldersInteractor.Params, List<Folder>>() {

    data class Params(val id: AccountId)

    override fun createObservable(params: Params): Flow<List<Folder>> {
        return folderRepository.observeFolders(params.id)
    }

}