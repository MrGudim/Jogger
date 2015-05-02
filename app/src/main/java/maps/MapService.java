package maps;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by hansg_000 on 23.04.2015.
 */
public class MapService extends Service {
    private final IBinder _binder = new ServiceBinder();
    public int counter;
    public boolean isStarted = false;

    @Override
    public void onCreate() {
        counter = 1;
        Toast.makeText(this, "Service created. Count: " + counter, Toast.LENGTH_SHORT).show();
        isStarted = true;
    }

    @Override
    public IBinder onBind(Intent arg) {
        return _binder;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        counter++;
        Toast.makeText(this, "Service started. Count: " + counter, Toast.LENGTH_SHORT).show();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStarted = false;
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }

    public class ServiceBinder extends Binder {
        public MapService getService() {
            return MapService.this;
        }
    }
}
