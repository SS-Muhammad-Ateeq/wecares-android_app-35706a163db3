package com.wecare.android.ui.template;

import com.wecare.android.data.DataManagerFlavour;
import com.wecare.android.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class TemplateActivityModule {

    //no need use factory class.
    @Provides
    TemplateViewModel provideHomeViewModel(DataManagerFlavour dataManager, SchedulerProvider schedulerProvider) {
        return new TemplateViewModel(dataManager, schedulerProvider);
    }


//    @Provides
//    TemplateAdapter provideTemplateAdapter() {
//        return new TemplateAdapter(new ArrayList<TemplateResponse>());
//    }
//
//    @Provides
//    LinearLayoutManager provideLinearLayoutManager(TemplateFragment fragment) {
//        return new LinearLayoutManager(fragment.getActivity());
//    }
//
//    @Provides
//    ViewModelProvider.Factory provideTemplateViewModel(TemplateViewModel templateViewModel) {
//        return new ViewModelProviderFactory<>(templateViewModel);
//    }
}
