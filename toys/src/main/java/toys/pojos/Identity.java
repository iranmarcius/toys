package toys.pojos;

public class Identity {
    private Object entity;

    public Identity(Object entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Identity && this.entity == ((Identity) other).entity;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(entity);
    }

}
