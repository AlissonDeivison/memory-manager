package so;

public enum Priority {

    BAIXA(0), MEDIA(1), ALTA(2), CRITICA(3);

    private int priorityNumber;

    Priority(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    };
}
