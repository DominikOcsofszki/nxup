
# Sicherheit

## Analyse:

Die durchgeführte Sicherheitsanalyse, für die Ermittlung von möglichen Angriffsvektoren, 
richtet sich nach der aktuellen [OWASP Top 10](https://owasp.org/Top10/).

---

**Broken Access Control**

Die Applikation implementiert keine Logins oder andere Zugriffskontrollen.

---

**Cryptographic Failures**

Es werden keine kryptografischen Funktionen z.B. für das signieren von Cookies verwendet. \
Es ist jedoch anzumerken, dass der Tomcat Server über kein gültiges TLS-Zertifikat verfügt.

---

**Injection**

An mehreren Stellen werden in der Applikation Daten verarbeitet, die von außen beeinflusst werden können:
  - Autos fahren in das Parkhaus (Parameter werden ausgelesen und gespeichert)
  - Autos verlassen das Parkhaus (Zeit und Preis Informationen werden gespeichert)
  - Der Tomcat Server startet und lädt gespeicherte Autos
  - Der Nutzer verändert die Öffnungszeiten
  - Der Nutzer verändert die maximale Anzahl an Parkplätzen 
  
Die Applikation verwendet kein Datenbanksystem. \
Es werden eine große Menge an inline-JavaScript aus externen Quellen geladen. 

---

**Insecure Design**

In der frühen Entwicklung waren Sicherheitskonzepte noch kein priorisiertes Thema.\
Es wurde als nicht von Grund auf mit dem Sicherheitsgedanken entwickelt.

---

**Security Misconfiguration**

Auf der Website werden im Debug-Modus, Fehlermeldungen mit vollständigem Stacktrace ausgegeben.\
Sicherheitsheader werden über eine Filterklasse gesetzt.\
*Konkrete Server Konfiguration nicht prüfbar.*

---

**Vulnerable and Outdated Components**

Für die verwendete Version von Tomcat (8.5.39) sind schwerwiegenden keine Schwachstellen bekannt
[CVE Details](https://www.cvedetails.com/vulnerability-list/vendor_id-45/product_id-887/version_id-644719/Apache-Tomcat-8.5.39.html). \
Für das ccmjs-Framework sind keine Schwachstellen bekannt.

---

**Identification and Authentication Failures**

Die Applikation implementiert keine Logins oder andere Zugriffskontrollen.

---

**Software and Data Integrity Failures**

Die Applikation ist abhängig von der ccmjs Parkhaus-API.

---

**Security Logging and Monitoring Failures**

Events werden geloggt.\
Logs werden nicht persistent gespeichert.\
Angriffe können nicht automatisiert detektiert und gemeldet werden.

---

**Server-Side Request Forgery**

In der Applikation werden keine dynamischen Requests vom Server generiert.

---

## Gegemaßnahmen:

Ein großer Teil der gefundenen Schwachstellen wurden als nur schwer oder nicht lösbar bestimmt.\
Um die Fläche für potenzielle Angriffe jedoch etwas zu reduzieren, wurden die folgenden Sicherheitsmaßnahmen implementiert.

- Die Parameter von Autos werden beim Betreten des Parkhauses bzw. beim Laden aus Dateien, mit einer Whitelist desinfiziert.
- Die bestehenden Sicherheitsheader wurden erweitert um: 
  - *X-Frame-Options*
  - *X-Content-Type-Options*
  - *Referrer-Policy*
  - *Permissions-Policy*

Aufgrund von technischen limitationen konnte keine effektive *Content Security Policy* definiert werden.\
*Strict-Transport-Policy* sollte eingeführt werden, sobald der Server über ein TLS Zertifikat verfügt.
