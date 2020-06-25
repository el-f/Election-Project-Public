package Model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class Election {

    //------------------------Default Election------------------------//
    public static final int DEFAULT_NUM_CITIZENS = 5; // default=5
    public static final int DEFAULT_NUM_PARTIES = 3; // default=3
    public static final int DEFAULT_NUM_BALLOTS = 2; // default=2
    public static final int DEFAULT_NUM_NOMINEES_PER_PARTY = 2; // default=2
    public static final int YEAR_MIN = 2019;
    public static final int YEAR_MAX = 2022;

    public static Election initDefault() throws MyException {
        Election election = new Election(HelperUtilities.getRandomInt(YEAR_MIN, YEAR_MAX),
                HelperUtilities.getRandomInt(1, 12));
        election.addNumOfRandomCitizens(DEFAULT_NUM_CITIZENS);
        election.addNumOfRandomBallots(DEFAULT_NUM_BALLOTS);
        election.addNumOfRandomParties(DEFAULT_NUM_PARTIES);
        for (PoliticalParty pp : election.getPoliticalParties()) {
            pp.addNumOfRandomNominees(DEFAULT_NUM_NOMINEES_PER_PARTY);
        }
        election.assignVotersToBoxes();
        return election;
    }
    //----------------------------------------------------------------//


    public static final int MAX_NUM_OF_PARTIES = HelperUtilities.defaultParties.size();
    private YearMonth electionDate;
    private final Set<Citizen> voters;
    private final List<PoliticalParty> politicalParties;
    private final List<BallotBox<? extends Citizen>> ballotBoxes;
    private final HashMap<PoliticalParty, Integer> finalResults;

    public Election(int year, int month) throws MyException {
        finalResults = new HashMap<>();
        setElectionDate(year, month);
        voters = new Set<>();
        politicalParties = new ArrayList<>();
        ballotBoxes = new ArrayList<>();
    }

    public List<PoliticalParty> getPoliticalParties() {
        return politicalParties;
    }

    public void addCitizen(Citizen c) throws MyException {
        if (c.getAge() >= 18) {
            voters.add(c);
            assignVotersToBoxes();
        }
    }

    public HashMap<PoliticalParty, Integer> getFinalResults() {
        return finalResults;
    }

    public Set<Citizen> getVoters() {
        return voters;
    }

    public List<BallotBox<? extends Citizen>> getBallotBoxes() {
        return ballotBoxes;
    }

    public boolean removeCitizen(Citizen c) {
        return voters.remove(c);
    }

    public void addRandomCitizen(boolean assignToBox) throws MyException {
        int randomInt = HelperUtilities.getRandomInt(0, 3);
        Citizen temp;
        switch (randomInt) {
            case 0:
                temp = new Citizen();
                break;
            case 1:
                temp = new Soldier();
                break;
            case 2:
                temp = new SickCitizen();
                break;
            case 3:
                temp = new SickSoldier();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + randomInt);
        }
        voters.add(temp);
        if (assignToBox) //Optimization for very large numbers of citizens
            assignVotersToBoxes();
    }

    public void addNumOfRandomCitizens(int amount) throws MyException {
        for (int i = 0; i < amount; i++) {
            addRandomCitizen(false); //Optimization for very large numbers of citizens
        }
        assignVotersToBoxes();
    }

    /*
     * In all methods of adding parties and ballots, we update all the existing ones
     * with the addition.
     */
    public boolean addParty(PoliticalParty party) {
        for (PoliticalParty pp : politicalParties) {
            if (pp.getName().equals(party.getName()))
                return false;
        }
        politicalParties.add(party);
        addPartyToBallots(party);
        return true;
    }

    public boolean removeParty(PoliticalParty party) {
        if (politicalParties.remove(party)) {
            removePartyFromBallots(party);
            return true;
        }
        return false;
    }

    public boolean addRandomParty() throws MyException {
        if (checkIfMaxNumRandomPartiesReached())
            return false;
        PoliticalParty temp;
        while (true) {
            temp = new PoliticalParty();
            if (checkPartyNameAvailable(temp.getName())) {
                politicalParties.add(temp);
                addPartyToBallots(temp);
                return true;
            }
        }
    }

    private boolean checkPartyNameAvailable(String partyName) {
        for (PoliticalParty pp : politicalParties) {
            if (pp.getName().equals(partyName))
                return false;
        }
        return true;
    }

    private boolean checkIfMaxNumRandomPartiesReached() {
        return HelperUtilities.defaultParties.stream().filter(partyName ->
                !checkPartyNameAvailable(partyName)).count() == HelperUtilities.defaultParties.size();
    }

    public void addNumOfRandomParties(int amount) throws MyException {
        if (checkIfMaxNumRandomPartiesReached())
            throw new MyException("Max Num Of Random Parties Reached! (" + Election.MAX_NUM_OF_PARTIES + ")");
        if (amount > MAX_NUM_OF_PARTIES)
            amount = MAX_NUM_OF_PARTIES;
        for (int i = 0; i < amount; ) {
            if (politicalParties.size() >= MAX_NUM_OF_PARTIES)
                break;
            if (addRandomParty())
                i++;
        }
    }

    private void addPartyToBallots(PoliticalParty party) {
        ballotBoxes.forEach(b -> b.getVotingResults().put(party, 0));
    }

    private void removePartyFromBallots(PoliticalParty party) {
        ballotBoxes.forEach(b -> b.getVotingResults().remove(party));
    }

    public void addBallotBox(BallotBox<?> b) {
        addExistingPartiesToBallot(b);
        ballotBoxes.add(b);
    }

    public void addBallotBox(String type, String address) throws MyException {
        Class<? extends Citizen> classType;
        switch (type.toUpperCase()) {
            case "REGULAR":
                classType = Citizen.class;
                break;
            case "SICK":
                classType = SickCitizen.class;
                break;
            case "SOLDIER":
                classType = Soldier.class;
                break;
            case "SICKSOLDIER":
                classType = SickSoldier.class;
                break;
            default:
                throw new MyException("Unexpected type: " + type);
        }
        addBallotBox(new BallotBox<>(address, classType));
    }

    public boolean removeBallotBox(BallotBox<?> b) {
        return ballotBoxes.remove(b);
    }

    private void addExistingPartiesToBallot(BallotBox<?> b) {
        politicalParties.forEach(p -> b.getVotingResults().put(p, 0));
    }

    public void addRandomBallotBox() throws MyException {
        int randomInt = HelperUtilities.getRandomInt(0, 3);
        if (randomInt == 0) {
            BallotBox<Citizen> temp = new BallotBox<>(Citizen.class);
            addBallotBox(temp);
        } else if (randomInt == 1) {
            BallotBox<SickCitizen> temp = new BallotBox<>(SickCitizen.class);
            addBallotBox(temp);
        } else if (randomInt == 2) {
            BallotBox<Soldier> temp = new BallotBox<>(Soldier.class);
            addBallotBox(temp);
        } else if (randomInt == 3) {
            BallotBox<SickSoldier> temp = new BallotBox<>(SickSoldier.class);
            addBallotBox(temp);
        } else
            throw new MyException("unexpected value: " + randomInt);
    }


    public void addNumOfRandomBallots(int amount) throws MyException {
        for (int i = 0; i < amount; i++) {
            addRandomBallotBox();
        }
    }

    public void addNumOfRandomNominees(int amount) throws MyException {
        for (int i = 0; i < amount; i++) {
            addRandomNomineeToRandomParty();
        }
    }

    public void addRandomNomineeToRandomParty() throws MyException {
        if (politicalParties.isEmpty()) {
            politicalParties.add(new PoliticalParty());
            politicalParties.get(0).addRandomNominee();
        } else
            politicalParties.get(HelperUtilities.getRandomInt(0, politicalParties.size() - 1)).addRandomNominee();
        addNomineesToVoters();
    }

    public void addNominee(String name, int ID, int yob, String party) throws MyException {
        boolean found = false;
        for (PoliticalParty pp : getPoliticalParties()) {
            if (pp.getName().equals(party)) {
                found = true;
                pp.getNominees().add(new Nominee(pp, name, ID, yob));
                break;
            }
        }
        if (!found) {
            PoliticalParty newParty = new PoliticalParty(party);
            newParty.addNominee(new Nominee(newParty, name, ID, yob));
            addParty(newParty);
        }
        addNomineesToVoters();
    }


    public void addRandomBoxOfType(Class<? extends Citizen> type) throws MyException {
        if (type.equals(Citizen.class)) {
            BallotBox<Citizen> citizenBallotBox = new BallotBox<>(Citizen.class);
            addBallotBox(citizenBallotBox);
        } else if (type.equals(SickCitizen.class)) {
            BallotBox<SickCitizen> sickCitizenBallotBox = new BallotBox<>(SickCitizen.class);
            addBallotBox(sickCitizenBallotBox);
        } else if (type.equals(Soldier.class)) {
            BallotBox<Soldier> soldierBallotBox = new BallotBox<>(Soldier.class);
            addBallotBox(soldierBallotBox);
        } else if (type.equals(SickSoldier.class)) {
            BallotBox<SickSoldier> sickSoldierBallotBox = new BallotBox<>(SickSoldier.class);
            addBallotBox(sickSoldierBallotBox);
        } else throw new MyException("unexpected type: " + type.getSimpleName());
    }

    public void setElectionDate(int year, int month) throws MyException {
        if (year > LocalDate.now().getYear() - 5 && year < LocalDate.now().getYear() + 5 && month > 0 && month <= 12) {
            electionDate = YearMonth.of(year, month);
        } else throw new MyException("Year and month not in legal range! " + "year=" + year + " month=" + month);

    }

    private void addNomineesToVoters() throws MyException {
        for (PoliticalParty pp : politicalParties)
            for (Nominee n : pp.getNominees())
                if (!voters.contains(n))
                    voters.add(n);
    }

    /*
     * 1. makes nominees able to vote 2. checks if voter isn't assigned already to a
     * ballot. 3. checks if the voter's designated ballot exists and if not adds it.
     * 4. adds the voter to a random box from the list that matches his type.
     */
    public void assignVotersToBoxes() throws MyException {
        addNomineesToVoters();
        for (Citizen v : voters.getSet()) {
            if (!v.isAssignedToBox()) {
                checkAndInitNeededBallot(getCitizensType(v));
                ballotBoxes.get(HelperUtilities.getRandom(getIndexesOfType(getCitizensType(v))))
                        .addVoter(v);
                v.assignToBox();
            }
        }
    }

    private void checkAndInitNeededBallot(Class<? extends Citizen> citizenType) throws MyException {
        if (getIndexesOfType(citizenType).isEmpty())
            addRandomBoxOfType(citizenType);
    }

    private Class<? extends Citizen> getCitizensType(Citizen citizen) {
        if (citizen instanceof Nominee)
            return Citizen.class;
        return citizen.getClass();
    }

    private List<Integer> getIndexesOfType(Class<? extends Citizen> type) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < ballotBoxes.size(); i++) {
            if (ballotBoxes.get(i).type.getSimpleName().equals(type.getSimpleName()))
                list.add(i);
        }
        return list;
    }

    public void randomVote() throws MyException {
        assignVotersToBoxes();
        for (BallotBox<?> b : ballotBoxes) {
            b.randomVote(politicalParties);
        }
        calculateFinalResults();
    }

    // merge all the boxes' results into a single dictionary
    public void calculateFinalResults() {
        politicalParties.forEach(pp -> finalResults.put(pp, 0));
        for (PoliticalParty party : politicalParties) {
            for (BallotBox<?> b : ballotBoxes) {
                int votesInBallot = b.getVotingResults().isEmpty() ? 0 : b.getVotingResults().get(party);
                int votesCounted = finalResults.get(party);
                finalResults.put(party, votesCounted + votesInBallot);
            }

        }
    }

    // compare the integer part of the string we built for each key and value
    private final Comparator<String> compareByVotes = (r1, r2) -> Integer.valueOf(r2.substring(r2.lastIndexOf("-") + 1))
            .compareTo(Integer.valueOf(r1.substring(r1.lastIndexOf("-") + 1)));

    public List<String> buildStringListFromHashMap(HashMap<PoliticalParty, Integer> results) {
        List<String> sortedResults;
        StringBuilder strb = new StringBuilder();
        results.forEach((key, value) -> strb.append(key.getName()).append("-").append(value).append("@"));
        sortedResults = Arrays.asList(strb.toString().split("@"));
        sortedResults.sort(compareByVotes);
        return sortedResults;
    }

    public String getWinningPartyName(List<String> results) {
        return results.get(0).substring(0, results.get(0).lastIndexOf('-') + 1);
    }

    public String getPrimeMinister(String winningParty) {
        for (PoliticalParty pp : politicalParties) {
            if (pp.getName().equals(winningParty.substring(0, winningParty.lastIndexOf("-"))) &&
                    !pp.getNominees().isEmpty()) {
                return ">The elected Prime Minister is:\n" +
                        pp.getNominees().get(0).toString().replace("<Nominee>", "");
            }
        }
        return "";
    }

    public double getPercentageOfVoters() {
        double percentagesSum = 0;
        double nonEmptyBallots = 0;
        for (BallotBox<?> b : ballotBoxes) {
            if (b.getPercentageOfVoters() != 0) {
                percentagesSum += b.getPercentageOfVoters();
                nonEmptyBallots++;
            }
        }
        return percentagesSum / nonEmptyBallots;
    }

    public String toString() {
        StringBuilder stb = new StringBuilder();
        stb.append("Election: " + "electionDate=").append(electionDate).append("\nvoters:\n");
        stb.append(voters);
        stb.append("Political Parties:\n");
        for (PoliticalParty pp : politicalParties) {
            stb.append(pp).append("\n");
        }
        stb.append("Ballot Boxes:\n");
        for (BallotBox<?> bb : ballotBoxes) {
            stb.append(bb).append("\n");
        }
        stb.append('}');
        return stb.toString();
    }

    public boolean equals(Election other) {
        if (this.electionDate != other.electionDate || this.voters.size() != other.voters.size()
                || this.ballotBoxes.size() != other.ballotBoxes.size()
                || this.politicalParties.size() != other.politicalParties.size())
            return false;
        for (int i = 0; i < voters.size(); i++) {
            if (!this.voters.get(i).equals(other.voters.get(i)))
                return false;
        }
        for (int i = 0; i < politicalParties.size(); i++) {
            if (!this.politicalParties.get(i).equals(other.politicalParties.get(i)))
                return false;
        }
        for (int i = 0; i < ballotBoxes.size(); i++) {
            if (!this.ballotBoxes.get(i).equals(other.ballotBoxes.get(i)))
                return false;
        }
        return true;
    }


}
