import java.io.*;
import java.util.ArrayList;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        File folder = new File("CSV");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));

        if (files == null || files.length == 0) {
            System.out.println("Keine CSV-Dateien im Verzeichnis gefunden.");
            return;
        }

        ArrayList<Row> allRows = new ArrayList<>();
        for (File file : files) {
            allRows.addAll(readRowsFromCSV(file));
        }

        Aggregator aggregator = new Aggregator();
        ArrayList<AggregatedRow> aggregatedRows = aggregator.aggregate(allRows);

        System.out.println("\n\n--- UNSORTIERTE AGGREGIERTE DATEN ---");
        printAggregatedRows(aggregatedRows);

        Sorter sorter = new Sorter();
        sorter.sort(aggregatedRows); // sortiert nach range absteigend

        System.out.println("\n\n--- SORTIERTE AGGREGIERTE DATEN (nach range absteigend) ---");
        printAggregatedRows(aggregatedRows);
    }

    private static ArrayList<Row> readRowsFromCSV(File file) {
        ArrayList<Row> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            int total = lines.size() - 1; // Header überspringen
            System.out.println("Einlesen aus Datei: " + file.getName());
            System.out.println("Gesamtzeilen (ohne Header): " + total);

            for (int i = 1; i < lines.size(); i++) {
                String[] tokens = lines.get(i).split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (tokens.length < 9) continue;

                String standort = tokens[4].trim();
                String effizienzRaw = tokens[8].replace("%", "").replace("\"", "").replace(",", ".").trim();
                String gewinnRaw = tokens[7].replace("€", "").replace("\"", "").replace(",", ".").trim();

                try {
                    double effizienz = Double.parseDouble(effizienzRaw);
                    double gewinn = Double.parseDouble(gewinnRaw);

                    rows.add(new Row(standort, effizienz, gewinn));
                } catch (NumberFormatException e) {
                    System.err.println("Ungültige Zeile übersprungen: " + lines.get(i));
                }

                // Ladebalken anzeigen
                if (total > 0) {
                    int progress = (i * 100) / total;
                    showProgressBar(progress);
                }
            }

            showProgressBar(100);
            System.out.println("\nEinlesen abgeschlossen.");

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen: " + file.getName());
            e.printStackTrace();
        }

        return rows;
    }

    private static void showProgressBar(int percent) {
        final int width = 50;
        int filled = (percent * width) / 100;
        StringBuilder bar = new StringBuilder();
        bar.append('\r').append("[");
        for (int i = 0; i < width; i++) {
            if (i < filled) bar.append('=');
            else if (i == filled) bar.append('>');
            else bar.append(' ');
        }
        bar.append("] ").append(percent).append("%");
        System.out.print(bar);
    }

    private static void printAggregatedRows(ArrayList<AggregatedRow> rows) {
        for (AggregatedRow r : rows) {
            System.out.printf(
                    "Standort: %-15s | MinEff: %6.2f | MaxEff: %6.2f | Range: %6.2f | Ø Gewinn: %8.2f\n",
                    r.produktionsstandort, r.minEffizienz, r.maxEffizienz, r.range, r.avgGewinn
            );
        }
    }
}
