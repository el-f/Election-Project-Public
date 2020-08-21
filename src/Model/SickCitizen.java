package Model;

public class SickCitizen extends Citizen implements Sickness {
    private int numOfSicknessDays;
    private boolean wearingProtectionSuit;

    //---------------Used For TableView---------------//
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public int getNumOfSicknessDays() {
        return numOfSicknessDays;
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public boolean isWearingProtectionSuit() {
        return wearingProtectionSuit;
    }
    //------------------------------------------------//

    public SickCitizen() throws MyException {
        super();
        setSickParams(HelperUtilities.getRandomBoolean(), HelperUtilities.getRandomInt(MIN_DAYS, MAX_DAYS));
    }

    public void setSickParams(boolean _isWearingProtectionSuit, int _numOfSicknessDays) {
        setNumOfSicknessDays(_numOfSicknessDays);
        setWearingProtectionSuit(_isWearingProtectionSuit);
        //Has to wear protection suit and to be in quarantine for at least a 2 weeks.
        setCanVote(wearingProtectionSuit && numOfSicknessDays >= MIN_QUARANTINE_DAYS_TO_VOTE);
    }

    public SickCitizen(String _name, int _id, int _yearOfBirth, boolean _isWearingProtectionSuit, int _numOfSicknessDays) throws MyException {

        super(_name, _id, _yearOfBirth);
        setSickParams(_isWearingProtectionSuit, _numOfSicknessDays);
    }


    public void setWearingProtectionSuit(boolean wearingProtectionSuit) {
        this.wearingProtectionSuit = wearingProtectionSuit;
    }

    public void setNumOfSicknessDays(int numOfSicknessDays) {
        this.numOfSicknessDays = numOfSicknessDays;
    }

//Implemented as required in project specifications but not actually used
// --Commented out by Inspection START (21/08/2020 11:02):
//    public boolean equals(SickCitizen other) {
//        return super.equals(other) && numOfSicknessDays == other.numOfSicknessDays &&
//                wearingProtectionSuit == other.wearingProtectionSuit;
//    }
// --Commented out by Inspection STOP (21/08/2020 11:02)

    @Override
    public String toString() {
        return super.toString() + " | numOfSicknessDays: " + numOfSicknessDays +
                " | isWearingProtectionSuit: " + wearingProtectionSuit;
    }
}
