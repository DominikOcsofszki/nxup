
## Iterationsbericht

Zwischenergebnisse der 4 Iterationsphasen.\
Testklassen werden hier nicht gesondert aufgelistet.

### 1. Phase

**User Stories:** US00, US01, US08, US14, US15, (US02), (US12), (US13)\
**Interfaces:** ICar\
**Klassen:** ParkhoseServlet, MainServlet, Car, Stats

Implementation der grundlegendsten Funktionen für einen ersten Betrieb.\
Basis Statistik Funktionen wie Summe, Durchschnitt, Minimum und Maximum.\
Fahrzeugtypen und Kundentypen können über die Parkhaus API definiert werden.\
Maximale Parkplätze und Öffnungszeiten können festgelegt werden.\
Parkplatzbelegung wird ausgegeben.

---

### 2. Phase

**User Stories:** US02, US09, US11, US12\
**Interfaces:** IObserver, IObservable, IParkingController, IParkingModel\
**Klassen:** ParkingController, ParkingModel, Price, Time, Locator, Finder, DailyEarningsView, WeeklyEarningsView, CurrentCostView

Erstellung des Model-View-Controller als Kern der Parkhaus Verwaltung.\
Nachbildung der Preisberechnung und Zeitsimulation für die Ausgabe von "live" Daten.\
Ausgabe von verschiedensten Statistiken über Views.\
Implementierung einer zentralen Konfigurationsdatei.\
Eigene Lokaliserung von freien Parkplätzen.

---

### 3. Phase

**User Stories:** US12, US13, (Undo/Redo)\
**Interfaces:** ICommand\
**Klassen:** Commander, CarEnterCommand, CarLeaveCommand, Data, Jsonify, Tableize, CarTypes, VehicleTypesView, VehicleCategoriesView

Einführen des Command Pattern für das vor- und rückgängig machen von Aktionen.\
Erstellen einer zentralen Klasse für das Lesen und Aufbereiten von Testdaten.\
Entwickeln von Hilfsklassen für das Generieren von Json und Tabellen.\
Implementation des Fahrzeugtyps als Multiton.\
Weitere Views für z.B. Anzahl der Fahrzeugtypen im Parkhaus.

---

### 4. Phase

**User Stories:** (US14), (US15), (Persistenz), (Sicherheit)\
**Klassen:** Saver, SecurityCORSFilter, CarDecorator, SanitizedCar, DurationView, EarningsByCategoriesView

Persistente Speicherung von Autos und Konfigurationsdaten im Dateisystem.\
Implementation von Sicherheitsfiltern und desinfiziertem Auto als Dekorator.\
Weitere Views für z.B. Gewinn per Kunden Kategorie.\
Finale Refaktorisierung und Aufbereitung von Quellcode.
