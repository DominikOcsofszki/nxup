
## Produktblatt

### Zielgruppen und Mehrwert

**Kunde:**

    Kunden erhalten eine vielzahl an hilfreichen Statusinformationen und müssen sich nicht mehr um Tickets kümmern.

**Betreiber:**

    Der Betreiber hat Zugriff auf Statistiken zu Einnahmen und Parkplatzbelegung.
    Er kann Parameter wie Öffnungszeiten und verfügbare Parkplätze dynamisch verändern.
    Außerdem benötigt er weniger Mitarbeiter, da viele Aufgaben vom System übernommen werden.

**Finanzamt:**

    Das Finanzamt kann gesammelte Informationen über Einnahmen abrufen und spart so Zeit.

---

### Klassen

**ParkhouseServlet:**

    Servlet Superklasse.
    Verarbeitet GET und POST Anfragen.
    Speichert einen ParkingController und einen Saver, sowie ein Array für Konfiguration.

**MainServlet:**

    Erweiterung des ParkhouseServlet.
    Stellt das primäre Parkhaus da.

**SecurityCORSFilter:**

    Setzt eine Reihe von HTTP Headern für Sicherheit und Privacy.

---

**Config:**

    Erlaubt es globale Parameter wie Simulationsgeschwindigkeit, Kundenkategorien oder Fahrzeugtypen festzulegen.

---

**Car:**

    Enthält die Parameter der Autos und berechnet Parkdauer und Preis.

**CarDecorator:**

    Erlaubt es das Auto funktional zu erweitern.

**SanitizedCar:**

    Erweitert das Auto um Methoden, die die String Parameter des Autos mit Whitelist filtern.

**CarTypes:**

    Multiton für den Fahrzeugtyp.

---

**Locator:**

    Sucht einfahrenden Autos, einen freien Parkplatz.

**Price:**

    Formatiert Preise für Ausgaben und bestimmt Preisfaktoren abhängig von Kundenkategorie und Fahrzeugtyp.

**Stats:**

    Berechnet Summe, Durchschnitt, Minimum und Maximum von Einnahmen.

---

**Commander:**

    Speichert Befehle und erlaubt es diese vor- und rückgängig zu machen.

**CarEnterCommand:**

    Befehl für das Einfahren von Autos.

**CarLeaveCommand:**

    Befehl für das Verlassen von Autos.

---

**ParkingController:**

    Instanziiert das ParkingModel und die Views.
    Erlaubt es Zustandsänderungen an das Model weiterzugeben.

**ParkingModel:**

    Hält alle Autos die das Parkhaus verwaltet (im Parkhaus und außerhalb).
    Bietet Methoden für das hinzufügen und entfernen von Autos.

---

**ClientCategoriesView:**

    Graph mit den aktuellen Kundenkategorien im Parkhaus.

**VehicleTypesView:**

    Graph mit den aktuellen Fahrzeugtypen im Parkhaus.

**DurationView:**

    Graph mit den aktuellen Parkzeiten der Autos im Parkhaus.

**DailyEarningsView:**

    Die aktuellen Tageseinnahmen.

**WeeklyEarningsView:**

    Die aktuellen Wocheneinnahmen.

**CurrentCostsView:**

    Tabelle mit den aktuellen Parkkosten von Autos im Parkhaus.

**EarningsByCategoriesView:**

    Tabelle mit den Einnahmen sortiert nach Kundenkategorie.

---

**Finder:**

    Sucht nach Autos in Iterierbaren Objekten, anhand von beliebigen Parametern.

**Jsonify:**

    Bietet Methoden für die Generierung von JSON Objekten.

**Saver:**

    Bietet Methoden für das persistente Speichern von Autos und Konfigurationen im Dateisystem.

**Tableize:**

    Bietet Methoden für die Generierung von HTML Tabellen.

**Time:**

    Bietet Methoden für die Transformation von Echtzeit zu Simulationszeit.

---

### Webapp

**index.jsp:**

    Primäre Seite der Applikation.
    Enthält die Parameter für die Parkhaus Servlets.

**style.css:**

    Enhält das CSS Styling für die Seite.

---

### Resourcen

**MainServlet.cars:**

    Speichert die Autos des MainServlet.

**MainServlet.conf:**

    Speichert die Konfiguration des MainServlet.
