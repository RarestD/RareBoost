package rare.creations.RareBoost.controllers;


import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.StringConverter;
import javafx.util.converter.CharacterStringConverter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import rare.creations.RareBoost.entity.Constellation;
import rare.creations.RareBoost.entity.ModifierException;
import rare.creations.RareBoost.service.Asterism;


import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadPoolExecutor;

@Scope("prototype")
@Component
@Controller
public class ConstellationScreenController implements Initializable{

    ConfigurableApplicationContext applicationContext;
    Asterism asterism;

    RareBoostController boostController;

    public ConstellationScreenController(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.asterism = applicationContext.getBean(Asterism.class);
        this.boostController = applicationContext.getBean(RareBoostController.class);
    }

    public String stickNotes = "M64 32C28.7 32 0 60.7 0 96V416c0 35.3 28.7 64 64 64H288V368c0-26.5 21.5-48 48-48H448V96c0-35.3-28.7-64-64-64H64zM448 352H402.7 336c-8.8 0-16 7.2-16 16v66.7V480l32-32 64-64 32-32z";
    public String exclamation = "M569.5 440C588 472 564.8 512 527.9 512H48.1c-36.9 0-60-40.1-41.6-72L246.4 24c18.5-32 64.7-32 83.2 0l239.9 416zM288 354c-25.4 0-46 20.6-46 46s20.6 46 46 46 46-20.6 46-46-20.6-46-46-46zm-43.7-165.3l7.4 136c.3 6.4 5.6 11.3 12 11.3h48.5c6.4 0 11.6-5 12-11.3l7.4-136c.4-6.9-5.1-12.7-12-12.7h-63.4c-6.9 0-12.4 5.8-12 12.7z";

    public String exclamationFill = "#e53206";


    public String stickStart = "#00B353";

    public String stickFill = "#0096c7";

    Constellation cons = new Constellation();

    @FXML
    MFXComboBox<String> delay_box, delay_range_box;

    @FXML
    MFXButton start_button;

    @FXML
    MFXTextField channelId, delayId, startHour, startMinute, stopHour, stopMinute, delayRangeMax, delayRangeMin, authField;

    @FXML
    HBox box_start, f_hbox;

    @FXML
    JFXTextArea messageText;

    @FXML
    Label status_label;

    @FXML
    HBox noteBoxes;

    @FXML
    SVGPath svgImage;

    @FXML
    MFXToggleButton delayRangeToggle, schedulerToggle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        limitJFX(messageText, 2000);
        messageText.addEventFilter(KeyEvent.KEY_TYPED, this::setupLimitedTextArea);
        delay_box.getItems().addAll("S", "M", "H");
        delay_range_box.getItems().addAll("S", "M", "H");
        delay_range_box.getSelectionModel().selectItem("S");
        delay_box.getSelectionModel().selectItem("S");
        channelId.addEventFilter(KeyEvent.KEY_TYPED, this::checkInput);
        delayId.addEventFilter(KeyEvent.KEY_TYPED, this::checkInput);
        delayRangeMax.addEventFilter(KeyEvent.KEY_TYPED, this::checkInput);
        delayRangeMin.addEventFilter(KeyEvent.KEY_TYPED, this::checkInput);
        startHour.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput23);
        stopHour.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput23);
        startMinute.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput60);
        stopMinute.addEventFilter(KeyEvent.KEY_TYPED, this::validateNumericInput60);
        delayRangeMin.setDisable(true);
        delayRangeMax.setDisable(true);
        delay_range_box.setDisable(true);
        startHour.setDisable(true);
        stopHour.setDisable(true);
        startMinute.setDisable(true);
        stopMinute.setDisable(true);
    }

    public void startInit(){
        delay_range_box.getSelectionModel().selectLast();
        delay_box.getSelectionModel().selectLast();
        delay_range_box.getSelectionModel().selectFirst();
        delay_box.getSelectionModel().selectFirst();
        delayRangeMin.setDisable(true);
        delayRangeMax.setDisable(true);
        delay_range_box.setDisable(true);
        startHour.setDisable(true);
        stopHour.setDisable(true);
        startMinute.setDisable(true);
        stopMinute.setDisable(true);
        delayRangeToggle.setSelected(false);
        schedulerToggle.setSelected(false);
        createConstellation();
    }

    private void createConstellation(){
        cons.setName("default");
        asterism.addConstellation(cons, this);
    }

    @FXML
    private void checkInput(KeyEvent event){
        if (event.getCharacter().matches("^(?!\\d+$).*")){
            event.consume();
        }
    }

    @FXML
    private void validateNumericInput23(KeyEvent event) {
        MFXTextField textField = (MFXTextField) event.getSource();
        String input = event.getCharacter();

        // Allow only numeric input
        if (!input.matches("[0-9]")) {
            event.consume();
        }

        // Limit the value to 23 (or any other limit you desire)
        String newValue = textField.getText() + input;
        try {
            int intValue = Integer.parseInt(newValue);
            if (intValue > 23 || textField.getText().length() >= 2 ) {
                event.consume();
            }
        } catch (NumberFormatException e) {
            // Handle non-numeric input (e.g., empty string)
            event.consume();
        }
    }

    @FXML
    private void validateNumericInput60(KeyEvent event) {
        MFXTextField textField = (MFXTextField) event.getSource();
        String input = event.getCharacter();

        // Allow only numeric input
        if (!input.matches("[0-9]")) {
            event.consume();
        }

        // Limit the value to 23 (or any other limit you desire)
        String newValue = textField.getText() + input;
        try {
            int intValue = Integer.parseInt(newValue);
            if (intValue > 59 || textField.getText().length() >= 2 ) {
                event.consume();
            }
        } catch (NumberFormatException e) {
            // Handle non-numeric input (e.g., empty string)
            event.consume();
        }
    }

    @FXML
    private void onStart(ActionEvent event){
        int index = boostController.selectedIndex;
        boolean isScheduled = false;
        if (schedulerToggle.isSelected()) {
            isScheduled = true;
        }
        if (delayRangeToggle.isSelected()){
            startRange(index, isScheduled);
        } else if (!delayRangeToggle.isSelected()) {
            startNormal(index, isScheduled);
        }else {
            System.out.println("Error");
        }
    }

    public void changeStatus(String sgvPath, String info, String css, String fill){
        Platform.runLater(() -> {
            svgImage.setContent(sgvPath);
            svgImage.setFill(Color.valueOf(fill));
            noteBoxes.setId(css);
            status_label.setText(info);
        });
    }

    @FXML
    private void onStop(ActionEvent event){
        System.out.println(asterism.getAsterism().size());
        int index = boostController.selectedIndex;
        asterism.stopConstellation(index);
        changeStatus(stickNotes, "Constellation Stopped", "noteBoxesInfo", stickFill);
    }

    @FXML
    private void onDelayRangeSelected(ActionEvent event){
        if (delayRangeToggle.isSelected()){
            delayRangeMin.setDisable(false);
            delayRangeMax.setDisable(false);
            delay_range_box.setDisable(false);
            delayId.setDisable(true);
            delay_box.setDisable(true);
        }else {
            delayRangeMin.setDisable(true);
            delayRangeMax.setDisable(true);
            delay_range_box.setDisable(true);
            delayId.setDisable(false);
            delay_box.setDisable(false);
        }
    }

    @FXML
    private void onSchedulerSelected(ActionEvent event){
        if (schedulerToggle.isSelected()){
            startHour.setDisable(false);
            stopHour.setDisable(false);
            startMinute.setDisable(false);
            stopMinute.setDisable(false);
        }else {
            startHour.setDisable(true);
            stopHour.setDisable(true);
            startMinute.setDisable(true);
            stopMinute.setDisable(true);
        }
    }

    private void setupLimitedTextArea(KeyEvent event) {
        if (event.isShortcutDown() && event.getCode().equals(javafx.scene.input.KeyCode.V)) {
            // Ctrl+V or Command+V pressed

            // Get the content from the clipboard
            Clipboard clipboard = Clipboard.getSystemClipboard();
            if (clipboard.hasString()) {
                String pastedText = clipboard.getString();

                // Check if the new text length exceeds the limit
                if ((messageText.getText() + pastedText).length() > 2000) {
                    event.consume(); // Cancel the event to prevent pasting
                } else {
                    // Allow pasting
                }
            }
        }
    }

    private void limitJFX(JFXTextArea jfxTextArea, int MAX_LENGTH){
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.isContentChange()) {
                // Check if pasting
                if (change.getControlText().isEmpty() && !change.getText().isEmpty()) {
                    // Pasting: check if the new text length exceeds the limit
                    if ((jfxTextArea.getText() + change.getText()).length() > MAX_LENGTH) {
                        return null; // Reject the change
                    }
                } else {
                    // Typing manually: check if the total text length exceeds the limit
                    if ((jfxTextArea.getText() + change.getText()).length() > MAX_LENGTH) {
                        return null; // Reject the change
                    }
                }
            }
            return change;
        });
        jfxTextArea.setTextFormatter(textFormatter);
    }

    private void startNormal(int index, boolean isScheduled){
        if (!checkValid()){
            return;
        }
        cons.setAuth(authField.getText());
        cons.setMessage(messageText.getText());
        switch (delay_box.getValue()){
            case "S" -> {
                cons.setDelay(Duration.ofSeconds(Long.parseLong(delayId.getText())));
            }
            case "M" -> {
                cons.setDelay(Duration.ofMinutes(Long.parseLong(delayId.getText())));
            }
            case "H" -> {
                cons.setDelay(Duration.ofHours(Long.parseLong(delayId.getText())));
            }
            default -> throw new IllegalStateException("Unexpected value: " + delay_box.getValue());
        }
        cons.setDestination(channelId.getText());
        if (isScheduled){
            LocalTime targetTime = LocalTime.of(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinute.getText()));
            LocalTime endTime = LocalTime.of(Integer.parseInt(stopHour.getText()), Integer.parseInt(stopMinute.getText()));
            asterism.editFullConstellationSchduler(index, cons, this, targetTime, endTime);
        }
        else {
            asterism.editFullConstellation(index, cons, this);
        }
        asterism.getAsterismConfig().set(index, cons);
        if (!asterism.getAsterism().get(index).isAlive()) {
            asterism.startConstellation(index);
            changeStatus(stickNotes, "Constellation Running", "noteBoxesRun", stickStart);
        }
    }

    private void startRange(int index, boolean isScheduled){
        if (!checkValid()){
            return;
        }
        int delayMIN = Integer.parseInt(delayRangeMin.getText());
        int delayMAX = Integer.parseInt(delayRangeMax.getText());
        if (delayMIN > delayMAX){
            changeStatus(exclamation, "Range Delay MIN > MAX", "noteBoxes", exclamationFill);
            return;
        }
        cons.setAuth(authField.getText());
        cons.setMessage(messageText.getText());
        cons.setDelayMin(delayMIN);
        cons.setDelayMax(delayMAX);
        cons.setDelayDuration(delay_range_box.getValue());
        cons.setDestination(channelId.getText());
        if (isScheduled){
            LocalTime targetTime = LocalTime.of(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinute.getText()));
            LocalTime endTime = LocalTime.of(Integer.parseInt(stopHour.getText()), Integer.parseInt(stopMinute.getText()));
            asterism.editFullConstellationSchdulerRandom(index, cons, this, targetTime, endTime);
        }
        else {
            asterism.editFullConstellationRandom(index, cons, this);
        }
        asterism.getAsterismConfig().set(index, cons);
        if (!asterism.getAsterism().get(index).isAlive()) {
            asterism.startConstellation(index);
            changeStatus(stickNotes, "Constellation Running", "noteBoxesRun", stickStart);
        }
    }

    private boolean checkValid(){
        if (authField.getText().isEmpty() || authField.getText().isBlank()){
            changeStatus(exclamation, "Authorization is Empty", "noteBoxes", exclamationFill);
            return false;
        }else if (channelId.getText().isEmpty() || channelId.getText().isBlank()){
            changeStatus(exclamation, "Channel ID is Empty", "noteBoxes", exclamationFill);
            return false;
        }else if (messageText.getText().isEmpty() || messageText.getText().isBlank()){
            changeStatus(exclamation, "Text Message is Empty", "noteBoxes", exclamationFill);
            return false;
        }
        if (delayRangeToggle.isSelected()){
            if (delayRangeMin.getText().isEmpty() || delayRangeMin.getText().isBlank()){
                changeStatus(exclamation, "Range Delay MIN's Empty", "noteBoxes", exclamationFill);
                return false;
            }else if (delayRangeMax.getText().isEmpty() || delayRangeMax.getText().isBlank()){
                changeStatus(exclamation, "Range Delay MAX's Empty", "noteBoxes", exclamationFill);
                return false;
            }
        }else {
            if (delayId.getText().isEmpty() || delayId.getText().isBlank()){
                changeStatus(exclamation, "Delay is Empty", "noteBoxes", exclamationFill);
                return false;
            }
        }
        return true;
    }



}
