import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Election {

    // Data
    List<Person> candidates;
    List<Ballot> ballots;
    List<Person> voters;
    ElectionResult result;
    int numCandidates;
    int numBallots;


    // Constructor
    public Election(List<Person> cand, String filename) {
        this.candidates = cand;
        this.voters = new ArrayList<>();
        this.ballots = new ArrayList<>();
        numBallots = 0;
        numCandidates = candidates.size();
        readBallots(filename);
        result = new ElectionResult(numCandidates, numBallots, candidates);
    }



    // Methods
    public void addCandidate(String candidateName) {
        var candidate = new Person();
        candidate.name = candidateName;
        candidate.id = candidates.size();
        candidates.add(candidate);
    }

    private void addVoter(String voterName) {
        var voter = new Person();
        voter.name = voterName;
        voter.id = numBallots;
        voters.add(voter);
    }

    public void addBallot(Ballot ballot) {
        numBallots++;
        ballots.add(ballot);
    }

    public ElectionResult getResult() {
        // Implement the method to get the result
        return result;
    }

    public void calculateResult() {
        boolean[] isEliminated = new boolean[candidates.size()];

        while (true) {
            ArrayList<Integer> voteCounts = new ArrayList<>(Collections.nCopies(candidates.size(), 0));

            for (Ballot ballot : ballots) {
                int candidateNum = ballot.getRankOne();
                while (candidateNum != -1 && isEliminated[candidateNum]) {
                    ballot.removeCandidate(candidateNum);
                    candidateNum = ballot.getRankOne();
                }
                if (candidateNum != -1) {
                    voteCounts.set(candidateNum, voteCounts.get(candidateNum) + 1);
                }
            }

            result.addRound(new ArrayList<>(voteCounts));

            if(result.getWinner() != -1){
                return;
            }

            int minVotes = Integer.MAX_VALUE;
            int minVotesCandidateNum = -1;
            for (int i = 0; i < voteCounts.size(); i++) {
                if (!isEliminated[i] && voteCounts.get(i) < minVotes) {
                    minVotes = voteCounts.get(i);
                    minVotesCandidateNum = i;
                }
            }

            isEliminated[minVotesCandidateNum] = true;

            if (ballots.stream().allMatch(ballot -> ballot.getRankOne() == -1 || isEliminated[ballot.getRankOne()])){
                return;
            }
        }
    }

   private void readBallots(String fileName) {
    File file = new File(fileName);
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String voterName = parts[0];
            addVoter(voterName);
            int[] candidateNums = new int[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                candidateNums[i - 1] = Integer.parseInt(parts[i]);
            }
            Ballot ballot = new Ballot(candidateNums);
            addBallot(ballot);
        }
    } catch (FileNotFoundException e) {
        System.out.println("File not found: " + fileName);
    }
}

    private int getCandidateNumber(String candidateName) {
        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i).name.equals(candidateName)) {
                return i;
            }
        }
        return -1; // Return -1 if the candidate is not found
    }
}