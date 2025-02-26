package com.tumult.mclu.client.gui.frame.core.geometry;

public interface IParentable {
    IContainer getParent();
    void setParent(IContainer parent);

    default float getAbsoluteLeft(){
        if (getParent() != null && this != getParent()) {
            return getParent().getAbsoluteLeft() + ((IPosition)this).left();
        }
        return ((IPosition)this).left();
    }
    default float getAbsoluteTop(){
        if (getParent() != null && this != getParent()) {
            return getParent().getAbsoluteTop() + ((IPosition)this).top();
        }
        return ((IPosition)this).top();
    }
}
