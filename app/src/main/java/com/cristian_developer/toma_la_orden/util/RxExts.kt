package com.cristian_developer.toma_la_orden.util

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.cristian_developer.toma_la_orden.data.net.ResponseBody
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class LifeDisposable(owner: LifecycleOwner) : LifecycleObserver {

    private val dis: CompositeDisposable = CompositeDisposable()

    init {
        owner.lifecycle.addObserver(this)
    }

    infix fun add(disposable: Disposable) {
        dis.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clear() {
        dis.clear()
    }
}

fun <T> Observable<T>.applySchedulers(): Observable<T> = compose {
    it.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> validateResponse(res: ResponseBody<T>): Observable<T> = Observable.create<T> {
    when {
        res.success -> it.onNext(res.data)
        res.err == null -> it.onComplete()
        else -> throw Throwable(res.err)
    }
}