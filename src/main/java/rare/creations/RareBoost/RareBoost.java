package rare.creations.RareBoost;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import rare.creations.RareBoost.controllers.LoginScreenController;
import rare.creations.RareBoost.service.Asterism;
import rare.creations.RareBoost.service.RequestServices;
import rare.creations.RareBoost.util.WebClientConfig;
import rare.creations.RareBoost.view.MainView;

@SpringBootApplication(
		scanBasePackageClasses = {
			WebClientConfig.class,
			RequestServices.class,
			Asterism.class,
			MainView.class,
			RareBoostApplication.class,
			StageInitializer.class,
			LoginScreenController.class},
		scanBasePackages = {
				"rare.creations.RareBoost.controllers"
		},
		exclude = {MongoAutoConfiguration.class})
public class RareBoost {

	private ConfigurableApplicationContext applicationContext;


	public static void main(String[] args) {
		Application.launch(RareBoostApplication.class, args);
	}


}
