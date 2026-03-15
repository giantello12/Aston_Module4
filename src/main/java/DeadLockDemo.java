public class DeadLockDemo {
    private final Object resource1 = new Object();
    private final Object resource2 = new Object();

    public Thread[] run() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource1) {
                    System.out.println("Thread 1: захватил resource1");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }
                    System.out.println("Thread 1: попытка захватить resource2");
                    synchronized (resource2) {
                        System.out.println("Thread 1: захватил resource2");
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource2) {
                    System.out.println("Thread 2: захватил resource2");
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }
                    System.out.println("Thread 2: попытка захватить resource1");
                    synchronized (resource1) {
                        System.out.println("Thread 2: захватил resource1");
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        return new Thread[]{thread1, thread2};
    }
}
