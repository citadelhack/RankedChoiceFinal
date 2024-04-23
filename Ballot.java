import java.sql.SQLOutput;

public class Ballot {
    // Inner Class
    class Vote {
        int candidateNum;
        Vote nextVote;
    }

    // Data
    Vote rankOne;

    // Constructor
    public Ballot(int[] candidateNums) {
        rankOne = new Vote();
        rankOne.candidateNum = candidateNums[0];
        Vote currentVote = rankOne;
        for (int i = 1; i < candidateNums.length; i++) {
            Vote nextVote = new Vote();
            nextVote.candidateNum = candidateNums[i];
            currentVote.nextVote = nextVote;
            currentVote = nextVote;
        }
    }

    // Methods
    public int getRankOne() {
        return rankOne != null ? rankOne.candidateNum : -1;
    }

    public void removeCandidate(int candidateNum) {
        // If the first vote is the one to be removed
        if (rankOne != null && rankOne.candidateNum == candidateNum) {
            rankOne = rankOne.nextVote;
        } else {
            // Search for the vote to be removed in the rest of the list
            Vote currentVote = rankOne;
            while (currentVote != null && currentVote.nextVote != null) {
                if (currentVote.nextVote.candidateNum == candidateNum) {
                    // Remove the vote
                    currentVote.nextVote = currentVote.nextVote.nextVote;
                    break;
                }
                currentVote = currentVote.nextVote;
            }
        }
    }
}