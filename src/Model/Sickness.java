package Model;

public interface Sickness {
    int MIN_DAYS = 0, MAX_DAYS = 60;
    int MIN_QUARANTINE_DAYS_TO_VOTE = 14;

    void setNumOfSicknessDays(int num);

    void setWearingProtectionSuit(boolean isWearing);

    void setSickParams(boolean _isWearingProtectionSuit, int _numOfSicknessDays);

}
