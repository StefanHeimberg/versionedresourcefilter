# Versioned Resource Servlet

## Ausgangslage

Um bei einem neuen Release des Web Frontends sicher zu stellen dass die neuen JS, CSS und Images auch wirklich vom Browser neu geladen werden ist es allgemein gültig die Version als query parameter anzufügen.

```
http://example.com/css/style.css?v=1.2.3
```

## Problem

Falls nun ein Reverse Proxy zwischen dem Browser und dem Webserver liegen, kann es sein dass dieser das caching ausschaltet weil dieser mit der Versionierung über den query paramter nicht umgehen kann.

Weiter kommt noch hinzu dass die Versionsnummer abhängig vom Buildjob ist wenn man einen Buildserver verwendet.


## Lösung

Zur Lösung dieses problems muss nun die Version in den Pfad rein. Dies ist der Grund für dieses Projekt

```
http://example.com/css/1.2.3/style.css
```

## Builen & Testen

```
mvn clean verify
```