//package com.wecare.android.ui.main.home;
//
//import com.wecare.android.data.model.api.responses.MainServiceModel;
//
//import java.util.ArrayList;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import dagger.Module;
//import dagger.Provides;
//
//@Module
//public class ServicesFragmentModule {
//
//    @Provides
//    ServicesAdapter provideServicesAdapter() {
//        return new ServicesAdapter(new ArrayList<MainServiceModel>());
//    }
//
//    @Provides
//    LinearLayoutManager provideLinearLayoutManager(ServicesFragment fragment) {
//        return new LinearLayoutManager(fragment.getActivity());
//    }
//
//}
