package Controller;

import Model.*;
import View.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

import java.util.List;
import java.util.Optional;

import static View.View.Views;

public class Controller {

    private final Election election;
    private final View view;

    public Controller(Election _election, View _view) {
        election = _election;
        view = _view;

        /*
            BallotBoxes menu events
         */
        view.addEventHandlerToMenuItem("showAB", event ->
                view.setToBallotsTable(election.getBallotBoxes()));

        view.addEventHandlerToMenuItem("addBM", event -> {
            TypeForm typeForm = new TypeForm();
            view.setCenterToVBox(typeForm.formView);
            view.setCurrentView(Views.Form);
            typeForm.addEventHandlerToSubmitButton(addBBMSecondForm(typeForm));
        });

        view.addEventHandlerToMenuItem("addRB", event -> {
            try {
                election.addRandomBallotBox();
                updateForSuccess();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventHandlerToMenuItem("addMRB", event -> {
            try {
                int num = getNumFromUser();
                if (num > 0) {
                    election.addNumOfRandomBallots(num);
                    updateForSuccess();
                }
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        /*
            Citizens menu events
         */
        view.addEventHandlerToMenuItem("showAC", event ->
                view.setToCitizensTable(election.getVoters().getSet()));

        view.addEventHandlerToMenuItem("addCM", event -> {
            TypeForm typeForm = new TypeForm();
            view.setCenterToVBox(typeForm.formView);
            view.setCurrentView(Views.Form);
            typeForm.addEventHandlerToSubmitButton(addCMSecondForm(typeForm));
        });

        view.addEventHandlerToMenuItem("addRC", event -> {
            try {
                election.addRandomCitizen(true);
                updateForSuccess();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventHandlerToMenuItem("addMRC", event -> {
            try {
                int num = getNumFromUser();
                if (num > 0) {
                    election.addNumOfRandomCitizens(num);
                    updateForSuccess();
                }
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        /*
            Parties menu events
         */
        view.addEventHandlerToMenuItem("showAP", event ->
                view.setToPartiesTable(election.getPoliticalParties()));

        view.addEventHandlerToMenuItem("addPM", event -> {
            PartyInitForm partyInitForm = new PartyInitForm();
            view.setCenterToVBox(partyInitForm.formView);
            view.setCurrentView(Views.Form);
            partyInitForm.addEventHandlerToSubmitButton(addPMFinishedForm(partyInitForm));
        });

        view.addEventHandlerToMenuItem("addRP", event -> {
            try {
                if (election.addRandomParty())
                    updateForSuccess();
                else
                    view.showAlert(AlertType.ERROR, "Max Num Of Random Parties Reached! (" + Election.MAX_NUM_OF_PARTIES + ")");
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventHandlerToMenuItem("addMRP", event -> {
            try {
                int num = getNumFromUser();
                if (num > 0) {
                    election.addNumOfRandomParties(num);
                    updateForSuccess();
                }
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        /*
            Nominees menu events
         */
        view.addEventHandlerToMenuItem("addNM", event -> {
            CitizenInitForm citizenInitForm = new CitizenInitForm("nominee");
            view.setCenterToVBox(citizenInitForm.formView);
            view.setCurrentView(Views.Form);
            citizenInitForm.addEventHandlerToSubmitButton(addCMFinishedSecondForm(citizenInitForm));
        });

        view.addEventHandlerToMenuItem("addRN", event -> {
            try {
                election.addRandomNomineeToRandomParty();
                updateForSuccess();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventHandlerToMenuItem("addMRN", event -> {
            try {
                int num = getNumFromUser();
                if (num > 0) {
                    election.addNumOfRandomNominees(num);
                    updateForSuccess();
                }
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        /*
            Election menu events
         */
        view.addEventHandlerToMenuItem("elect", event -> {
            try {
                election.randomVote();
                view.enableVotesResults();
                view.triggerShowResults();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventHandlerToMenuItem("showResults", event -> {
            List<String> results = election.buildStringListFromHashMap(election.getFinalResults());
            String winningParty = election.getWinningPartyName(results);
            view.setToElectionResults(
                    results,
                    election.getPercentageOfVoters(),
                    election.getPrimeMinister(winningParty),
                    winningParty);
        });

        //Misc menu events
        view.addEventHandlerToMenuItem("clear", event -> view.setToMainView(
                election.getBallotBoxes().size(),
                election.getVoters().size(),
                election.getPoliticalParties().size()));
        view.setCurrentView(Views.Main);
        view.refreshCurrentView();
    }

    private EventHandler<ActionEvent> addPMFinishedForm(PartyInitForm partyInitForm) {
        return event -> {
            if (partyInitForm.getAllFilled()) {
                try {
                    if (election.addParty(new PoliticalParty(partyInitForm.name.getText(), partyInitForm.getWing(),
                            Integer.parseInt(partyInitForm.year.getText()),
                            Integer.parseInt(partyInitForm.month.getText()),
                            Integer.parseInt(partyInitForm.day.getText()))))
                        updateForSuccess();
                    else {
                        view.showAlert(AlertType.ERROR, "Party with the same name already exists!");
                    }
                } catch (Exception exception) {
                    alertForException(exception);
                }
            } else {
                view.showAlert(AlertType.ERROR, "Please Fill All Fields!");

            }
        };
    }

    private EventHandler<ActionEvent> addCMSecondForm(TypeForm type) {
        return event -> {
            if (type.getIsAtLeastOneSelected()) {
                CitizenInitForm citizenInitForm = new CitizenInitForm(type.getType());
                view.setCenterToVBox(citizenInitForm.formView);
                citizenInitForm.addEventHandlerToSubmitButton(addCMFinishedSecondForm(citizenInitForm));
            } else {
                view.showAlert(AlertType.ERROR, "Please Choose One!");
            }
        };
    }

    private EventHandler<ActionEvent> addBBMSecondForm(TypeForm type) {
        return event -> {
            if (type.getIsAtLeastOneSelected()) {
                BallotInitForm ballotInitForm = new BallotInitForm(type.getType());
                view.setCenterToVBox(ballotInitForm.formView);
                ballotInitForm.addEventHandlerToSubmitButton(addBBMFinishedSecondForm(ballotInitForm));
            } else {
                view.showAlert(AlertType.ERROR, "Please Choose One!");
            }
        };
    }

    private EventHandler<ActionEvent> addBBMFinishedSecondForm(BallotInitForm ballotInitForm) {
        return event -> {
            if (ballotInitForm.getAllFilled()) {
                try {
                    election.addBallotBox(ballotInitForm.type, ballotInitForm.address.getText());
                    updateForSuccess();
                } catch (Exception exception) {
                    alertForException(exception);
                }
            } else {
                view.showAlert(AlertType.ERROR, "Please Fill All Fields!");
            }
        };

    }

    private EventHandler<ActionEvent> addCMFinishedSecondForm(CitizenInitForm citizenInitForm) {
        return event -> {
            try {
                if (citizenInitForm.getAllFilled()) {
                    boolean success = false;
                    String name = citizenInitForm.name.getText();
                    int ID = Integer.parseInt(citizenInitForm.ID.getText());
                    int yearOfBirth = Integer.parseInt(citizenInitForm.yearOfBirth.getText());
                    switch (citizenInitForm.type) {
                        case "regular":
                            try {
                                election.addCitizen(new Citizen(name, ID, yearOfBirth));
                                success = true;
                            } catch (Exception exception) {
                                alertForException(exception);
                            }
                            break;
                        case "sick":
                            try {
                                election.addCitizen(new SickCitizen(name, ID, yearOfBirth,
                                        citizenInitForm.isWearingProtectionSuit.isSelected(),
                                        Integer.parseInt(citizenInitForm.numSickDays.getText())));
                                success = true;
                            } catch (Exception exception) {
                                alertForException(exception);
                            }
                            break;
                        case "soldier":
                            try {
                                election.addCitizen(new Soldier(name, ID, yearOfBirth,
                                        citizenInitForm.carryWeapon.isSelected()));
                                success = true;
                            } catch (Exception exception) {
                                alertForException(exception);
                            }
                            break;
                        case "sickSoldier":
                            try {
                                election.addCitizen(new SickSoldier(name, ID, yearOfBirth,
                                        citizenInitForm.isWearingProtectionSuit.isSelected(),
                                        Integer.parseInt(citizenInitForm.numSickDays.getText()),
                                        citizenInitForm.carryWeapon.isSelected()));
                                success = true;
                            } catch (Exception exception) {
                                alertForException(exception);
                            }
                            break;
                        case "nominee":
                            try {
                                election.addNominee(citizenInitForm.name.getText(),
                                        Integer.parseInt(citizenInitForm.ID.getText()),
                                        Integer.parseInt(citizenInitForm.yearOfBirth.getText()),
                                        citizenInitForm.party.getText());
                                success = true;
                            } catch (Exception exception) {
                                alertForException(exception);
                            }
                            break;
                        default:
                            throw new MyException("unexpected type: " + citizenInitForm.type);
                    }
                    if (success)
                        updateForSuccess();
                } else {
                    view.showAlert(AlertType.ERROR, "Please Fill All Fields!");
                }
            } catch (Exception exception) {
                alertForException(exception);
            }
        };
    }

    private void updateForSuccess() {
        view.showAlert(AlertType.INFORMATION, "Added Successfully!");
        view.refreshCurrentView();
    }


    private int getNumFromUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Please enter number of randoms to add");
        dialog.setContentText("number:");
        Optional<String> result = dialog.showAndWait();
        return result.map(Integer::parseInt).orElse(0);
    }

    private void alertForException(Exception exception) {
        view.showAlert(AlertType.ERROR, exception.toString());
    }
}
