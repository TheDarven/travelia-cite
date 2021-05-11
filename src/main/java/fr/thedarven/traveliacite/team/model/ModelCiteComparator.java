package fr.thedarven.traveliacite.team.model;

import java.util.Comparator;

public class ModelCiteComparator  implements Comparator<TeamCite> {

    @Override
    public int compare(TeamCite o1, TeamCite o2) {
        if (o1.getAmountOfEmeralds() == o2.getAmountOfEmeralds()) {
            return 0;
        }
        return o1.getAmountOfEmeralds() > o2.getAmountOfEmeralds() ? -1 : 1;
    }

}
