package kampus.enhancedbb;

import android.app.Application;

/**
 * Created by Павел on 20.12.2015.
 */
public class Auth extends Application{
    public String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public Auth(String login) {
        this.login = login;
    }
}
