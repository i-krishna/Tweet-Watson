// AlgorithmExecutor.java
public class AlgorithmExecutor {
    private final InputParser inputParser;

    public AlgorithmExecutor(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    public void executeAlgorithm() {
        int selectprog = inputParser.getSelectprog();
        switch (selectprog) {
            case 1:
                executeWithoutEnclaves();
                break;
            case 2:
                executeWithEnclaves();
                break;
            default:
                System.out.println("Invalid selection");
        }
    }

    private void executeWithoutEnclaves() {
        // Logic to execute algorithm without enclaves
    }

    private void executeWithEnclaves() {
        // Logic to execute algorithm with enclaves
    }
}
