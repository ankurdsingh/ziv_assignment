package com.ankur.zivame_assignment.model;

/**
 * Created by ankur on 8/5/18.
 */

public class FeatureValuesPOJO {

    private String name;
    private String description;
    private boolean isDivider;
    private boolean isDescription;
    private boolean isSelect;

    public int getDescriptionFor() {
        return descriptionFor;
    }

    public void setDescriptionFor(int descriptionPos) {
        this.descriptionFor = descriptionPos;
    }

    private int descriptionFor;

    public int getDescriptionPosition() {
        return descriptionPosition;
    }

    public void setDescriptionPosition(int descriptionPosition) {
        this.descriptionPosition = descriptionPosition;
    }

    private int descriptionPosition;

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

    public boolean isDivider() {
        return isDivider;
    }

    public void setDivider(boolean divider) {
        isDivider = divider;
    }

    public boolean isDescription() {
        return isDescription;
    }

    public void setDescription(boolean description) {
        isDescription = description;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "FeatureValuesPOJO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
