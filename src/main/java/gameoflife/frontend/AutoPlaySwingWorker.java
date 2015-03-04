package gameoflife.frontend;

import javax.swing.SwingWorker;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.function.Supplier;

import static java.lang.Thread.sleep;

public class AutoPlaySwingWorker extends SwingWorker<Object, Object> {

    private final Runnable periodicTask;
    private final Supplier<Integer> delaySupplier;
    private final Runnable guiRefresh;

    public AutoPlaySwingWorker(Runnable periodicTask, Supplier<Integer> delaySupplier, Runnable guiRefresh) {
        this.periodicTask = periodicTask;
        this.delaySupplier = delaySupplier;
        this.guiRefresh = guiRefresh;
    }

    @Override
    protected Object doInBackground() throws Exception {
        while (!isCancelled()) {
            periodicTask.run();
            publish(new Object());
            sleep(delaySupplier.get());
        }
        return null;
    }

    @Override
    protected void process(List<Object> chunks) {
        guiRefresh.run();
    }

    @Override
    protected void done() {
        try {
            get();
        } catch (CancellationException e) {
            // cancelled
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}