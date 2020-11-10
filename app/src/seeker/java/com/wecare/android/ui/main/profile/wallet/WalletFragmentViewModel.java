package com.wecare.android.ui.main.profile.wallet;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.WalletResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;


public class WalletFragmentViewModel extends BaseViewModel<WalletFragmentNavigator> {


    public WalletFragmentViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void getWallet() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getSeekerWallets()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<WalletResponse>>() {
                    @Override
                    public void accept(List<WalletResponse> walletResponse) throws Exception {
                        getNavigator().walletFetchedSuccessfully(walletResponse);
                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void showWallets() {
        getNavigator().showWallets();
    }

    public void paymentHistoryClicked() {
        getNavigator().paymentHistoryClicked();
    }
}
