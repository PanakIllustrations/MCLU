package com.tumult.mclu.client.gui.frame.core.geometry;

public interface IDirtyable {
    boolean isDirty();
    void markDirty();
    void clearDirty();
}
