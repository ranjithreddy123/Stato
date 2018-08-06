package com.example.sri.sta;
import java.util.Observable;


/**
 * Created by sri on 01-08-2018.
 */

public class ObservableNetwork extends Observable {
    private static ObservableNetwork instance = new ObservableNetwork();

    public static ObservableNetwork getInstance() {
        return instance;
    }

    private ObservableNetwork() {
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}
