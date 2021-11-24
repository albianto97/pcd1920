package pcd.lab03.personale;

import javax.swing.*;
import java.util.List;


class CounterTask extends SwingWorker<Integer, Integer> {
    protected Integer doInBackground() throws Exception {
        int i = 0;
        int sum = 0;
        int maxCount = 10;
        while (!isCancelled() && i < maxCount) {
            sum+=i;
            i++;
            publish(new Integer[] { i });
            setProgress(100 * i / maxCount);
            Thread.sleep(1000);
        }
        return sum;
    }

    protected void process(List<Integer> chunks) {
        for (int i : chunks)
            System.out.println("Step "+i);
    }
    protected void done() {
        if (isCancelled()){
            System.out.println("Task cancelled.");
        } else {
            System.out.println("Task completed.");
        }
    }
}