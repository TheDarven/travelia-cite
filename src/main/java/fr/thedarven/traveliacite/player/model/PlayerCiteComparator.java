package fr.thedarven.traveliacite.player.model;

import java.util.Comparator;

public class PlayerCiteComparator implements Comparator<PlayerCite> {

    public int compare(PlayerCite o1, PlayerCite o2) {
        if (o1.getEmeralds() == o2.getEmeralds()) {
            return 0;
        }
        return o1.getEmeralds() > o2.getEmeralds() ? -1 : 1;
    }

}
