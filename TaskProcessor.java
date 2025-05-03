package imageEncryption;

import java.util.concurrent.*;

/**
 * TaskProcessor
 *
 * @author xuetianbao
 * @date 2024/06/29
 */
public class TaskProcessor {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 30;
    private static final long KEEP_ALIVE_TIME = 60;
    private static final int QUEUE_CAPACITY = R.PIC_NUM;

    private static class SingletonHolder {
        private static final TaskProcessor INSTANCE = new TaskProcessor();
    }

    private final ExecutorService executorService;

    private TaskProcessor() {
        executorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static TaskProcessor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void submitTask(Runnable task) {
        getExecutorService().submit(task);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.HOURS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
