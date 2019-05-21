package com.example.smartjakapp.presenter

import android.util.Log
import com.example.smartjakapp.network.ApiClient
import com.example.smartjakapp.view.PoliceView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PolicePresenter(
    private val mainView: PoliceView.MainView
) : PoliceView.PresenterView {

    private var disposable: Disposable? = null

    override fun loadData() {
        disposable = ApiClient.instance
            .getListPolice()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()?.data
            }
            .subscribe(
                { result ->
                    mainView.getData(result)
                },
                { error -> Log.e("Error Response", "Error $error") }
            )
    }

    override fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}