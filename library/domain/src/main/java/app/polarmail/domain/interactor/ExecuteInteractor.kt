package app.polarmail.domain.interactor

abstract class ExecuteInteractor<in Params> {

    suspend operator fun invoke(params: Params) {
        return doWork(params)
    }

    protected abstract suspend fun doWork(params: Params)

}