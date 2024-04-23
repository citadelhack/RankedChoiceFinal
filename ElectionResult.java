import java.util.ArrayList;
import java.util.List;

public class ElectionResult {
    // Data
    ArrayList<ArrayList<Integer>> resTable;
    List<Person> candidates;
    int numCandidates;
    int numBallots;
    int currRound;

    // Constructor
    public ElectionResult(int numCandidates, int numBallots, List<Person> candidates) {
        this.numCandidates = numCandidates;
        this.numBallots = numBallots;
        this.candidates = candidates;
        this.resTable = new ArrayList<>();
        this.currRound = 0;
    }

    // Methods
    public void addRound(ArrayList<Integer> round) {
        resTable.add(new ArrayList<>(round));
        currRound++;
    }

    public int getWinner() {
        if (currRound == 0) {
            return -1; // No rounds have been conducted yet
        }

        int majority = numBallots / 2;
        ArrayList<Integer> lastRound = resTable.get(currRound - 1);
        for (int i = 0; i < numCandidates; i++) {
            int candidateVotes = lastRound.get(i);
            if (candidateVotes > majority) {
                return i; // Return the candidate number
            }
        }

        return -1; // No candidate has over 50% of the votes
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currRound; i++) {
            sb.append("Round ").append(i + 1).append(":\n");
            ArrayList<Integer> round = resTable.get(i);
            for (int j = 0; j < numCandidates; j++) {
                sb.append("Candidate ").append(candidates.get(j).name).append(": ").append(round.get(j)).append(" votes\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}