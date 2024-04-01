// Main.java
public class Main {
    public static void main(String[] args) {
        InputParser inputParser = new InputParser(args);
        inputParser.parseInput();
        AlgorithmExecutor algorithmExecutor = new AlgorithmExecutor(inputParser);
        algorithmExecutor.executeAlgorithm();
    }
}
