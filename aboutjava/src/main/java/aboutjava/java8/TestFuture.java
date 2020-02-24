package aboutjava.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: springBootPractice
 * @description: 异步编程Java8
 * @author: hu_pf
 * @create: 2019-07-26 10:02
 **/
public class TestFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


//        //获得返回值
//        getReturnValue();

//        //获取自定义的返回值
//        getReturnValueCustom();

//        //顺序执行异步任务
//        exeSequen();

//        //组合CompletableFuture
//        commbinCompletableFuture();

//        //组合多个CompletableFuture
//        commbinManyCompletableFuture();

        //异常处理
        exceptionHandl();
    }

    public static Future getNoArgsFuture(){
        CompletableFuture<String> noArgsFuture = new CompletableFuture<>();
        return noArgsFuture;
    }

    public static Future getFutureNoReturn(){
        CompletableFuture noReturn = CompletableFuture.runAsync(()->{
            //执行逻辑,无返回值
        });
        return noReturn;
    }

    public static Future getFuntureHasReturn(){

        CompletableFuture<String> hasReturn = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "hasReturn";
            }
        });

        return hasReturn;
    }

    public static Future getFuntureHasReturnLambda(){
        CompletableFuture<String> hasReturnLambda = CompletableFuture.supplyAsync(TestFuture::get);
        return hasReturnLambda;
    }

    private static String get() {
        System.out.println("Begin Invoke getFuntureHasReturnLambda");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }
        System.out.println("End Invoke getFuntureHasReturnLambda");
        return "hasReturnLambda";
    }

    //获得异步返回值结果
    private static void getReturnValue() throws ExecutionException, InterruptedException {
        CompletableFuture<String> funtureHasReturnLambda = (CompletableFuture<String>) getFuntureHasReturnLambda();
        System.out.println("Main Method Is Invoking");
        funtureHasReturnLambda.get();
        System.out.println("Main Method End");
    }

    //自定义返回值
    private static void getReturnValueCustom() throws ExecutionException, InterruptedException {
        CompletableFuture<String> funtureHasReturnLambda = (CompletableFuture<String>) getFuntureHasReturnLambda();
        System.out.println("Main Method Is Invoking");
        new Thread(()->{
            System.out.println("Thread Is Invoking ");
            try {
                Thread.sleep(1000);
                funtureHasReturnLambda.complete("custome value");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread End ");
        }).run();
        String value = funtureHasReturnLambda.get();
        System.out.println("Main Method End value is "+ value);
    }

    //顺序执行异步任务
    private static void exeSequen() throws ExecutionException, InterruptedException {

        //thenApply  可获取到前一个任务的返回值,也有返回值
        CompletableFuture<String> seqFutureOne = CompletableFuture.supplyAsync(()-> "seqFutureOne");
        CompletableFuture<String> seqFutureTwo = seqFutureOne.thenApply(name -> name + " seqFutureTwo");
        System.out.println(seqFutureTwo.get());

        //thenAccept  可获取到前一个任务的返回值,但是无返回值
        System.out.println("-------------");
        CompletableFuture<Void> thenAccept = seqFutureOne
                .thenAccept(name -> System.out.println(name + "thenAccept"));
        System.out.println(thenAccept.get());

        //thenRun 获取不到前一个任务的返回值,也无返回值
        System.out.println("-------------");
        CompletableFuture<Void> thenRun = seqFutureOne.thenRun(() -> {
            System.out.println("thenRun");
        });
        System.out.println(thenRun.get());

        //thenApply和thenApplyAsync的区别
        System.out.println("-------------");
        CompletableFuture<String> supplyAsyncWithSleep = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "supplyAsyncWithSleep Thread Id : " + Thread.currentThread();
        });
        CompletableFuture<String> thenApply = supplyAsyncWithSleep
                .thenApply(name -> name + "------thenApply Thread Id : " + Thread.currentThread());
        CompletableFuture<String> thenApplyAsync = supplyAsyncWithSleep
                .thenApplyAsync(name -> name + "------thenApplyAsync Thread Id : " + Thread.currentThread());
        System.out.println("Main Thread Id: "+ Thread.currentThread());
        System.out.println(thenApply.get());
        System.out.println(thenApplyAsync.get());
        System.out.println("-------------No Sleep");
        CompletableFuture<String> supplyAsyncNoSleep = CompletableFuture.supplyAsync(()->{
            return "supplyAsyncNoSleep Thread Id : " + Thread.currentThread();
        });
        CompletableFuture<String> thenApplyNoSleep = supplyAsyncNoSleep
                .thenApply(name -> name + "------thenApply Thread Id : " + Thread.currentThread());
        CompletableFuture<String> thenApplyAsyncNoSleep = supplyAsyncNoSleep
                .thenApplyAsync(name -> name + "------thenApplyAsync Thread Id : " + Thread.currentThread());
        System.out.println("Main Thread Id: "+ Thread.currentThread());
        System.out.println(thenApplyNoSleep.get());
        System.out.println(thenApplyAsyncNoSleep.get());
    }

    //组合CompletableFuture
    public static void commbinCompletableFuture() throws ExecutionException, InterruptedException {
        //thenCompose() thenCombine()
        //thenCompose()
        CompletableFuture<String> thenComposeComplet = getTastOne().thenCompose(s -> getTastTwo(s));
        System.out.println(thenComposeComplet.get());

        //thenApply
        CompletableFuture<CompletableFuture<String>> thenApply = getTastOne()
                .thenApply(s -> getTastTwo(s));

        System.out.println(thenApply.get().get());

        //thenCombine()
        CompletableFuture<Integer> thenComposeOne = CompletableFuture.supplyAsync(() -> 192);
        CompletableFuture<Integer> thenComposeTwo = CompletableFuture.supplyAsync(() -> 196);
        CompletableFuture<Integer> thenComposeCount = thenComposeOne
                .thenCombine(thenComposeTwo, (s, y) -> s + y);
        System.out.println(thenComposeCount.get());
    }

    public static CompletableFuture<String> getTastOne(){
        return CompletableFuture.supplyAsync(()-> "topOne");
    }


    public static CompletableFuture<String> getTastTwo(String s){
        return CompletableFuture.supplyAsync(()-> s + "  topTwo");
    }

    //组合多个CompletableFuture
    public static void commbinManyCompletableFuture() throws ExecutionException, InterruptedException {
        //allOf()  anyOf()

        //allOf()
        CompletableFuture<Integer> one = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<Integer> two = CompletableFuture.supplyAsync(() -> 2);
        CompletableFuture<Integer> three = CompletableFuture.supplyAsync(() -> 3);
        CompletableFuture<Integer> four = CompletableFuture.supplyAsync(() -> 4);
        CompletableFuture<Integer> five = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> six = CompletableFuture.supplyAsync(() -> 6);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(one, two, three, four, five, six);
        voidCompletableFuture.thenApply(v->{
            return Stream.of(one,two,three,four, five, six)
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        }).thenAccept(System.out::println);

        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            System.out.println("voidCompletableFuture1");
        });

        CompletableFuture<Void> voidCompletableFutur2 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }
            System.out.println("voidCompletableFutur2");
        });

        CompletableFuture<Void> voidCompletableFuture3 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            System.out.println("voidCompletableFuture3");
        });

        CompletableFuture<Object> objectCompletableFuture = CompletableFuture
                .anyOf(voidCompletableFuture1, voidCompletableFutur2, voidCompletableFuture3);
        objectCompletableFuture.get();
    }

    //异常处理
    public static void exceptionHandl() throws ExecutionException, InterruptedException {
//        //异常演示
//        CompletableFuture.supplyAsync(()->{
//            //发生异常
//            int i = 10/0;
//            return "Success";
//        }).thenRun(()-> System.out.println("thenRun"))
//        .thenAccept(v -> System.out.println("thenAccept"));
//
//        CompletableFuture.runAsync(()-> System.out.println("CompletableFuture.runAsync"));
//
//        //exceptionally()处理异常
//
//        CompletableFuture<String> exceptionally = CompletableFuture.supplyAsync(() -> {
//            //发生异常
//            int i = 10 / 0;
//            return "Success";
//        }).exceptionally(e -> {
//            System.out.println(e);
//            return "Exception has Handl";
//        });
//        System.out.println(exceptionally.get());

        //handle()处理异常

        System.out.println("-------有异常-------");
        CompletableFuture.supplyAsync(()->{
            //发生异常
            int i = 10/0;
            return "Success";
        }).handle((response,e)->{
            System.out.println("Exception:" + e);
            System.out.println("Response:" + response);
            return response;
        });

        System.out.println("-------无异常-------");
        CompletableFuture.supplyAsync(()->{
            return "Sucess";
        }).handle((response,e)->{
            System.out.println("Exception:" + e);
            System.out.println("Response:" + response);
            return response;
        });

    }
}
