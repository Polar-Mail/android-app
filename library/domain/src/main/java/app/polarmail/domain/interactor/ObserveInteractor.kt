package app.polarmail.domain.interactor

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

abstract class ObserveInteractor<in Params, out T> {

    private val channel = ConflatedBroadcastChannel<Params>()

    operator fun invoke(params: Params) = channel.sendBlocking(params)

    protected abstract fun createObservable(params: Params): Flow<T>

    fun observe(): Flow<T> = channel.asFlow()
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }

}