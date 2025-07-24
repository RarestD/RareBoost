package rare.creations.RareBoost.entity;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

public class ConstellationTab {

    public static HBox createConstellationTab(String name){
        var prefH = 121;
        var prefW = 350;
        HBox pane = new HBox();
        pane.setMinWidth(prefW);
        pane.setMinHeight(prefH);
        pane.setPrefHeight(prefH);
        pane.setPrefWidth(prefW);
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setId("constellation_holder");
        MFXTextField consName = new MFXTextField(name);
        consName.setMouseTransparent(true);
        consName.setFloatMode(FloatMode.DISABLED);
        consName.setAllowEdit(false);
        consName.setId("cons_text");
        consName.setAlignment(Pos.CENTER);
        consName.setMinHeight(120);
        consName.setMinWidth(240);
        consName.setPrefWidth(240);
        consName.setPrefHeight(120);
        consName.setMaxWidth(240);
        consName.setMaxHeight(120);
        HBox hBox = new HBox();
        hBox.setMinHeight(121);
        hBox.setMinWidth(243);
        hBox.setPrefWidth(243);
        hBox.setPrefHeight(121);
        hBox.setMaxWidth(243);
        hBox.setMaxHeight(121);
        hBox.setId("constellationtab");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(consName);
        pane.getChildren().add(hBox);
        return pane;
    }

}
