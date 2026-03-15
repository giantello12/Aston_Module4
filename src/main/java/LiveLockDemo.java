public class LiveLockDemo {
    private final Object resource1 = new Object();
    private final Object resource2 = new Object();

    private boolean isResource1Free = true;
    private boolean isResource2Free = true;

    public Thread[] run() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int attempts = 0;
                while (attempts < 5) {
                    synchronized (resource1) {
                        isResource1Free = false;
                        System.out.println("Thread 1: захватил resource1 (попытка " + (attempts + 1) + ")");
                    }

                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }

                    synchronized (resource2) {
                        if (isResource2Free) {
                            System.out.println("Thread 1: УСПЕХ! захватил resource2");
                            return;
                        } else {
                            System.out.println("Thread 1: resource2 занят, пробую снова");
                        }
                    }

                    synchronized (resource1) {
                        isResource1Free = true;
                    }

                    attempts++;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int attempts = 0;
                while (attempts < 5) {
                    synchronized (resource2) {
                        isResource2Free = false;
                        System.out.println("Thread 2: захватил resource2 (попытка " + (attempts + 1) + ")");
                    }

                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }

                    synchronized (resource1) {
                        if (isResource1Free) {
                            System.out.println("Thread 2: УСПЕХ! захватил resource1");
                            return;
                        } else {
                            System.out.println("Thread 2: resource1 занят, пробую снова");
                        }
                    }

                    synchronized (resource2) {
                        isResource2Free = true;
                    }

                    attempts++;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        return new Thread[]{thread1, thread2};
    }
}