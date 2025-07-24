package rare.creations.RareBoost;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import rare.creations.RareBoost.entity.Status;

@SpringBootTest
class RareBoostTests {

	ConfigurableApplicationContext applicationContext;

	public RareBoostTests(ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@BeforeEach

	@Test	void contextLoads() throws InterruptedException {
		// cause im not implementing login after all
	}

	@Test
	void updateStatus(){
		// cause im not implementing login after all
	}


}
