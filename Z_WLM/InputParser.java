// InputParser.java
public class InputParser {
    private final String[] args;
    private int selectprog;
    private String algorithm;
    private int numLimit;
    private int numOfThreads;
    private String timeInterval;

    public InputParser(String[] args) {
        this.args = args;
    }

    public void parseInput() {
        // Parsing logic here to set selectprog, algorithm, numLimit, numOfThreads, and timeInterval
    }

    public int getSelectprog() {
        return selectprog;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getNumLimit() {
        return numLimit;
    }

    public int getNumOfThreads() {
        return numOfThreads;
    }

    public String getTimeInterval() {
        return timeInterval;
    }
}
