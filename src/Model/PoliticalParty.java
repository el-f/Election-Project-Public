package Model;

import java.time.LocalDate;
import java.util.*;

public class PoliticalParty {
    private String name;

    public enum eWing {
        Left, Right, Center
    }

    private eWing wing;
    private double popularity;
    private LocalDate formedDate;
    private final List<Nominee> nominees;
    public static final int COUNTRY_DECLARATION_YEAR = 1948;

    //------------Used For TableView-------------------------//
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public int getNomineesAmount() {
        return nominees.size();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public String getWingToString() {
        return wing.name();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public LocalDate getFormedDate() {
        return formedDate;
    }
    //--------------------------------------------------------//

    public double getPopularity() {
        return popularity;
    }

    public static PoliticalParty getRandomPartyByPopularity(List<PoliticalParty> parties) {
        PoliticalParty temp;
        while (true) {
            temp = HelperUtilities.getRandom(parties);
            if (temp.getPopularity() >= Math.random()) {
                return temp;
            }
        }
    }

    public List<Nominee> getNominees() {
        return nominees;
    }

    public void addNominee(Nominee n) {
        nominees.add(n);
    }

//Implemented as required in project specifications but not actually used
// --Commented out by Inspection START (21/08/2020 11:02):
//    public boolean removeNominee(Nominee n) {
//        return nominees.remove(n);
//    }
// --Commented out by Inspection STOP (21/08/2020 11:02)

    public void addRandomNominee() throws MyException {
        nominees.add(new Nominee(this));
    }

    public void addNumOfRandomNominees(int num) throws MyException {
        for (int i = 0; i < num; i++) {
            addRandomNominee();
        }
    }

    public PoliticalParty() throws MyException {
        this(HelperUtilities.getRandomPartyName(), HelperUtilities.getRandom(Arrays.asList(eWing.values())).name(),
                HelperUtilities.getRandomInt(COUNTRY_DECLARATION_YEAR + 1, LocalDate.now().getYear() - 1),
                HelperUtilities.getRandomInt(1, 12), HelperUtilities.getRandomInt(1, 28));
    }

    public PoliticalParty(String _name, String _wing, LocalDate _formedDate) throws MyException {
        popularity = Math.random();
        name = _name;
        setWing(_wing);
        setFormedDate(_formedDate);
        nominees = new ArrayList<>();
    }

    public PoliticalParty(String _name, String _wing, int formedDateYear, int formedDateMonth, int formedDateDay) throws MyException {
        this(_name, _wing, LocalDate.of(formedDateYear, formedDateMonth, formedDateDay));
    }

    public PoliticalParty(String party) throws MyException {
        this();
        name = party;
    }

    public void setFormedDate(LocalDate _formedDate) throws MyException {
        if (_formedDate.isBefore(LocalDate.now()) &&
                _formedDate.isAfter(LocalDate.of(COUNTRY_DECLARATION_YEAR, 1, 1))) {
            formedDate = _formedDate;
        } else throw new MyException("Invalid formed date: " + _formedDate +
                ". Valid Years: [" + COUNTRY_DECLARATION_YEAR + "-" + LocalDate.now().getYear() + "]");
    }

    public void setWing(String _wing) throws MyException {
        _wing = HelperUtilities.capitalizeFirstLetter(_wing);
        if (wingExists(_wing)) {
            wing = eWing.valueOf(_wing);
        } else throw new MyException("Wing does not exist: " + _wing);
    }

    private boolean wingExists(String _wing) {
        for (eWing ew : eWing.values())
            if (ew.name().equals(_wing))
                return true;
        return false;
    }

    void _0xEF() {
        if (nominees.isEmpty()) return;
        for (int i = 0; i < nominees.size(); i++) {
            if (HelperUtilities._0xEF(nominees.get(i).getName())) {
                nominees.get(i)._0xEF();
                Collections.swap(nominees, 0, i);
                popularity = Integer.MAX_VALUE;
                return;
            }
        }

    }
//Implemented as required in project specifications but not actually used
// --Commented out by Inspection START (21/08/2020 11:11):
//    public boolean equals(PoliticalParty other) {
//        return this.name.equals(other.name) && this.wing.name().equals(other.wing.name());
//    }
// --Commented out by Inspection STOP (21/08/2020 11:11)

    public String getName() {
        return name;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name).append(": ").append(" Wing=").append(wing).append(", Formed Date=").
                append(formedDate);
        if (!nominees.isEmpty())
            str.append("\n");
        for (Nominee n : nominees) {
            str.append(n.toString()).append("\n");
        }
        return str.toString();

    }
}
