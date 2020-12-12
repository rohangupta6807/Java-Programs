public class ProducerConsumer {
    public static void main(String[] args) {
        Data data = new Data();

        // Thread 1  to produce
        new Thread(() -> {
            while (true) {
                data.produce((int) (Math.random() * 10));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Thread 2 to consume
        new Thread(() -> {
            while (true) {
                data.consume();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class Data {
    int amount;

    public synchronized void produce(int count) {
        if (amount != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        amount = count;
        System.out.println("Produced : " + getPattern(count));
        notify();
    }

    public synchronized void consume() {
        if (amount == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int sudoAmount = amount;
        while (amount != 0) {
            amount = amount - 1;
            System.out.println("Consumed : " + getPattern(sudoAmount - amount));
        }
        notify();
    }

    private String getPattern(int number) {
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < number; ++i) {
            sf.append("*");
        }
        return sf.toString();
    }
}