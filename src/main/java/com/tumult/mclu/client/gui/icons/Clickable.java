package com.tumult.mclu.client.gui.icons;

public class Clickable {
    /*
    public final ClickableIcon backpack;
    public final ClickableIcon map;
    public final ClickableIcon passport;

    public Clickable() {
        this.backpack = registerClickableIcon(IconUtils.INSTANCE.getIcon().backpack);
        this.map = registerClickableIcon(IconUtils.INSTANCE.getIcon().map);
        this.passport = registerClickableIcon(IconUtils.INSTANCE.getIcon().passport);
    }

    private ClickableIcon registerClickableIcon(GuiIcon.DrawableIcon icon) {
        return new ClickableIcon(icon);
    }


    public static class ClickableIcon extends Rectangle {
        private int screenX = 0;
        private int screenY = 0;
        private double mouseX;
        private double mouseY;
        private boolean isActive = true;
        private boolean isVisible = true;

        public ClickableIcon(GuiIcon.DrawableIcon icon) {
            setRectangle(screenX, screenY, icon.getIconWidth(), icon.getIconHeight());
        }

        public boolean isActive() {
            return isActive;
        }
        public boolean isVisible() {
            return isVisible;
        }

        public void setRectanglePosition(double screenX, double screenY) {
            setRectanglePosition(new Vector2DPoint(screenX, screenY));
        }
        public void setRectanglePosition(Vector2DPoint point) {
            this.screenX = (int) point.x;
            this.screenY = (int) point.y;
        }

        public void setActive(boolean isActive) {
            this.isActive = isActive;
        }
        public void setVisible(boolean isVisible) {
            this.isVisible = isVisible;
        }

        public void updateMouse(double mouseX, double mouseY) {
            updateMouse(new Vector2DPoint(mouseX, mouseY));
        }

        private void updateMouse(Vector2DPoint mousePos) {
            this.mouseX = mousePos.x;
            this.mouseY = mousePos.y;
        }

        public boolean containsPoint(double x, double y) {
            return x > getLeft() && x < getRight() && y > getTop() && y < getBottom();
        }
        private boolean containsPoint() {
            return mouseX > getLeft() && mouseX < getRight() && mouseY > getTop() && mouseY < getBottom();
        }

        public void mouseDownEvent(double x, double y, int button) {
            this.clickSound(Minecraft.getInstance().getSoundManager());
            updateMouse(new Vector2DPoint(x, y));
            handleMouseEvent(button, this::onPressed);
        }
        public void mouseUpEvent(double x, double y, int button) {
            updateMouse(new Vector2DPoint(x, y));
            handleMouseEvent(button, this::onReleased);
        }

        protected void handleMouseEvent(int button, Runnable onPressOrRelease) {
            if (!isActive() || !isVisible() || !containsPoint())
                return;
            InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
            boolean flag = Minecraft.getInstance().options.keyPickItem.isActiveAndMatches(mouseKey);
            System.out.println("Mouse key matched keyPickItem: " + flag);
            if (button == 0 || button == 1 || flag) {
                onPressOrRelease.run();
            }
        }

        protected void clickSound(SoundManager soundHandler) {
            soundHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }

        protected void onPressed() {
            // Handle the mouse press event
            System.out.println("Mouse button pressed!");
        }

        protected void onReleased() {
            // Handle the mouse release event
            System.out.println("Mouse button released!");
        }
    }*/
}
