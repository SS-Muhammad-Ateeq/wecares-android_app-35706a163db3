
package com.wecare.android.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by amitshekhar on 07/07/17.
 */

public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
