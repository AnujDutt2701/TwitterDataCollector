package com.anujdutt.twitterdatacollector.entity;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topic")
public class Topic {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer id;

    @ColumnInfo(name="query")
    public String query;

    @ColumnInfo(name = "query_display_name")
    public String queryDisplayName;

    @ColumnInfo(name = "query_location")
    public String queryLocation;

    @ColumnInfo(name = "query_language")
    public String queryLanguage;


    public Topic(String query, String queryDisplayName, String queryLocation, String queryLanguage) {
        this.query = query;
        this.queryDisplayName = queryDisplayName;
        this.queryLocation = queryLocation;
        this.queryLanguage = queryLanguage;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        Topic rhs = (Topic) other;
        return Objects.equals(query, rhs.query)
                && Objects.equals(queryDisplayName, rhs.queryDisplayName)
                && Objects.equals(id, rhs.id)
                && Objects.equals(queryLocation, rhs.queryLocation)
                && Objects.equals(queryLanguage, rhs.queryLanguage);
    }
}
