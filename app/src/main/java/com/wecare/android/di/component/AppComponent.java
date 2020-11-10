package com.wecare.android.di.component;

import android.app.Application;

import com.wecare.android.WeCareApplication;
import com.wecare.android.di.builder.ActivityBuilder;
import com.wecare.android.di.builder.AppActivityBuilder;
import com.wecare.android.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, AppActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(WeCareApplication app);

}
