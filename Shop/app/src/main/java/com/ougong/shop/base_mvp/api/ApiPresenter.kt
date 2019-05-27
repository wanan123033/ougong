package com.ougong.shop.base_mvp.api

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.support.annotation.CallSuper
import com.baigui.commonlib.utils.LogUtils
import com.ougong.shop.base_mvp.fetcher.Fetcher
import io.armcha.ribble.domain.fetcher.Status
import io.armcha.ribble.domain.fetcher.result_listener.RequestType
import io.armcha.ribble.domain.fetcher.result_listener.ResultListener
import io.armcha.ribble.presentation.base_mvp.base.BaseContract
import io.armcha.ribble.presentation.base_mvp.base.BasePresenter
import io.armcha.ribble.presentation.utils.extensions.log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Chatikyan on 04.08.2017.
 */

abstract class ApiPresenter<VIEW : BaseContract.View> : BasePresenter<VIEW>(), ResultListener {

    protected var fetcher: Fetcher = Fetcher()

    private val TYPE_NONE = RequestType.TYPE_NONE
    protected val AUTH: RequestType = RequestType.AUTH
    protected val POPULAR_SHOTS = RequestType.POPULAR_SHOTS
    protected val RECENT_SHOTS = RequestType.RECENT_SHOTS
    protected val FOLLOWINGS_SHOTS = RequestType.FOLLOWINGS_SHOTS
    protected val LIKED_SHOTS = RequestType.LIKED_SHOTS
    protected val COMMENTS = RequestType.COMMENTS
    protected val LIKE = RequestType.LIKE
    protected val SUCCESS = Status.SUCCESS
    protected val LOADING = Status.LOADING
    protected val ERROR = Status.ERROR
    protected val EMPTY_SUCCESS = Status.EMPTY_SUCCESS


    protected infix fun RequestType.statusIs(status: Status) = fetcher.getRequestStatus(this) == status

    protected val RequestType.status
        get() = fetcher.getRequestStatus(this)

    fun <TYPE> fetch(flowable: Flowable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(flowable, requestType, this, success)
    }

    fun <TYPE> fetch(observable: Observable<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(observable, requestType, this, success)
    }

    fun <TYPE> fetch(single: Single<TYPE>,
                     requestType: RequestType = TYPE_NONE, success: (TYPE) -> Unit) {
        fetcher.fetch(single, requestType, this, success)
    }

    fun complete(completable: Completable,
                 requestType: RequestType = TYPE_NONE, success: () -> Unit = {}) {
        fetcher.complete(completable, requestType, this, success)
    }
//
//    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
//    fun onStop() {
//        LogUtils.e("lifeSicle stop",this)
//        fetcher.clear()
//    }


    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    fun onStop() {
        LogUtils.e("lifeSicle stop",this)
        fetcher.clear()
    }

    @CallSuper
    override fun onPresenterDestroy() {
        super.onPresenterDestroy()

        LogUtils.e("onPresenterDestroy",this)
        fetcher.clear()
    }

    @CallSuper
    override fun onRequestStart(requestType: RequestType) {
        onRequestStart()
    }

    @CallSuper
    override fun onRequestError(requestType: RequestType, errorMessage: String?) {
        onRequestError(errorMessage)
    }
}