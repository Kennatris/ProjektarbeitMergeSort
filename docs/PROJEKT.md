# CSV-Auswertung – Projekt und Technik

## Aufgabe

Das Programm verbindet das Einlesen einer großen CSV-Datei mit Datenaggregation und einer eigenen MergeSort-Implementierung. Sortiert werden die zusammengefassten Standorte. Die einzelnen CSV-Zeilen liefern dafür die Mess- und Gewinnwerte.

## Datenfluss

![Technikübersicht](media/technik.svg)

| Klasse | Aufgabe |
| --- | --- |
| `Main` | CSV-Dateien suchen, Zeilen lesen und Ergebnisse ausgeben |
| `Row` | Standort, Effizienz und Gewinn einer Datenzeile halten |
| `Aggregator` | Minimum, Maximum, Spannweite und mittleren Gewinn je Standort berechnen |
| `AggregatedRow` | Zusammengefasste Werte und Anzahl halten |
| `Sorter` | Aggregierte Einträge rekursiv mit MergeSort ordnen |

## CSV-Format

Alle Dateien mit der Endung `.csv` im Ordner `CSV` werden eingelesen. Die erste Zeile dient als Kopfzeile. Standort, Gewinn und Effizienz werden aus der fünften, achten und neunten Spalte gelesen. Das Programm berücksichtigt in Anführungszeichen stehende Felder und wandelt Dezimalkommas um.

Aus den Effizienzwerten entstehen Minimum und Maximum. Ihre Differenz ist die Spannweite. Der durchschnittliche Gewinn wird beim Hinzufügen einer Zeile aktualisiert.

```text
Spannweite = maximale Effizienz − minimale Effizienz
Durchschnittlicher Gewinn = Summe der Gewinne / Anzahl der Zeilen
```

## MergeSort

`mergeSort` teilt die Standortliste rekursiv in zwei Hälften. `merge` führt die sortierten Teillisten zusammen. Der größere `range`-Wert kommt zuerst. Bei gleichen Werten wird zuerst der Eintrag aus der linken Hälfte übernommen.

Für k aggregierte Standorte benötigt diese Sortierung O(k log k) Vergleiche und zusätzlichen Speicher für die Teillisten. Das Einlesen und Gruppieren der CSV-Zeilen erfolgt vorher.

## Ausführen

Die Java-Dateien lassen sich mit JDK 21 kompilieren. Im Projektordner unter Windows:

```powershell
New-Item -ItemType Directory -Force build | Out-Null
javac -encoding UTF-8 -d build src/*.java
java -Xmx1g -cp build Main
```

Unter Linux:

```bash
mkdir -p build
javac -encoding UTF-8 -d build src/*.java
java -Xmx1g -cp build Main
```

Das Arbeitsverzeichnis muss den Ordner `CSV` enthalten. Die Anwendung liest die Daten in den Speicher und zeigt währenddessen ihren Fortschritt in der Konsole.

## Mitgelieferte Daten

`sensor_produkte_1000000.csv` enthält 1.000.000 Datenzeilen und 16 Standorte. Die folgenden Werte entsprechen der Programmausgabe:

| Standort | Minimum | Maximum | Spannweite | Ø Gewinn (€) |
| --- | ---: | ---: | ---: | ---: |
| Tettnang | 1,30 | 141,57 | 140,27 | 691,93 |
| Opole | 1,14 | 129,39 | 128,25 | 742,83 |
| Shanghai | 6,36 | 131,87 | 125,51 | 946,18 |
| Malvern | 1,65 | 120,48 | 118,83 | 738,56 |
| Tokyo | 1,04 | 97,07 | 96,03 | 568,68 |
| Guadalajara | 1,02 | 96,90 | 95,88 | 626,77 |
| Montreal | 3,12 | 94,16 | 91,04 | 792,42 |
| Seoul | 2,19 | 90,38 | 88,19 | 562,27 |
| Istanbul | 0,57 | 87,86 | 87,29 | 754,00 |
| São Paulo | 0,76 | 82,50 | 81,74 | 673,38 |
| Mailand | 0,85 | 80,83 | 79,98 | 635,50 |
| Katowice | 1,36 | 75,12 | 73,76 | 762,53 |
| Delhi | 2,51 | 74,86 | 72,35 | 542,88 |
| Brno | 0,33 | 67,03 | 66,70 | 672,26 |
| Lissabon | 2,89 | 62,56 | 59,67 | 714,68 |
| Barcelona | 0,34 | 59,09 | 58,75 | 629,50 |

[Zur Übersicht](../README.md) · [Ergebnisgrafik](GALERIE.md). Alle Angaben und Downloads ohne Gewähr.
