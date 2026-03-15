public class SynchronizedPrinter {
    private final Object lock = new Object();
    private boolean turn = true; // ture для 1 false для 2

    public void run() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (turn) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {}
                        System.out.println(1);
                        turn = false;
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (Exception e) {
                            System.err.println("Ошибка: " + e.getMessage());
                        }
                    }
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (!turn) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {}
                        System.out.println(2);
                        turn = true;
                        lock.notify();
                    } else {
                        try {
                            lock.wait();
                        } catch (Exception e) {
                            System.err.println("Ошибка: " + e.getMessage());
                        }
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}