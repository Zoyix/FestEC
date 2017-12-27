package com.dianbin.latte.delegates.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/27.
 */

public class EventManage {

    private static final HashMap<String, Event> EVENTS = new HashMap<>();

    private EventManage() {
        addEvent("test",new TestEvent());
    }

    private static class Holder {
        private static final EventManage INSTANCE = new EventManage();
    }

    public static EventManage getInstance() {
        return Holder.INSTANCE;
    }

    public EventManage addEvent(@NonNull String name, @NonNull Event event) {
        EVENTS.put(name, event);
        return this;
    }

    public Event createEvent(@NonNull String action) {
        final Event event = EVENTS.get(action);
        if (event == null) {
            return new UndefineEvent();
        }
        return event;
    }

}
