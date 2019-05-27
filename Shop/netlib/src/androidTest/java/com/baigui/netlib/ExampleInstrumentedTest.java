package com.baigui.netlib;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.baigui.netlib.V2.http.HttpMethods;
import org.junit.Test;
import org.junit.runner.RunWith;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        SubscriberOnNextListener getTopMovieOnNext = new SubscriberOnNextListener() {
//            @Override
//            public void onNext(Object o) {
//
//            }
//        };
//        HttpMethods.getInstance().getTopMovie(new ProgressSubscriber(getTopMovieOnNext, appContext), 0, 10);
    }
}
