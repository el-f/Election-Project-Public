package Model;

public class Soldier extends Citizen {
    protected boolean carryWeapon;
    public static final int MIN_AGE = 18, MAX_AGE = 21;

    public Soldier() throws MyException {
        this(HelperUtilities.getRandomName(), HelperUtilities.getRandomID(),
                HelperUtilities.getRandomYearForSoldier(), HelperUtilities.getRandomBoolean());
    }

    public Soldier(String _name, int _id, int _yearOfBirth, boolean _carryWeapon) throws MyException {
        super(_name, _id);
        setYearOfBirth(_yearOfBirth);
        setCarryWeapon(_carryWeapon);
        //can vote in ballot only if not carrying weapon
        setCanVote(!carryWeapon);
    }

    public void setCarryWeapon(boolean _carryWeapon) {
        carryWeapon = _carryWeapon;
    }

    //------------Used For TableView------------//
    public boolean isCarryWeapon() {
        return carryWeapon;
    }
    //------------------------------------------//

    public static boolean checkValidYearOfBirth(int _yearOfBirth) {
        int age = getAge(_yearOfBirth);
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    @Override
    public void setYearOfBirth(int _yearOfBirth) throws MyException {
        if (checkValidYearOfBirth(_yearOfBirth))
            yearOfBirth = _yearOfBirth;
        else
            throw new MyException("Invalid year of birth: " + _yearOfBirth +
                    ". Valid Ages: [" + MIN_AGE + "-" + MAX_AGE + "]");

    }

    public boolean equals(Soldier other) {
        return super.equals(other) && carryWeapon == other.carryWeapon;
    }

    public String toString() {
        return super.toString() + " | CarryWeapon: " + carryWeapon;
    }
}
