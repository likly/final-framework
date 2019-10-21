package org.finalframework.data.query;

import org.finalframework.data.annotation.enums.PersistentType;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-21 10:27:10
 * @since 1.0
 */
public class QPropertyImpl<T, E extends QEntity> implements QProperty<T> {

    private final E entity;
    private final Class<T> type;
    private final String path;
    private final String name;
    private final String column;

    private final boolean idProperty;
    private final PersistentType persistentType;
    private final boolean insertable;
    private final boolean updatable;
    private final boolean selectable;

    public QPropertyImpl(BuilderImpl<T, E> builder) {
        this.entity = builder.entity;
        this.type = builder.type;
        this.path = builder.path;
        this.name = builder.name;
        this.column = builder.column;
        this.idProperty = builder.idProperty;
        this.persistentType = builder.persistentType;

        this.insertable = builder.insertable;
        this.updatable = builder.updatable;
        this.selectable = builder.selectable;
    }

    @Override
    public E getEntity() {
        return this.entity;
    }

    @Override
    public Class<T> getType() {
        return this.type;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getTable() {
        return this.entity.getTable();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getColumn() {
        return this.column;
    }

    @Override
    public boolean isIdProperty() {
        return idProperty;
    }

    @Override
    public PersistentType getPersistentType() {
        return this.persistentType;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public boolean unique() {
        return false;
    }

    @Override
    public boolean nonnull() {
        return false;
    }

    @Override
    public boolean isInsertable() {
        return insertable;
    }

    @Override
    public boolean isUpdatable() {
        return updatable;
    }

    @Override
    public boolean isSelectable() {
        return selectable;
    }

    public static class BuilderImpl<T, E extends QEntity> implements Builder<T> {
        private final E entity;
        private final Class<T> type;
        private String path;
        private String name;
        private String column;

        private boolean idProperty = false;
        private PersistentType persistentType;

        private boolean insertable = true;
        private boolean updatable = true;
        private boolean selectable = true;

        public BuilderImpl(E entity, Class<T> type) {
            this.entity = entity;
            this.type = type;
        }

        @Override
        public Builder<T> path(String path) {
            this.path = path;
            return this;
        }

        @Override
        public Builder<T> name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Builder<T> column(String column) {
            this.column = column;
            return this;
        }

        @Override
        public Builder<T> idProperty(boolean idProperty) {
            this.idProperty = idProperty;
            return this;
        }

        @Override
        public Builder<T> persistentType(PersistentType persistentType) {
            this.persistentType = persistentType;
            return this;
        }

        @Override
        public Builder<T> insertable(boolean insertable) {
            this.insertable = insertable;
            return this;
        }

        @Override
        public Builder<T> updatable(boolean updatable) {
            this.updatable = updatable;
            return this;
        }

        @Override
        public Builder<T> selectable(boolean selectable) {
            this.selectable = selectable;
            return this;
        }


        @Override
        public QProperty<T> build() {
            return new QPropertyImpl<>(this);
        }
    }
}