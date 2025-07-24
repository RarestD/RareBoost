package rare.creations.RareBoost;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RareBoostApplication extends Application  {

    @Getter
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init()  {
        applicationContext = new SpringApplicationBuilder(RareBoost.class).run();
         //applicationContext.getBean(Database.class).getProduct();
    }

    @Override
    public void start(Stage stage)  {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        System.out.println("start");
    }

    @Override
    public void stop()  {
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent{
        public StageReadyEvent(Stage stage){
            super(stage);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }
}
