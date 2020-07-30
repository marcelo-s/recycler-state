package com.example.recyclerstate.viewmodel.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = Item.TABLE_NAME)
public class Item {
    public static final String TABLE_NAME = "item";

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String description;
}
