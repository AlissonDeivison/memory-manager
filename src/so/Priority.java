package so;

public enum Priority {

    BAIXA(3), MEDIA(2), ALTA(1), CRITICA(0);

    private int priorityNumber;

    Priority(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    };
}
