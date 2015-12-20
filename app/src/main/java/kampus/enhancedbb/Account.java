package kampus.enhancedbb;

import java.util.List;

/**
 * Created by Павел on 20.12.2015.
 */
public class Account {
    public long id;
    public String name;
    public List<Profile> Profiles;
    public List<Subdivision> Subdivs;
    public boolean isModerator;
}
