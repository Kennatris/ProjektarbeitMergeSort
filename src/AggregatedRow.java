public class AggregatedRow {
    public String produktionsstandort;
    public double minEffizienz;
    public double maxEffizienz;
    public double avgGewinn;
    public double range;
    public int count;

    public AggregatedRow(String produktionsstandort, double minEffizienz, double maxEffizienz, double avgGewinn) {
        this.produktionsstandort = produktionsstandort;
        this.minEffizienz = minEffizienz;
        this.maxEffizienz = maxEffizienz;
        this.avgGewinn = avgGewinn;
    }
}
