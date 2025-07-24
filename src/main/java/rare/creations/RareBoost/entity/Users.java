package rare.creations.RareBoost.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "users")
public class Users {

    private String username;
    private String password;
    private String auth;
    private Date expirationDate;
    private boolean valid;
    private String status;

}
