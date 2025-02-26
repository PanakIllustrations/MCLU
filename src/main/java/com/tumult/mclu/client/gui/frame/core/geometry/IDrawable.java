package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public interface IDrawable {
    int resolution();
    VertexFormat.Mode mode();
    float depth();
    void setDepth(float depth);
    void copyTo(float[] out);

    default void normalize(float divisor, float[] out){
        for (int i = 0; i < out.length; i++) {
            out[i] /= divisor;
        }
    }
}
