package com.wecare.android.ui.main.rating;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.data.model.api.requests.RatingRequest;
import com.wecare.android.data.model.api.responses.RatingResponse;
import com.wecare.android.ui.base.BaseViewModel;
import com.wecare.android.utils.JSONBuilderFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class RatingMainViewModel extends BaseViewModel<RatingMainNavigator> {



    public RatingMainViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }


    public void doRating(RatingRequest ratingRequest) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doRating(JSONBuilderFlavour.getCommonRequestParams(ratingRequest))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<RatingResponse>() {
                    @Override
                    public void accept(@NonNull RatingResponse response) throws Exception {
                        setIsLoading(false);
                        if (response.isSuccess())
                            getNavigator().ratingDoneSuccessfully();
                        else
                            getNavigator().handleError(response.getError().getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        setIsLoading(false);
                        getNavigator().showCommonError();
                    }
                }));
    }


}
