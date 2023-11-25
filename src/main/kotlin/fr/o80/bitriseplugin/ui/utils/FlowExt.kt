package fr.o80.bitriseplugin.ui.utils

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.throttleFirst(waitMillis: Int) = flow {
    coroutineScope {
        var nextMillis = 0L
        collect {
            val current = System.currentTimeMillis()
            if (nextMillis < current) {
                nextMillis = current + waitMillis
                emit(it)
            }
        }
    }
}
