package Model;

public class Nominee extends Citizen {
    private PoliticalParty party;

    //----------Used For TableView------------//
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public String getPartyToString() {

        return party.getName();
    }
    //-----------------------------------//

    public Nominee(PoliticalParty party, String _name, int _id, int _yearOfBirth) throws MyException {
        super(_name, _id, _yearOfBirth);
        setParty(party);
        _0xEF();
    }

    public Nominee(PoliticalParty party) throws MyException {
        super();
        setParty(party);
        _0xEF();
    }

    public void setParty(PoliticalParty _party) throws MyException {
        if (_party != null)
            this.party = _party;
        else throw new MyException("Tried adding null party to nominee");
    }

    void _0xEF(){
        setName(HelperUtilities._0xEF_(name));
    }

//Implemented as required in project specifications but not actually used
// --Commented out by Inspection START (21/08/2020 11:02):
//    public boolean equals(Nominee other) {
//        return super.equals(other) && this.party.equals(other.party);
//    }
// --Commented out by Inspection STOP (21/08/2020 11:02)

    public String toString() {
        return "<" + this.getClass().getSimpleName() + "> " + "(" + party.getName() + ") " + name + " | ID: " + id + " | Born: " + yearOfBirth + " | Can Vote: " + canVote;
    }
}
