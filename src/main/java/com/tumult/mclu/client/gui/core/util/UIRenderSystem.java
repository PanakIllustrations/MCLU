package com.tumult.mclu.client.gui.core.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.core.geometry.Rect;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.client.event.ScreenEvent;
import org.joml.Matrix4f;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UIRenderSystem {
    private static final Map<String, ShaderInstance> SHADERS = new HashMap<>();

    public static final String ROUNDED_RECT = "rounded_rect";


    public static void initShaders(ResourceProvider resourceProvider) throws IOException {
        SHADERS.put(ROUNDED_RECT, new ShaderInstance(
                resourceProvider,
                new ResourceLocation(McluConstants.MOD_ID, "rounded_rect"),
                DefaultVertexFormat.POSITION_TEX
        ));
    }

    public static void disposeShaders() {
        for (ShaderInstance shader : SHADERS.values()) {
            shader.close();
        }
        SHADERS.clear();
    }

    public static void drawRoundRect(Rect rect, float[] color, float cornerRadius, float zOffset) {
        ShaderInstance shader = SHADERS.get(ROUNDED_RECT);
        if (shader == null) return;

        RenderSystem.setShader(() -> shader);
        shader.safeGetUniform("BackgroundColor").set(color[0], color[1], color[2], color[3]);
        shader.safeGetUniform("CornerRadius").set(cornerRadius);
        shader.safeGetUniform("RectSize").set(rect.width(), rect.height());

        Matrix4f matrix4f = RenderSystem.getModelViewMatrix();
        shader.safeGetUniform("MadelViewMatrix").set(matrix4f);

        drawRect(rect, zOffset);
    }

    private static void drawRect(Rect rect, float zLevel) {
        float x = rect.getAbsoluteLeft();
        float y = rect.getAbsoluteTop();
        float w = rect.width();
        float h = rect.height();

        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        builder.vertex(x, y + h, zLevel).uv(0, 1).endVertex();
        builder.vertex(x + w, y + h, zLevel).uv(1, 1).endVertex();
        builder.vertex(x + w, y, zLevel).uv(1, 0).endVertex();
        builder.vertex(x, y, zLevel).uv(0, 0).endVertex();

        BufferUploader.drawWithShader(builder.end());
    }
}
