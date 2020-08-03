package com.example.recyclerstate.viewmodel.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Item.TABLE_NAME)
public class Item {
    public static final String TABLE_NAME = "item";

    @PrimaryKey(autoGenerate = true)
    public long id;

    private String name;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public static class Builder {
        private String name;
        private String description;


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Item build() {
            Item item = new Item();
            item.setName(name);
            item.setDescription(description);
            return item;
        }

    }
}
