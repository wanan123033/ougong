package com.baigui.netlib;

import com.baigui.netlib.V2.http.HttpMethods;
import io.reactivex.functions.Consumer;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class V2Test {
    @Test
    public void getContent() {
        Consumer consumer = new Consumer<List<Contributor>>(){

            @Override
            public void accept(List<Contributor> contributors) throws Exception {
                System.out.print("hello");
            }
        };

//        try {
//            HttpMethods.getInstance().getTopMovie(consumer, "square", "retrofit");
//        } catch (IOException e) {
//
//        }
    }
}
