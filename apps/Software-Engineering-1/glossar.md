
Die 10 wichtigsten Begriffe aus der Geschäftslogik/Fachlogik unseres Projekts.

### Parkhouse
Das Parkhouse enthält alle Etagen und Parkplätze.

### Parkingdeck
Ein Parkhaus kann aus mehreren Etagen bestehen.

### Parkingspot
Eine Etage hat mehrere Parkplätze von verschiedenen Typen.

### Car
Ein Fahrzeug welches im Parkhaus parkt und eindeutig identifiziert werden kann.

### Accounting
Enthält Informationen und Statistiken über Gewinn.

### Ticket
Ein Ticket speichert Ankunftszeit und den Fahrzeugtyp.

### Ticketsystem
Das Ticketsystem verwaltet alle Tickets.

### Checkout
An der Kasse können Parktickets bezahlt werden.

### Pricingsystem
Das Preissystem enthält unterschiedliche Tarife.

### Panel
Die Informationstafel enthält Daten, die dem Kunden angezeigt werden könne.

[//]: # (@startuml)

[//]: # (left to right direction)

[//]: # (skinparam packageStyle rectangle)

[//]: # (actor Customer)

[//]: # (actor Manager)

[//]: # (rectangle Parkhaus {)

[//]: # (Customer -- &#40;checkout&#41;)

[//]: # (&#40;checkout&#41; .> &#40;payment&#41; : <<include>>)

[//]: # (&#40;help&#41; .> &#40;checkout&#41; : <<extend>>)

[//]: # (&#40;checkout&#41; -- Manager)

[//]: # (})

[//]: # (@enduml)