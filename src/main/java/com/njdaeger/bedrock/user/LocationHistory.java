package com.njdaeger.bedrock.user;

import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.Location;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public final class LocationHistory {

    private int index;
    private final int max;
    private final IUser user;
    private final LinkedList<Location> list;

    public LocationHistory(IUser user) {
        this.user = user;
        this.list = new LinkedList<>();
        this.max = user.getPlugin().getSettings().getHistoryRecordLimit();
    }

    public Location getFirst() {
        return list.getFirst();
    }

    public Location getPrevious() {
        return list.get(index--);
    }

    public Location getNext() {
        return list.get(index++);
    }

    public void push(Location location) {

    }

}
