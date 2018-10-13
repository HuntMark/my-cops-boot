package com.example.orm.jpa;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * Abstract superclass for entities. We are assuming that early primary key
 * generation will be used.
 *
 * @param <T> the type of {@link EntityId} that will be used for this entity
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends EntityId> implements Entity<T> {

    @EmbeddedId
    private T id;

    @ArtifactForFramework
    protected AbstractEntity() {
    }

    public AbstractEntity(T id) {
        this.id = Preconditions.checkNotNull(id);
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;

        if (this == o) {
            return true;
        } else if (o instanceof AbstractEntity) {
            AbstractEntity other = (AbstractEntity) o;
            result = Objects.equals(id, other.id);
        }

        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
