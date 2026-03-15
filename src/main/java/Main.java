public class Main {
    public static void main(String[] args) {
        System.out.println("Демонстрация DeadLock");
        boolean isDeadLockDetected = deadLockDemo();
        if (isDeadLockDetected) {
            System.out.println("Обнаружен DeadLock!");
        }
        System.out.println("\nДемонстарция LiveLock");
        boolean isLiveLockDetected = liveLockDemo();
        if (isLiveLockDetected) {
            System.out.println("Обнаружен LiveLock!");
        }
        printerDemo();
    }

    public static boolean deadLockDemo() {
        System.out.println("Main Thread DeadLock: Запуск");

        DeadLockDemo deadLockDemo = new DeadLockDemo();
        Thread[] threads = deadLockDemo.run();
        Thread thread1 = threads[0];
        Thread thread2 = threads[1];

        System.out.println("Main Thread: Запущены потоки Thread1 и Thread2, ожидание завершения.");

        try {
            thread1.join(2000);
            thread2.join(2000);

            if (!thread1.isAlive() && !thread2.isAlive()) {
                System.out.println("Main Thread: все потоки успешно выполнены");
                return false;
            } else {
                System.out.println("Main Thread: превышено время ожидания.\n" +
                                   "Main Thread: состояние потоков:\n" +
                                   "Main Thread: Thread1 - " + thread1.getState() + "\n" +
                                   "Main Thread: Thread2 - " + thread2.getState() + "\n" +
                                   "Main Thread: Thread1 и Thread2 отправлен сигнал прерывания");
                thread1.interrupt();
                thread2.interrupt();
            }
        } catch (InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        System.out.println("Main Thread: Обнаружен DeadLock");
        System.out.println("Main Thread: Thread1 и Thread2 заблокированы и не могут завершиться.");

        return true;
    }

    public static boolean liveLockDemo() {
        System.out.println("Main Thread LiveLock: Запуск");
        LiveLockDemo liveLockDemo = new LiveLockDemo();
        Thread[] threads = liveLockDemo.run();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
        System.out.println("Main Thread: Thread 1 - " + threads[0].getState() + "\n" +
                           "Main Thread: Thread 2 - " + threads[1].getState() + "\n" +
                           "Main Thread: Оба потока активны, но не могут завершить работу. Обнаружен LiveLock");
        return true;
    }

    public static void printerDemo() {
        System.out.println("\nДемонстарция многопоточного вывода.");
        SynchronizedPrinter sp = new SynchronizedPrinter();
        sp.run();
    }
}
