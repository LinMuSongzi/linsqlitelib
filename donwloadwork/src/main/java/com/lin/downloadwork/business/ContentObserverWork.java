package com.lin.downloadwork.business;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by linhui on 2017/12/14.
 */
final class ContentObserverWork extends ContentObserver implements IObserverWork,Subscription {

    private Subscription subscription;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    private ContentObserverWork(Handler handler) {
        super(handler);
    }


    static IObserverWork create(Subscription subscription,String threadName,Runnable firstRunnable){
        HandlerThread handlerThread = new HandlerThread(threadName);
        handlerThread.start();
        Handler contentObserverhandler = new Handler(handlerThread.getLooper());
        contentObserverhandler.post(firstRunnable);
        return new ContentObserverWork(contentObserverhandler).setSubscription(subscription);
    }

    private IObserverWork setSubscription(Subscription subscription){
        this.subscription = subscription;
        return this;
    }

    @Override
    public void onChange(boolean selfChange) {
        if(subscription!=null){
            subscription.onChange(selfChange);
        }else{
            super.onChange(selfChange);
        }
    }

    @Override
    public ContentObserver getContentObserver() {
        return this;
    }
}
