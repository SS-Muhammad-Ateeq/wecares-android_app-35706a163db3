package com.wecare.android.ui.main.profile.wallet;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.GiverWalletResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import androidx.databinding.ObservableBoolean;
import io.reactivex.functions.Consumer;

public class WalletFragmentViewModel extends BaseViewModel<WalletFragmentNavigator> {


    public WalletFragmentViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    private final ObservableBoolean isExpanded = new ObservableBoolean(false);

    public ObservableBoolean getIsExpanded() {
        return isExpanded;
    }

    public void getWallet(){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getGiverWallet()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<GiverWalletResponse>() {
                    @Override
                    public void accept(GiverWalletResponse walletResponse) throws Exception {
                        if (walletResponse.isSuccess()) {
                            getNavigator().walletFetchedSuccessfully(walletResponse);
                        } else
                            getNavigator().handleError(walletResponse.getError().getMessage());

                        setIsLoading(false);

                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                    }
                }));
    }

    public void paymentHistoryClicked(){
        getNavigator().paymentHistoryClicked();
    }


    public void expandTableClicked(){
        isExpanded.set(!isExpanded.get());
        getNavigator().isExpanded(isExpanded.get());
    }
}
