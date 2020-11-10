
package com.wecare.android.ui.splash;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.responses.LookupResponse;
import com.wecare.android.data.model.api.responses.PageContentResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;



public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(DataManagerFlavour dataManager,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void fetchLookUps() {
        getCompositeDisposable().add(getDataManager()
                .getLookups()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<LookupResponse>() {
                    @Override
                    public void accept(LookupResponse lookupResponse) throws Exception {
                        if (lookupResponse.isSuccess()){
                        getDataManager().setLookupsModel(lookupResponse);
                        getNavigator().lookupsFinishedSuccessfully();}
                        else
                            getNavigator().handleError(lookupResponse.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }

    public void getPages(){
        getCompositeDisposable().add(getDataManager()
                .getPagesRemote()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<PageContentResponse>>() {
                    @Override
                    public void accept(List<PageContentResponse> pageContentResponses) throws Exception {
                    getDataManager().setPages(pageContentResponses);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
    }

    public void decideNextActivity() {
        if (!getDataManager().isIntroViewed()) {
            getNavigator().openIntroActivity();
            return;
        }
        if (getDataManager().getCurrentUserLoggedInMode()
                == DataManagerFlavour.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
            getNavigator().openLoginActivity();
        } else {
            getNavigator().openMainActivity();
        }
    }

}
