public class Event extends Task{
    private String eventPeriod;

    public Event(String description, String eventPeriod) throws EmptyArgument {
        super(description);
        eventPeriod = eventPeriod.trim();
        if (eventPeriod.isEmpty()){
            throw new EmptyArgument();
        }
        this.eventPeriod = eventPeriod;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (Event Time: " + eventPeriod + ")";
    }
}
