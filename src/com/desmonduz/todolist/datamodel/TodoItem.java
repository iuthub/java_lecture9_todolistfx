package com.desmonduz.todolist.datamodel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoItem {
    private String shortDescription;
    private String details;
    private LocalDate deadline;

    public TodoItem(String shortDescription, String details, LocalDate deadline) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.deadline = deadline;
    }

    public void load(TodoItem item) {
        setDetails(item.getDetails());
        setDeadline(item.getDeadline());
        setShortDescription(item.getShortDescription());
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return this.getShortDescription();
    }
}
