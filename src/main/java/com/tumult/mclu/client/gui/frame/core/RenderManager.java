package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tumult.mclu.client.gui.frame.core.util.RenderingConfig;
import com.tumult.mclu.client.gui.frame.core.util.UIProfiler;
import net.minecraft.client.renderer.ShaderInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RenderManager {
    private static final Map<Supplier<ShaderInstance>, List<AbstractGuiElement<?>>> BATCHED_ELEMENTS = new HashMap<>();

    public static void beginFrame() {
        if (RenderingConfig.ENABLE_PROFILING) {
            UIProfiler.start("frame_total");
        }
        BATCHED_ELEMENTS.clear();
    }

    public static void registerForBatch(AbstractGuiElement<?> element, Supplier<ShaderInstance> shader) {
        if (RenderingConfig.ENABLE_BATCHING) {
            BATCHED_ELEMENTS.computeIfAbsent(shader, s -> new ArrayList<>()).add(element);
        } else {
            // Immediate mode rendering if batching is disabled
            element.render();
        }
    }

    public static void renderBatches() {
        if (!RenderingConfig.ENABLE_BATCHING) return;

        if (RenderingConfig.ENABLE_PROFILING) {
            UIProfiler.start("render_batches");
        }

        // Render by shader type
        for (Map.Entry<Supplier<ShaderInstance>, List<AbstractGuiElement<?>>> entry : BATCHED_ELEMENTS.entrySet()) {
            Supplier<ShaderInstance> shader = entry.getKey();
            List<AbstractGuiElement<?>> elements = entry.getValue();

            if (elements.isEmpty()) continue;

            // Group by vertex format
            Map<VertexFormat, List<AbstractGuiElement<?>>> formatGroups = new HashMap<>();
            for (AbstractGuiElement<?> element : elements) {
                formatGroups.computeIfAbsent(element.format, f -> new ArrayList<>()).add(element);
            }

            // Set shader
            RenderSystem.setShader(shader);

            // Render each format group
            for (Map.Entry<VertexFormat, List<AbstractGuiElement<?>>> formatEntry : formatGroups.entrySet()) {
                VertexFormat format = formatEntry.getKey();
                List<AbstractGuiElement<?>> formatElements = formatEntry.getValue();

                // Further group by mode (QUADS, TRIANGLE_FAN, etc.)
                Map<VertexFormat.Mode, List<AbstractGuiElement<?>>> modeGroups = new HashMap<>();
                for (AbstractGuiElement<?> element : formatElements) {
                    modeGroups.computeIfAbsent(element.rect.mode(), m -> new ArrayList<>()).add(element);
                }

                // Render each mode group
                for (Map.Entry<VertexFormat.Mode, List<AbstractGuiElement<?>>> modeEntry : modeGroups.entrySet()) {
                    VertexFormat.Mode mode = modeEntry.getKey();
                    List<AbstractGuiElement<?>> modeElements = modeEntry.getValue();

                    if (mode == VertexFormat.Mode.QUADS) {
                        // QUADS can be batched into a single draw call
                        BufferBuilder builder = Tesselator.getInstance().getBuilder();
                        builder.begin(mode, format);

                        for (AbstractGuiElement<?> element : modeElements) {
                            if (element.isVisible()) {
                                element.addToBuffer(builder);
                            }
                        }

                        com.mojang.blaze3d.vertex.BufferUploader.drawWithShader(builder.end());
                    } else {
                        // Other modes need separate draw calls
                        for (AbstractGuiElement<?> element : modeElements) {
                            if (element.isVisible()) {
                                element.render();
                            }
                        }
                    }
                }
            }
        }

        if (RenderingConfig.ENABLE_PROFILING) {
            UIProfiler.end("render_batches");
        }
    }

    public static void endFrame() {
        if (RenderingConfig.ENABLE_PROFILING) {
            UIProfiler.end("frame_total");
        }
    }
}