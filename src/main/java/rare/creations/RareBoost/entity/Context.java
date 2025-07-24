package rare.creations.RareBoost.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

public class Context {


    ConfigurableApplicationContext applicationContext;

    public Context(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
