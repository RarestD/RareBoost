package rare.creations.RareBoost;


import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<RareBoostApplication.StageReadyEvent> {

    @Value("classpath:/fxml/RareBoost.fxml")
    private Resource bssaResource;
    private final String applicationTittle;
    private  ConfigurableApplicationContext applicationContext;

    public static Stage stage = null;


    public StageInitializer(@Value("${spring.application.name}") String applicationTittle,
                        ConfigurableApplicationContext applicationContext) {
    this.applicationTittle = applicationTittle;
    this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(RareBoostApplication.StageReadyEvent event) {
        try {
            System.out.println(Screen.getPrimary().getBounds());
            FXMLLoader fxmlLoader = new FXMLLoader(bssaResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            //stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("image/Klee.jpg")));
            Scene scene = new Scene(parent);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            StageInitializer.stage = stage;
            stage.setScene(scene);
            stage.setFullScreenExitHint("");
            stage.setTitle(applicationTittle);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
