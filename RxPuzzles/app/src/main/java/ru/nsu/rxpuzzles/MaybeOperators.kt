package ru.nsu.rxpuzzles

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

object MaybeOperators {

    object Task1 {

        /**
         * Вернуть пустой Maybe, который мгновенно завершит работу.
         */
        fun solve(): Maybe<String> = Maybe.empty()
    }

    object Task2 {

        /**
         * Преобразовать результат получаемый из [worker] в Maybe поток.
         */
        fun solve(worker: Worker): Maybe<String> = Maybe.create { emitter ->
            worker.setResultListener { maybeStr ->
                if (maybeStr != null) {
                    emitter.onSuccess(maybeStr)
                } else {
                    emitter.onComplete()
                }
            }
        }

        interface Worker {

            fun setResultListener(listener: (String?) -> Unit)
        }
    }

    object Task3 {

        /**
         * Завершать поток, если в [source] будет отрицательное число.
         * Возвращать значение, если оно будет положительным.
         */

        @SuppressLint("CheckResult")
        fun solve(source: Single<Int>): Maybe<Int> = Maybe.create { emitter ->
            source.subscribe({
                if (it < 0) {
                    emitter.onComplete()
                } else {
                    emitter.onSuccess(it)
                }
            }, {

            })
        }
    }

    object Task4 {

        /**
         * Если [first] поток не вернет значение, то переключить на [second] поток.
         */
        fun solve(first: Maybe<Int>, second: Single<Int>): Single<Int> = first.switchIfEmpty(second)
    }
}