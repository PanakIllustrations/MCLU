package com.tumult.mclu.client.gui.frame.core;

public interface IRenderable {
//    default Pair<ShaderInstance, BufferBuilder> preDraw( ResourceLocation texture, Color color, VertexFormat.Mode mode, VertexFormat format) {
//        ShaderInstance oldShader = RenderSystem.getShader();
//        BufferBuilder builder = Tesselator.getInstance().getBuilder();
//        builder.begin(mode, format);
//
//        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
//        RenderSystem.setShaderTexture(0, texture);
//
//        RenderSystem.setShaderColor(
//            color.getRed() / 255f,
//            color.getGreen() / 255f,
//            color.getBlue() / 255f,
//            color.getAlpha() / 255f
//        );
//
//        RenderSystem.enableBlend();
//        RenderSystem.enableDepthTest();
//
//        return new Pair<>(oldShader, builder);
//    }
//
//    default void postDraw(Pair<ShaderInstance, BufferBuilder> renderData) {
//        BufferUploader.drawWithShader(renderData.getSecond().end());
//        RenderSystem.disableBlend();
//        RenderSystem.enableDepthTest();
//        RenderSystem.setShader(renderData::getFirst);
//    }
}
