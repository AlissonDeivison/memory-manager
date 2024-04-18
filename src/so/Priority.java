package so;

public enum Priority {

    CRITICA(0),
    ALTA(1), 
    MEDIA(2), 
    BAIXA(3);

    private int priorityNumber;

    Priority(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    };
}
