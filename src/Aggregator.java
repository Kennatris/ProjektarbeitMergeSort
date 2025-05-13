import java.util.ArrayList;

public class Aggregator {
    public ArrayList<AggregatedRow> aggregate(ArrayList<Row> rows) {
        ArrayList<AggregatedRow> result = new ArrayList<>();

        for (Row currentRow : rows) {
            String standort = currentRow.produktionsstandort;
            boolean alreadySeen = false;

            for (AggregatedRow existingResults : result) {
                if (existingResults.produktionsstandort.equals(standort)) {
                    if (currentRow.effizienz < existingResults.minEffizienz)
                        existingResults.minEffizienz = currentRow.effizienz;
                    if (currentRow.effizienz > existingResults.maxEffizienz)
                        existingResults.maxEffizienz = currentRow.effizienz;

                    existingResults.range = existingResults.maxEffizienz - existingResults.minEffizienz;

                    existingResults.avgGewinn = existingResults.avgGewinn * existingResults.count + currentRow.gewinn;
                    existingResults.count++;
                    existingResults.avgGewinn = existingResults.avgGewinn / existingResults.count;

                    alreadySeen = true;
                    break;
                }
            }

            if (!alreadySeen) {
                AggregatedRow newAggregatedRow = new AggregatedRow(
                        standort,
                        currentRow.effizienz, // min
                        currentRow.effizienz, // max
                        currentRow.gewinn
                );
                newAggregatedRow.avgGewinn = currentRow.gewinn;
                newAggregatedRow.count = 1;
                newAggregatedRow.range = 0;
                result.add(newAggregatedRow);
            }
        }
        return result;
    }
}