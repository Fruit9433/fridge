package com.example.afp.myapplication.thread;

import android.arch.lifecycle.MutableLiveData;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.example.afp.myapplication.DataBusiness.Dish;
import com.example.afp.myapplication.DataBusiness.Product;
import com.example.afp.myapplication.Recycler.RecylerAdapter;
import com.example.afp.myapplication.db.App;
import com.example.afp.myapplication.db.AppDataBase;
import com.example.afp.myapplication.db.ProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ThreadPoolManager {
    private static ThreadPoolManager sInstance = null;
    private static final int DEFAULT_THREAD_POOL_SIZE = 4;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;
    private MutableLiveData<List<Product>> mProductsLivedata;
    private MutableLiveData<List<Product>> mShouldBuysProductsLivedata;
    private MutableLiveData<List<Dish>> mDishesLivedata;
    private List<Product> mProductListToUpdate;
    private final ExecutorService mExecutorService;
    private final BlockingQueue<Runnable> mTaskQueue;
    private List<Action> mTaskList;
    private final ProductDao productDao;



    // The class is used as a singleton
    static {
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        sInstance = new ThreadPoolManager();
    }

    // Made constructor private to avoid the class being initiated from outside
    private ThreadPoolManager() {
        // initialize a queue for the thread pool. New tasks will be added to this queue
        mTaskQueue = new LinkedBlockingQueue<>();

        mTaskList = new CopyOnWriteArrayList<>();

        mProductsLivedata = new MutableLiveData<>();

        mDishesLivedata = new MutableLiveData<>();

        mShouldBuysProductsLivedata = new MutableLiveData<>();

        mProductListToUpdate = new CopyOnWriteArrayList<>();

        productDao = App.getInstance().getDatabase().productDao();

        Log.e("Cores","Available cores: " + NUMBER_OF_CORES);

        /*
            TODO: You can choose between a fixed sized thread pool and a dynamic sized pool
            TODO: Comment one and uncomment another to see the difference.
         */
        //mExecutorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE, new BackgroundThreadFactory());
        mExecutorService = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES*2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mTaskQueue, new BackgroundThreadFactory());
    }

    public static ThreadPoolManager getsInstance() {
        if (sInstance == null) {
            sInstance = new ThreadPoolManager();
            return sInstance;
        }
        return sInstance;
    }

    public void addChanges(Product product) {
        mProductListToUpdate.add(product);
    }

    public void deleteChanges(Product product) {
        for (Product product1: mProductListToUpdate) {
            if (product1.equals(product)) {
                mProductListToUpdate.remove(product);
            }
        }
    }

    public void acceptChanges() {
        if (mProductListToUpdate.isEmpty()) {
            return;
        }
        Disposable d = Completable.fromAction(() -> productDao.update(mProductListToUpdate))
                .subscribeOn(Schedulers.from(mExecutorService))
                .subscribe(this::onComplete, this::onError);
    }


    public void insertProduct(Product product) {
        Disposable d = Completable.fromAction(() -> productDao.insert(product))
                .subscribeOn(Schedulers.from(mExecutorService))
                .subscribe(this::onComplete,this::onError);
    }

    public void insertDish(Dish dish) {
        Disposable d = Completable.fromAction(() -> productDao.insert(dish))
                .subscribeOn(Schedulers.from(mExecutorService))
                .subscribe(this::onComplete,this::onError);
    }

    public void deleteDish(Dish dish) {
        Disposable d = Completable.fromAction(() -> productDao.delete(dish))
                .subscribeOn(Schedulers.computation())
                .subscribe(this::onComplete,this::onError);
    }

    public void updateProduct(Product product) {
        Disposable d = Completable.fromAction(() -> productDao.update(product))
                .subscribeOn(Schedulers.from(mExecutorService))
                .subscribe(this::onComplete, this::onError);
    }

    public void deleteProduct(Product product) {
        Disposable d = Completable.fromAction(() -> productDao.delete(product))
                .subscribeOn(Schedulers.from(mExecutorService))
                .subscribe(this::onComplete, this::onError);
    }

    public void updateDish(Dish dish) {
        Disposable d = Completable.fromAction(() -> productDao.update(dish))
                .subscribeOn(Schedulers.from(mExecutorService))
                .subscribe(this::onComplete, this::onError);
    }

    public void getAllShouldBuyProducts(){
        Disposable disposable = productDao.getShouldBuyProducts()
                .subscribeOn(Schedulers.from(mExecutorService))
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(this::onShouldBuyProductsReceived,this::onError);
    }

    public void getAllBoughtProducts(){
        Disposable disposable = productDao.getBoughtProducts()
                .subscribeOn(Schedulers.from(mExecutorService))
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(this::onProductsReceived,this::onError);
    }

    public void getAllDishes(){
        Disposable disposable = productDao.getAllDishes()
                .subscribeOn(Schedulers.from(mExecutorService))
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(this::onDishesReceived,this::onError);
    }

    private void onError(Throwable throwable) {
        Log.d("error", throwable.getMessage());
    }

    private void onComplete() {
        Log.d("Ok", "Ok");
    }

    public void onProductsReceived(List<Product> list) {
        mProductsLivedata.setValue(list);
    }

    public void onShouldBuyProductsReceived(List<Product> list) {
        mShouldBuysProductsLivedata.setValue(list);
    }

    public void onDishesReceived(List<Dish> list) {
        mDishesLivedata.setValue(list);
    }

    public MutableLiveData<List<Product>> getProductsLivedata() {
        return mProductsLivedata;
    }

    public MutableLiveData<List<Product>> getShouldBuyProductsLivedata() {
        return mShouldBuysProductsLivedata;
    }

    public MutableLiveData<List<Dish>> getDishesLivedata() {
        return mDishesLivedata;
    }

    private static class BackgroundThreadFactory implements ThreadFactory {

        private static int sTag = 1;

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("CustomThread" + sTag);
            thread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);

            // A exception handler is created to log the exception from threads
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Log.e("Error", thread.getName() + " encountered an error: " + ex.getMessage());
                }
            });
            return thread;
        }
    }
}
