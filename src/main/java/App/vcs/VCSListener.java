package App.vcs;

import java.util.List;

/**
 * Created by ironman on 13.08.2017.
 */
public interface VCSListener {
    List<String>[] getLastCommits();
}
