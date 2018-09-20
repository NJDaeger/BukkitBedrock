package com.njdaeger.bedrock.user;

import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.Location;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class LocationHistory {

    private int index;
    private final int max;
    private final IUser user;
    private final Location[] locations;
    private int size = 0;
    ///private final List<Location> locations;

    public LocationHistory(IUser user) {
        this.max = user.getPlugin().getSettings().getHistoryRecordLimit();
        this.locations = new Location[max];
        this.user = user;
    }

    public Location getTop() {
        return locations[max-1];
    }

    public Location getPrevious() {
        if (index >= max || index == 0) return null;
        return locations.get(index--);
    }

    public Location getNext() {
        if (index <= 0) return null;
        return list.get(index++);
    }

    public void push(Location location) {
        if (size >= max)
        this.index = locations.size()-1;
        locations.
    }

}
