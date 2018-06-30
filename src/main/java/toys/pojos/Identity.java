package toys.pojos;

public class Identity {
    private Object entity;

    public Identity(Object entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object other) {
        return Identity.class.isInstance(other) && this.entity == ((Identity) other).entity;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(entity);
    }

}
