package Model;

import java.time.LocalDate;

public class Citizen {

    protected String name;
    protected int id;
    protected int yearOfBirth;
    protected boolean canVote;
    protected boolean isAssignedToBox;
    protected boolean hasVoted;
    public static final int MIN_AGE = 21, MAX_AGE = 150;

    //------------Used For TableView-------------------------//
    @SuppressWarnings("unused")
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }
    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }
    @SuppressWarnings("unused")
    public String getTypeToString() {
        return this.getClass().getSimpleName();
    }
    //--------------------------------------------------------//

    public Citizen(String _name, int _id, int _yearOfBirth) throws MyException {
        this(_name, _id);
        setYearOfBirth(_yearOfBirth);
        setCanVote(true);
        isAssignedToBox = false;
    }

    public Citizen() throws MyException {
        this(HelperUtilities.getRandomName(), HelperUtilities.getRandomID(),
                HelperUtilities.getRandomYearForCitizen());
    }

    protected Citizen(String _name, int _id) throws MyException {
        name = _name;
        setID(_id);
    }

    public boolean getCanVote() {
        return canVote;
    }

    public int getAge() {
        return LocalDate.now().getYear() - yearOfBirth;
    }

    public static int getAge(int _yearOfBirth) {
        return LocalDate.now().getYear() - _yearOfBirth;
    }

    public void setYearOfBirth(int _yearOfBirth) throws MyException {
        if (checkValidYearOfBirth(_yearOfBirth)) {
            yearOfBirth = _yearOfBirth;
        } else
            throw new MyException("Invalid year of birth: " + _yearOfBirth +
                    ". Valid Ages: [" + MIN_AGE + "-" + MAX_AGE + "]");
    }

    public static boolean checkValidYearOfBirth(int _yearOfBirth) {
        int age = getAge(_yearOfBirth);
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    private void setID(int _id) throws MyException {
        if (checkValidIDLength(_id))
            id = _id;
        else throw new MyException("Invalid Num of ID Digits! (ID: " + _id + ")");
    }

    public static boolean checkValidIDLength(int id) {
        return Integer.valueOf(id).toString().length() == 9;
    }

    private boolean checkAge() {
        return getAge() >= 18;
    }

    public void setCanVote(boolean _canVote) {
        canVote = checkAge() && _canVote;
    }

    public boolean isAssignedToBox() {
        return isAssignedToBox;
    }

    public void assignToBox() {
        isAssignedToBox = true;
    }

    public boolean equals(Citizen other) {
        return this.getClass().equals(other.getClass()) && this.name.equals(other.name)
                && this.id == other.id && this.yearOfBirth == other.yearOfBirth
                && this.canVote == other.canVote && this.isAssignedToBox == other.isAssignedToBox
                && this.hasVoted == other.hasVoted;
    }

    public String toString() {
        return "<" + this.getClass().getSimpleName() + "> " + name + " | ID: " + id +
                " | Born: " + yearOfBirth + " | Can Vote: " + canVote;
    }
}
