package com.example.smartjakapp.hospital

import android.util.Log
import com.example.smartjakapp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Bencoleng on 21/05/2019.
 */
class HospitalPresenter(private val mainView: HospitalView.MainView) : HospitalView.PresenterView {

    private var disposable: Disposable? = null

    override fun loadData() {
        disposable = ApiClient.instance.getListHospital()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()?.features
            }
            .subscribe(
                { result -> mainView.getData(result) },
                { error -> Log.e("Error", "Error res : $error") }
            )
    }

    override fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}