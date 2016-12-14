package com.jack.productsearch.connection;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jack on 3/10/16.
 *
 * http://stackoverflow.com/questions/22066481/rxjava-can-i-use-retry-but-with-delay
 */
public class RetryWithDelay implements
        Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;
    private TimeUnit timeUnit;

    public RetryWithDelay(final int maxRetries, final int retryDelayMillis, TimeUnit timeUnit) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.timeUnit = timeUnit;
        this.retryCount = 0;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                if (++retryCount < maxRetries) {
                    // When this Observable calls onNext, the original
                    // Observable will be retried (i.e. re-subscribed).
                    return Observable.timer(retryDelayMillis, timeUnit);
                }
                // Max retries hit. Just pass the error along.
                return Observable.error(throwable);
            }
        });
    }
}
