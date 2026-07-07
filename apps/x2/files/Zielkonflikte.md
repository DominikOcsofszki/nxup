
## Zielkonflikte

Während des Projekts kamen folgende, signifikate Probleme auf:

**Keine einheitlichen Daten für Tests**\
*Lösung: Implementierung einer zentralen Klasse für das Lesen und Bereitstellen von Testdaten.*

**Unklarheit über Preisberechnung und Zeitbeschleunigung in der Simulation**\
*Lösung: Systematische Analyse der Parkhaus JavaScript API.*

**Implementation eine Content Securty Policy wegen vielen inline-Scripts problematisch**\
*Lösung: Verwendung von Hashes über Java Script Libaries für ausnahmen (aufgrund von hoher komplexität verworfen).*

**Daten gehen nach einem Server Neustart verloren**\
*Lösung: Implementierung einer persistenten Speicherung im Dateisystem.*

**String Parameter des Autos können potenziell schädlichen JavaScript Code enthalten**\
*Lösung: Einsatz einer Filterfunktion für das Desinfizieren von String Parametern vor der Ausgabe.*

**Stats-Methoden in Template- und Multiton Pattern umwandeln**\
Die Patterns wurden zwar als Möglichkeit gesehen, jedoch bestanden zu wenige algorithmische Parallelen.\
*Lösung: Basis Statistiken als statische Hilfsklasse.*

**Controller als Singleton**\
Zu starke Einschränkung von Evolvierbarkeit (mehrere Controller können nötig sein). \
*Lösung: Entfernen des Patterns.*