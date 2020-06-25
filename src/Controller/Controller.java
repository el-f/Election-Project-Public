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

    private Election election;
    private View view;

    public Controller(Election _election, View _view) {
        election = _election;
        view = _view;

        /*
            BallotBoxes menu events
         */
        view.addEventToShowBB(event ->
                view.setToBallotsTable(election.getBallotBoxes()));

        view.addEventToAddBM(event -> {
            TypeForm typeForm = new TypeForm();
            view.setCenterToVBox(typeForm.formView);
            view.setCurrentView(Views.Form);
            typeForm.addEventToSubmitButton(addBBMSecondForm(typeForm));
        });

        view.addEventToAddRB(event -> {
            try {
                election.addRandomBallotBox();
                updateForSuccess();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventToAddMRB(event -> {
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
        view.addEventToShowAC(event ->
                view.setToCitizensTable(election.getVoters().getSet()));

        view.addEventToAddCM(event -> {
            TypeForm typeForm = new TypeForm();
            view.setCenterToVBox(typeForm.formView);
            view.setCurrentView(Views.Form);
            typeForm.addEventToSubmitButton(addCMSecondForm(typeForm));
        });

        view.addEventToAddRC(event -> {
            try {
                election.addRandomCitizen(true);
                updateForSuccess();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventToAddMRC(event -> {
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
        view.addEventToShowAP(event ->
                view.setToPartiesTable(election.getPoliticalParties()));

        view.addEventToAddPM(event -> {
            PartyInitForm partyInitForm = new PartyInitForm();
            view.setCenterToVBox(partyInitForm.formView);
            view.setCurrentView(Views.Form);
            partyInitForm.addEventToSubmitButton(addPMFinishedForm(partyInitForm));
        });

        view.addEventToAddRP(event -> {
            try {
                if (election.addRandomParty())
                    updateForSuccess();
                else
                    view.showAlert(AlertType.ERROR, "Max Num Of Random Parties Reached! (" + Election.MAX_NUM_OF_PARTIES + ")");
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventToAddMRP(event -> {
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
        view.addEventToAddNM(event -> {
            CitizenInitForm citizenInitForm = new CitizenInitForm("nominee");
            view.setCenterToVBox(citizenInitForm.formView);
            view.setCurrentView(Views.Form);
            citizenInitForm.addEventToSubmitButton(addCMFinishedSecondForm(citizenInitForm));
        });

        view.addEventToAddRN(event -> {
            try {
                election.addRandomNomineeToRandomParty();
                updateForSuccess();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventToAddMRN(event -> {
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
        view.addEventToElect(event -> {
            try {
                election.randomVote();
                view.enableVotesResults();
                view.triggerShowResults();
            } catch (Exception exception) {
                alertForException(exception);
            }
        });

        view.addEventToShowResults(event -> {
            List<String> results = election.buildStringListFromHashMap(election.getFinalResults());
            String winningParty = election.getWinningPartyName(results);
            view.setToElectionResults(
                    results,
                    election.getPercentageOfVoters(),
                    election.getPrimeMinister(winningParty),
                    winningParty);
        });

        //Misc menu events
        view.addEventToClearScene(event -> view.setToMainView(
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
                citizenInitForm.addEventToSubmitButton(addCMFinishedSecondForm(citizenInitForm));
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
                ballotInitForm.addEventToSubmitButton(addBBMFinishedSecondForm(ballotInitForm));
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
        String message;
        if (exception instanceof MyException)
            message = exception.getMessage();
        else {
            message = "Error! " + exception.getClass().getSimpleName();
        }
        view.showAlert(AlertType.ERROR, message + ".\nPlease Try Again.");
    }
}
