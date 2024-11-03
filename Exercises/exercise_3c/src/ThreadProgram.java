class PrimeThread extends Thread {
    public void run() {
        int count = 0;
        int num = 2;
        while (count < 25) {
            if (isPrime(num)) {
                System.out.println(System.currentTimeMillis() + " [Thread-1] Prime calculated: " + num);
                count++;
                try {
                    Thread.sleep((int)(Math.random() * 401) + 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            num++;
        }
    }

    private boolean isPrime(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}

class FibonacciThread extends Thread {
    public void run() {
        int n1 = 0, n2 = 1;
        for (int i = 1; i <= 50; i++) {
            System.out.println(System.currentTimeMillis() + " [Thread-2] Fibonacci calculated: " + n1);
            int next = n1 + n2;
            n1 = n2;
            n2 = next;
            try {
                Thread.sleep((int)(Math.random() * 401) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class FactorialThread extends Thread {
    public void run() {
        for (int i = 1; i <= 50; i++) {
            long fact = 1;
            for (int j = 1; j <= i; j++) {
                fact *= j;
            }
            System.out.println(System.currentTimeMillis() + " [Thread-3] Factorial calculated for " + i + ": " + fact);
            try {
                Thread.sleep((int)(Math.random() * 401) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadProgram {
    public static void main(String[] args) {
        PrimeThread primeThread = new PrimeThread();
        FibonacciThread fibonacciThread = new FibonacciThread();
        FactorialThread factorialThread = new FactorialThread();

        primeThread.start();
        fibonacciThread.start();
        factorialThread.start();

        try {
            primeThread.join();
            fibonacciThread.join();
            factorialThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
