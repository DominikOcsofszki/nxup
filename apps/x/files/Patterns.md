
## Patterns

Die folgenden Pattern wurden im Projekt implementiert:

### Observer (MVC)

**Interfaces**: IObserver, IObservable, IParkingModel, IParkingController\
**Klassen**: ParkingController, ParkingModel, *Views*

Der Model-View-Controller spielt eine Zentrale Rolle in der Applikation.\
Über ihn werden einfahrende Autos registriert, gespeichert, verarbeitet und Ausgaben aufbereitet.

---

### Command

**Interfaces**: ICommand\
**Klassen**: Commander, CarEnterCommand, CarLeaveCommand

Der Commander speichert die Enter/Leave Anfragen an die Applikation und führt diese aus.\
Er bietet die Möglichkeit die gespeicherten Befehle vor- und rückgängig zu machen.

---

### Decorator

**Klassen**: CarDecorator, SanitizedCar

Über den Dekorierer können die Funktionen des Autos erweitert werden, ohne die ursprüngliche Klasse zu verändern.\
Das *SanitizedCar* filtert die String Parameter des Autos mit einer Whitelist.

---

### Multiton

**Klassen**: CarTypes

Das Multiton ermöglicht es eine feste Anzahl von Fahrzeugtyp Instanzen zu verwalten.

---

Indirekt wurden weitere Pattern aus der JavaAPI und Javax verwendet.\
Darunter: **Iterator** (Streams), **Factory** (Json), **Builder** (String, Json), **Composite** (Json)
