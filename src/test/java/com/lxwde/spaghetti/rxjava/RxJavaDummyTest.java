package com.lxwde.spaghetti.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class RxJavaDummyTest {

    @Test
    public void testFlowable() {

        String EVEN[] = {""}, ODD[] = {""};
        Flowable.just("Hello world").subscribe(System.out::println);

        Flowable.range(1, 5)
                .test()
                .assertResult(1, 2, 3, 4, 5);

        Flowable.create((FlowableEmitter<Integer> emitter) -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER).subscribe(System.out::println);

        Flowable.fromArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(i -> i != 10)
                .groupBy(i -> 0 == (i % 2) ? "EVEN" : "ODD")
                .subscribe(group -> {
                    group.subscribe(number -> {
                        if (group.getKey().toString().equals("EVEN")) {
                            EVEN[0] += number;
                        } else {
                            ODD[0] += number;
                        }
                    });
                });
        System.out.println(EVEN[0]);
        System.out.println(ODD[0]);
    }

    @Test
    public void testObservable() throws InterruptedException {
        final String[] result = {null};
        Observable<String> observable = Observable.just("Hello");
        observable.subscribe(s -> result[0] = s);
        Assert.assertEquals("Hello", result[0]);

        AtomicReference<String> ref = new AtomicReference<>();
        observable.subscribe(s -> ref.set(s));
        Assert.assertEquals("Hello", ref.get());

        result[0] = "";
        String[] letters = {"a", "b", "c", "d", "e"};
        observable = Observable.fromArray(letters);
        observable.subscribe(
          s -> result[0] += s,
          Throwable::printStackTrace,
          () -> result[0] += "_Completed"
        );

        Assert.assertEquals("abcde_Completed", result[0]);

        //
        result[0] = "";
        ConnectableObservable<Long> connectable
                = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        connectable.subscribe(i -> result[0] += i);
        assertFalse(result[0].equals("01"));

        connectable.connect();
        Thread.sleep(500);
        assertTrue(result[0].equals("01"));
    }

    @Test
    public void testFilterAndGroupBy() {
        String EVEN[] = {""}, ODD[] = {""};
        Observable.fromArray(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(i -> i != 10)
                .groupBy(i -> 0 == (i % 2) ? "EVEN" : "ODD")
                .subscribe(group ->
                        group.subscribe((number) -> {
                            if (group.getKey().toString().equals("EVEN")) {
                                EVEN[0] += number;
                            } else {
                                ODD[0] += number;
                            }
                        })
                );
        assertEquals("02468", EVEN[0]);
        assertEquals("13579", ODD[0]);

        System.out.println(EVEN[0]);
        System.out.println(ODD[0]);
    }

    @Test
    public void testZipWith() {
        List<String> words = Arrays.asList(
                "the",
                "quick",
                "brown",
                "fox",
                "jumped",
                "over",
                "the",
                "lazy",
                "dog"
        );
        Observable.fromIterable(words)
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count)->String.format("%2d. %s", count, string))
                .subscribe(System.out::println);
    }

    @Test
    public void testSubscribeOn() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(System.out::println);

        Observable.just(5, 6, 7, 8, 9)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(System.out::println);
    }
}
