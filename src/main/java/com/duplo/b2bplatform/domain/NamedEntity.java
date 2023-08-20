package com.duplo.b2bplatform.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    protected String name;
    protected String description;
    protected boolean status = false;

    public NamedEntity(String name){
        this.name = name;
    }

    public NamedEntity(String name, String description) {
        this(name);
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
