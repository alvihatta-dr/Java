package com.alvi.todolistapp.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty description;
    private final BooleanProperty completed;
    private final BooleanProperty important;
    private final StringProperty listName;

    public Task(String description, String listName) {
        this.description = new SimpleStringProperty(description);
        this.completed = new SimpleBooleanProperty(false);
        this.important = new SimpleBooleanProperty(false);
        this.listName = new SimpleStringProperty(listName);
    }

    // --- Getter dan Setter untuk description ---
    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    // --- Getter dan Setter untuk completed ---
    public boolean isCompleted() {
        return completed.get();
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    // --- Getter dan Setter untuk important ---
    public boolean isImportant() {
        return important.get();
    }

    public BooleanProperty importantProperty() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important.set(important);
    }

    // --- Getter dan Setter untuk listName ---
    public String getListName() {
        return listName.get();
    }

    public StringProperty listNameProperty() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName.set(listName);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
