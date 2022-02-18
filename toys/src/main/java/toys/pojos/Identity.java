package toys.pojos;

public class Identity {
    private final Object entity;

    public Identity(Object entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Identity id) && this.entity == id.entity;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(entity);
    }

}
