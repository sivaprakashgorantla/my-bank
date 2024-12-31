package com.sivaprakash.loan.kafka;

public class Event {
    private String eventType;
    private Object data;

    // Default constructor for deserialization
    public Event() {
    }

    public Event(String eventType, Object data) {
        this.eventType = eventType;
        this.data = data;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType='" + eventType + '\'' +
                ", data=" + data +
                '}';
    }
}
