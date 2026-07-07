package parkhouse.models;

import parkhouse.views.IObserver;

public interface IObservable {

    /*
    Author: jstueh2s
     */

    void registerObserver(IObserver o);
    void removeObserver(IObserver o);
    void notifyObservers();

}
