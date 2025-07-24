package rare.creations.RareBoost.controllers;

import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import rare.creations.RareBoost.StageInitializer;
import rare.creations.RareBoost.entity.Constellation;
import rare.creations.RareBoost.entity.ConstellationTab;
import rare.creations.RareBoost.entity.DataPair;
import rare.creations.RareBoost.entity.Dock;
import rare.creations.RareBoost.service.Asterism;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@Controller
public class RareBoostController implements Initializable{

    ConfigurableApplicationContext applicationContext;

    Asterism asterism;

    public RareBoostController(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.asterism = applicationContext.getBean(Asterism.class);
    }

    @Getter
    @Setter
    ArrayList<DataPair<VBox, ConstellationScreenController>> unused_screen = new ArrayList<>(10);

    @Getter
    @Setter
    ArrayList<DataPair<VBox, ConstellationScreenController>> used_screen = new ArrayList<>(10);

    @Getter
    @Setter
    ArrayList<Dock<Integer, HBox, VBox>> docks = new ArrayList<>(10);

    @FXML
    BorderPane parent;

    @FXML
    HBox stageparent, addButton, pluscons;

    @FXML
    FontAwesomeIcon iconAdd;

    @FXML
    Label ticket_pass;

    @FXML
    MFXScrollPane constellation;

    @FXML
    VBox cons_vbox, centerVbox;

    private double xOffSet = 0;
    private double yOffSet = 0;

    private int index = 0;

    private VBox writeBox;

    private int maxCons = 4;

    int selectedIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(ticket_pass.getText());
        switch (ticket_pass.getText()){
            case "Interstellar" ->{
                maxCons = 4;
            }
            case "Voyager" -> {
                maxCons = 10;
            }
        }
        try {
            createScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeBox = makeUtil();
        makeStageDraggable();
    }

    private void setScreen(double width, double height){

    }

    private ImageView setView(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setMouseTransparent(true);
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        return imageView;
    }

    private VBox makeUtil(){
        //getting image and set the imageview
        Image write = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/write.png")));
        Image trash = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/trash.png")));
        ImageView writeView = setView(write);
        ImageView trashView = setView(trash);

        //set writebox for base border
        HBox writeBox = new HBox(writeView);
        writeBox.setMinWidth(67);
        writeBox.setMinHeight(45);
        writeBox.setPrefWidth(67);
        writeBox.setPrefHeight(45);
        writeBox.setMaxWidth(67);
        writeBox.setMaxHeight(45);
        writeBox.setId("write_box");
        writeBox.setAlignment(Pos.CENTER);
        writeBox.setOnMouseClicked(this::changedConstellationName);
        //set trashbox for base border
        HBox trashBox = new HBox(trashView);
        trashBox.setMinWidth(67);
        trashBox.setMinHeight(45);
        trashBox.setPrefWidth(67);
        trashBox.setPrefHeight(45);
        trashBox.setMaxWidth(67);
        trashBox.setMaxHeight(45);
        trashBox.setId("trash_box");
        trashBox.setAlignment(Pos.CENTER);
        trashBox.setOnMouseClicked(this::removeConstellation);
        //set and adding the box inside the container
        VBox vBox = new VBox();
        vBox.setId("util_box");
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(writeBox);
        vBox.getChildren().add(trashBox);
        return vBox;
    }

    private void addConstellationPass(int max) throws IOException {
        max -= 1;
        if (index < max){
            HBox constellationHolder = ConstellationTab.createConstellationTab("Constellation " + (index + 1));
            HBox constellationTab = ((HBox) constellationHolder.getChildren().get(0));
            constellationTab.setOnMouseClicked(this::onConsTabClicked);
            HBox node = (HBox) cons_vbox.getChildren().get(cons_vbox.getChildren().size() - 1);
            docks.add(new Dock<>(index, constellationTab, unused_screen.get(0).getItem1()));
            if (asterism.asterism.size() != maxCons && asterism.asterismConfig.size() != maxCons) {
                ConstellationScreenController item2 = unused_screen.get(0).getItem2();
                item2.startInit();
            }
            used_screen.add(unused_screen.get(0));
            unused_screen.remove(0);
            animatePlus(node, constellationTab.getMaxHeight());
            cons_vbox.getChildren().add(index, constellationHolder);
            animateChild(constellationTab);
            index++;
        }else {
            pluscons.setOnMouseClicked(this::doNothing);
            System.out.println("here");
            System.out.println(index);
            HBox constellationHolder = ConstellationTab.createConstellationTab("Constellation " + (index + 1));
            HBox constellationTab = ((HBox) constellationHolder.getChildren().get(0));
            constellationTab.setOnMouseClicked(this::onConsTabClicked);
            Node node = cons_vbox.getChildren().get(cons_vbox.getChildren().size() - 1);
            docks.add(new Dock<>(index, constellationTab, unused_screen.get(0).getItem1()));
            if (asterism.asterism.size() != maxCons && asterism.asterismConfig.size() != maxCons) {
                ConstellationScreenController item2 = unused_screen.get(0).getItem2();
                item2.startInit();
            }
            used_screen.add(unused_screen.get(0));
            unused_screen.remove(0);
            fadeAnimate(node, constellationTab.getMaxHeight());
            cons_vbox.getChildren().add(index, constellationHolder);
            animateChild(constellationTab);
            index++;
        }
    }

    private void doNothing(MouseEvent event){
        // do nothing
    }

    private void onConsTabClicked(MouseEvent event){
        int thisindex = 0;
        for(Node children : cons_vbox.getChildren()){
            HBox holder = ((HBox) children);
            HBox source = (HBox) event.getSource();
            HBox node = ((HBox) holder.getChildren().get(0));
            String id = node.getId();
            if (id != null){
                if (node.getId().equals("selected_cons")) {
                    if (holder.getChildren().size() != 1) {
                        holder.getChildren().remove(holder.getChildren().size() - 1);
                    }
                    MFXTextField label = (MFXTextField) node.getChildren().get(0);
                    label.setId("cons_text");
                    node.setId("constellationtab");
                }
            }
            if (source.equals(node)){
                selectedIndex = thisindex;
                centerVbox.getChildren().clear();
                System.out.println(docks.size());
                System.out.println(docks.get(thisindex).getV2());
                VBox value = docks.get(thisindex).getV2();
                centerVbox.getChildren().add(value);
            }
            thisindex++;
        }
        HBox source = (HBox) event.getSource();
        HBox parent1 = (HBox) source.getParent();
        MFXTextField label = (MFXTextField) source.getChildren().get(0);
        label.setId("selected_cons_text");
        source.setId("selected_cons");
        parent1.getChildren().add(writeBox);
        writeBox.getChildren().get(1).setOnMouseClicked(this::removeConstellation);

    }

    private void createScreen() throws IOException {
        for (int i = 0; i < maxCons ; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConstellationScreen.fxml"));
            loader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            VBox load = loader.load();
            DataPair<VBox, ConstellationScreenController> pair = new DataPair<>(load, loader.getController());
            unused_screen.add(pair);
        }
    }


    private void animateChild(HBox child) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), child);

        // Wait for the layout pass to complete before getting the height
        child.applyCss();
        child.layout();

        // Start from the left of the VBox
        transition.setFromX(-child.getMaxWidth());
        // Move to the original X position
        transition.setToX(0);
        transition.play();
    }

    private void animatePlus(Node child, double from) {
        child.setDisable(true);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), child);

        // Wait for the layout pass to complete before getting the height
        child.applyCss();

        // Start from the left of the VBox
        transition.setFromY(-from - 40);
        // Move to the original X position
        transition.setToY(0);
        transition.play();
        transition.setOnFinished(event -> {
            child.setDisable(false);
        });
    }

    private void deleteAnimate(HBox child){
        child.setDisable(true);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), child);

        // Wait for the layout pass to complete before getting the height
        child.applyCss();
        child.layout();

        transition.setAutoReverse(false);
        // Move to the original X position
        transition.setToX(-child.getPrefWidth());
        System.out.println(-child.getPrefWidth());
        transition.play();
        transition.setOnFinished(event -> {
            cons_vbox.getChildren().remove(child);
            System.out.println(index);
            if (index >= maxCons - 1){
                if (!cons_vbox.getChildren().contains(addButton)){
                    cons_vbox.getChildren().add(addButton);
                    addButton.setOpacity(1.0);
                    pluscons.setOnMouseClicked(event1 -> {
                        try {
                            addConstellationTab(event1);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            child.setDisable(false);
            event.consume();
        });
    }

    private void fadeAnimate(Node node, double from){
        node.setDisable(true);
        TranslateTransition translate = new TranslateTransition(Duration.millis(100), node);

        // Wait for the layout pass to complete before getting the height
        node.applyCss();

        // Start from the left of the VBox
        translate.setFromY(-from - 40);
        // Move to the original X position
        translate.setToY(0);
        FadeTransition transition =  new FadeTransition(Duration.millis(100), node);

        transition.setToValue(0);
        translate.play();
        translate.setOnFinished(event -> {
            transition.play();
            transition.setOnFinished(fadeEvent ->{
                node.setDisable(false);
                cons_vbox.getChildren().remove(addButton);
            });
        });

    }

    @FXML
    private void addConstellationTab(MouseEvent event) throws IOException {
        addConstellationPass(maxCons);

    }



    private void makeStageDraggable(){
        stageparent.setOnMousePressed(mouseEvent -> {
            yOffSet = mouseEvent.getSceneY();
            xOffSet = mouseEvent.getSceneX();
        });
        stageparent.setOnMouseDragged(mouseEvent -> {
            if (StageInitializer.stage.isFullScreen()) {
                StageInitializer.stage.setFullScreen(false);
            }
            StageInitializer.stage.setX(mouseEvent.getScreenX() - xOffSet);
            StageInitializer.stage.setY(mouseEvent.getScreenY() - yOffSet);
            StageInitializer.stage.setOpacity(0.8f);
        });
        stageparent.setOnDragDone(dragEvent -> {
            StageInitializer.stage.setOpacity(1.0f);

        });
        stageparent.setOnMouseReleased(mouseEvent -> {
            StageInitializer.stage.setOpacity(1.0f);
        });
    }

    private void changedConstellationName(MouseEvent event){
        HBox source = (HBox) event.getSource();
        VBox parent1 = (VBox) source.getParent();
        HBox firstFrame = (HBox) parent1.getParent();
        HBox cons = (HBox) firstFrame.getChildren().get(0);
        MFXTextField mfxTextField = (MFXTextField) cons.getChildren().get(0);
        mfxTextField.setAllowEdit(true);
        mfxTextField.requestFocus();
        mfxTextField.setOnKeyPressed(event1 -> {
            if (event1.getCode() == KeyCode.ENTER){
                mfxTextField.setAllowEdit(false);
            }
        });
    }

    // a simpler way to distinguish between loop and recursion is that
    // loop tends to have a simpler way of doing for a simple job that need looping
    // while the recursion is for a complex one like we need the data again on the next run

    private void checkContainer(Region container) {
        if (container instanceof VBox) {
            for (Node child : ((VBox) container).getChildren()) {
                if (child instanceof Rectangle){
                   continue;
                }else {
                    checkContainer((Region) child);
                }
            }
        } else if (container instanceof HBox) {
            for (Node child : ((HBox) container).getChildren()) {
                if (child instanceof Rectangle){
                    continue;
                }else {
                    checkContainer((Region) child);
                }
            }
        } else if (container instanceof AnchorPane) {
            for (Node child : ((AnchorPane) container).getChildren()) {
                if (child instanceof Rectangle){
                    continue;
                }else {
                    checkContainer((Region) child);
                }
            }
        }else {
            if (container instanceof MFXTextField) {
                System.out.println("Here (MFXTextField)");
                ((MFXTextField) container).setText("");
            } else if (container instanceof JFXTextArea) {
                System.out.println("Here (JFXTextArea)");
                ((JFXTextArea) container).setText("");
            }
        }
        // Additional logic based on the actual type of the container
    }


    private void removeConstellation(MouseEvent event){
        index--;
        HBox source = (HBox) event.getSource();
        source.setOnMouseClicked(this::doNothing);
        VBox parent1 = (VBox) source.getParent();
        HBox firstFrame = (HBox) parent1.getParent();
        deleteAnimate(firstFrame);
        centerVbox.getChildren().clear();
        asterism.removeConstellation(selectedIndex);
        docks.remove(selectedIndex);
        VBox vBox = used_screen.get(selectedIndex).getItem1();
        for (Node child : vBox.getChildren()) {
            checkContainer((Region) child);
        }
        DataPair<VBox, ConstellationScreenController> pair = new DataPair<>(vBox, used_screen.get(selectedIndex).getItem2());
        unused_screen.add(pair);
        used_screen.remove(selectedIndex);
    }

    @FXML
    private void minimized_handle(MouseEvent event){
        StageInitializer.stage.setIconified(true);
    }

    @FXML
    private void fullscreen_handle(MouseEvent event){
        if (!StageInitializer.stage.isFullScreen()) {
            StageInitializer.stage.setFullScreen(true);
        }else {
            StageInitializer.stage.setFullScreen(false);
        }
    }

    @FXML
    private void close_handle(MouseEvent event){
        System.exit(0);
    }

}
