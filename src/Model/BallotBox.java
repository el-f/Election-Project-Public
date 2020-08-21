package Model;

import java.util.*;

public class BallotBox<T extends Citizen> {

    private final int serialNum;
    private static final int FIRST_NUM = 1;
    private static int serialNumGen = FIRST_NUM;
    private String address;
    private final Set<T> designatedCitizens;
    public final Class<T> type; //Class file to force the type field to be extension of Citizen.
    private final HashMap<PoliticalParty, Integer> votingResults;


    //---------------Used For TableView----------------//
    @SuppressWarnings("unused")
    public int getDesignatedCitizensAmount() {
        return designatedCitizens.size();
    }

    @SuppressWarnings("unused")
    public String getTypeToString() {
        return type.getSimpleName();
    }

    @SuppressWarnings("unused")
    public int getSerialNum() {
        return serialNum;
    }

    @SuppressWarnings("unused")
    public String getAddress() {
        return address;
    }
    //--------------------------------------------//

    public BallotBox(Class<T> type) {
        this(HelperUtilities.getRandomAddress(), type);
    }

    public BallotBox(String _address, Class<T> type) {
        address = _address;
        this.type = type;
        serialNum = serialNumGen++;
        designatedCitizens = new Set<>();
        votingResults = new HashMap<>();
    }

    public void randomVote(List<PoliticalParty> parties) {
        for (T c : designatedCitizens.getSet()) {
            /*
             * asks citizen if he wants to vote, a chance for a 'yes' is set in
             * HelperUtilities (HIGH_CHANCE), and checks that he didn't vote yet.
             */
            if (c.getCanVote() && HelperUtilities.getHighChanceBoolean() && !c.hasVoted) {
                voteToParty(PoliticalParty.getRandomPartyByPopularity(parties));
                c.hasVoted = true;
            }
        }
    }

    private void voteToParty(PoliticalParty party) {
        int votes = votingResults.getOrDefault(party, 0);
        if (votingResults.containsKey(party))
            votingResults.put(party, votes + 1);
    }

    public HashMap<PoliticalParty, Integer> getVotingResults() {
        return votingResults;
    }

    public double getPercentageOfVoters() {
        if (designatedCitizens.isEmpty())
            return 0;
        return votingResults
                .values()
                .stream()
                .mapToDouble(Integer::doubleValue)
                .sum() / designatedCitizens.size() * 100;
    }


    /*
    All types of citizens inherit from Citizen, so there will never be ClassCastException at runtime
    but for safety we add our own check and exception.
     */
    @SuppressWarnings("unchecked")
    public void addVoter(Citizen c) throws MyException {
        if (c.getClass().getSimpleName().equals(type.getSimpleName()) ||
                (c instanceof Nominee && type.equals(Citizen.class)))
            designatedCitizens.add((T) c);
        else throw new MyException("Not the correct type of citizen (" + c.getClass().getSimpleName() + ")");
    }

    public boolean equals(BallotBox<?> other) {
        return type.equals(other.type) && this.serialNum == other.serialNum && this.address.equals(other.address);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("#").append(serialNum).append(". Address: ").append(address).append(", Type: ")
                .append(type.getSimpleName());
        if (!designatedCitizens.isEmpty())
            str.append("\n");
        str.append(designatedCitizens);
        return str.toString();
    }


}
