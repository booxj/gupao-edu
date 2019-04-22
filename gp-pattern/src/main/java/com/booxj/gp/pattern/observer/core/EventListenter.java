package com.booxj.gp.pattern.observer.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件的注册和监听
 */
public class EventListenter {

    // 保存所有注册的事件
    protected static Map<Enum, Event> events = new HashMap<>();

    public void addLisenter(Enum eventType, Object target, Method callback) {
        events.put(eventType, new Event(target, callback));
    }

    private void trigger(Event e) {
        e.setSource(this);
        e.setTime(System.currentTimeMillis());
        try {
            e.getCallback().invoke(e.getTarget(), e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void trigger(Enum call) {
        if (!this.events.containsKey(call)) {
            return;
        }
        Event event = events.get(call);
        event.setTrigger(call.toString());
        trigger(event);
    }
}
