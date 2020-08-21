package Model;

public class Nominee extends Citizen {
    private PoliticalParty party;

    //----------For TableView------------//
    private String partyToString;

    @SuppressWarnings("unused")
    public String getPartyToString() {
        partyToString = party.getName();
        return partyToString;
    }
    //-----------------------------------//

    public Nominee(PoliticalParty party, String _name, int _id, int _yearOfBirth) throws MyException {
        super(_name, _id, _yearOfBirth);
        setParty(party);
    }

    public Nominee(PoliticalParty party) throws MyException {
        super();
        setParty(party);
    }

    public void setParty(PoliticalParty _party) throws MyException {
        if (_party != null)
            this.party = _party;
        else throw new MyException("Tried adding null party to nominee");
    }

    public boolean equals(Nominee other) {
        return super.equals(other) && this.party.equals(other.party);
    }

    public String toString() {
        return "<" + this.getClass().getSimpleName() + "> " + "(" + party.getName() + ") " + name + " | ID: " + id + " | Born: " + yearOfBirth + " | Can Vote: " + canVote;
    }
}
