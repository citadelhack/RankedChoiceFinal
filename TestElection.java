import java.util.ArrayList;

public class TestElection {
    public static void main(String[] args) {
        // 1. Create a list of Person objects to represent the candidates
        ArrayList<Person> candidates = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            Person candidate = new Person();
            candidate.name = "Bad Choice" + i;
            candidate.id = i;
            candidates.add(candidate);
        }
        // 2. Create an instance of the Election class, passing the list of candidates and a filename to the constructor
        Election election = new Election(candidates, "ballots.txt");
        // 3. Call the calculateResult method to calculate the result of the election
        election.calculateResult();
        // 4. Call the getResult method to get the result of the election
        ElectionResult result = election.getResult();
        System.out.println(result);
        System.out.println("\n\n********\nWinner: " + candidates.get(result.getWinner()).name + "\n********");
    }
}
