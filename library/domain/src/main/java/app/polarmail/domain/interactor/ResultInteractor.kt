package app.polarmail.domain.interactor

abstract class ResultInteractor<in Params, out T> {

    suspend operator fun invoke(params: Params): T {
        return doWork(params)
    }

    protected abstract suspend fun doWork(params: Params): T

}