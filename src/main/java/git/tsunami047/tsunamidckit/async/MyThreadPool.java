package git.tsunami047.tsunamidckit.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *@Author: natsumi
 *@CreateTime: 2023-06-01  18:16
 *@Description: 我的线程池
 */
public class MyThreadPool {

    private final ExecutorService executor;
    private final ExecutorService singleExecutor;
    private volatile static MyThreadPool myThreadPool;

    public static MyThreadPool getThreadPool(){
        if (myThreadPool == null) {
            synchronized (MyThreadPool.class) {
                if (myThreadPool == null) {
                    myThreadPool = new MyThreadPool();
                }
            }
        }
        return myThreadPool;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public ExecutorService getSingleExecutor() {
        return singleExecutor;
    }

    private MyThreadPool() {
        int parallelism = Runtime.getRuntime().availableProcessors();
        executor = Executors.newWorkStealingPool(parallelism);
        singleExecutor = Executors.newSingleThreadExecutor();

    }

    public static void singleExecute(Runnable runnable){
        getThreadPool().getSingleExecutor().execute(runnable);
    }

    public static Future<?> singleExecute(Callable<?> callable){
        return getThreadPool().getSingleExecutor().submit(callable);
    }

    public static void asyncExecute(Runnable runnable){
        getThreadPool().getExecutor().execute(runnable);
    }

    public static Future<?> asyncExecute(Callable<?> callable){
        return getThreadPool().getExecutor().submit(callable);
    }


}
