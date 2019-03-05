import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Monitor {
    List<IFile> listeners = new ArrayList<>();
    Path directory = Paths.get("D:\\KMS");

    public void subscribe(IFile client) {
        listeners.add(client);
    }

    public void unsubscribe(IFile client) {
        listeners.remove(client);
    }

    public void start() {
        try {
            WatchService watch = FileSystems.getDefault().newWatchService();
            directory.register(watch, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

            boolean valid = true;
            do {
                WatchKey key = watch.take();
                for (WatchEvent event : key.pollEvents()) {
                    for (IFile client : listeners) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE && client instanceof IAddFile) {
                            ((IAddFile) client).added(event.context().toString());
                        } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE && client instanceof IDelFile) {
                            ((IDelFile) client).deleted(event.context().toString());
                        }
                    }
                }
                valid = key.reset();
            } while (valid);
            watch.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
