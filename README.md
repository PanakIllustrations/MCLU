# MCLU - Minecraft Mod with Custom UI Framework

A Minecraft mod featuring a custom-built UI framework and real-time HUD system, demonstrating event-driven architecture, state machine design, and hierarchical component systems.

**Platform:** Forge 1.20.1  
**Language:** Java

## Technical Highlights

### State Machine Architecture
Implemented finite state machines for multiple systems

- **Interactive Event Handler** ([EventHandler.java](src/main/java/com/tumult/mclu/client/gui/frame/core/EventHandler.java), [DraggableRect.java](src/main/java/com/tumult/mclu/client/gui/frame/core/DraggableRect.java`): 6-state FSM managing UI interaction states (IDLE, HOVERED, PRESSED, HOLDING, PRESSED_OUTSIDE, HOLDING_OUTSIDE) with transition logic and event propagation

https://github.com/user-attachments/assets/0749ae26-a5d2-4269-b79b-349d171bcb13
  
- **Animation State Controller** ([CustomAttributeHudOverlay.java](src/main/java/com/tumult/mclu/client/gui/screens/CustomAttributeHudOverlay.java)): 5-state FSM orchestrating UI animations (IDLE, FLASH_DELAY, ANTICIPATION, INTERPOLATION, FOLLOW_THROUGH) with tick-based timing and interpolation

https://github.com/user-attachments/assets/5e808242-b3ce-4644-ba76-63205cd16e69

- **Optomized data structures and algorithms for real-time simulation** ([Vector4DRect.java](src/main/java/com/tumult/mclu/client/gui/frame/geometry/Vector4DRect.java), [DrawableRect.Java](src/main/java/com/tumult/mclu/client/gui/frame/geometry/DrawableRect.java): limited object instantiation, bached rendering, and geometric aproximations based on standard incremental rotation methods used in computer graphics (inspired by [this algorithm](https://web.archive.org/web/20210113024333/http://www.java2s.com/example/java/javax.media.opengl/draw-sphere-with-opengl.html) from javax.media.opengl)

### Hierarchical Communication System
Designed a tree-based component architecture with parent-child relationships and message passing:

- **Node Tree Structure** [Node.java](src/main/java/com/tumult/mclu/client/gui/frame/core/Node.java): Recursive rendering and event propagation through component hierarchy
- **Input Capture Protocol**: Child components can block parent input handling, similar to interrupt priority systems
- **Event Bubbling**: Mouse events propagate through the component tree with boundary checking and state coordination

**Priority-Based Input Arbitration:**
Components use a capture flag to implement mutual exclusion for concurrent input events:

| Interaction          | CloseBtn Capturing | ResizeHandle Capturing | Parent Dragging |
|---------------------|-------------------|--------------------------|-----------------|
| Click close button  | Y                 | N                        |    BLOCKED      |
| Drag resize handle  | N                 | Y                        |    BLOCKED      |
| Drag empty area     | N                 | N                        |    ACTIVE       |

### Real-Time System Design
Implemented tick-based update loops with frame-perfect rendering:

- **UIManager** [UIManager.java](src/main/java/com/tumult/mclu/client/gui/frame/core/UIManager.java): Centralized input polling and screen coordinate management
- **Synchronized State Updates**: Components update state based on mouse position and button states each frame
- **Drag System**: Offset-based dragging with coordinate transformation and boundary clamping

### Data Structures & Software Design

**Custom Geometry Library:**
- [Vector2DPoint.java](src/main/java/com/tumult/mclu/client/gui/frame/geometry/Vector2DPoint.java): 2D point operations (arithmetic, geographic calculations)
- [Vector4DRect.java](src/main/java/com/tumult/mclu/client/gui/frame/geometry/Vector4DRect.java): Rectangle representation with drag tracking and bounds testing

**Patterns Used:**
- Singleton pattern for icon management  [IconUtils](src/main/java/com/tumult/mclu/client/gui/icons/IconUtils.java)
- Template method pattern for drawable components
- Observer pattern in event handling
- Strategy pattern for interactive behaviors

## Project Structure

```
src/main/java/com/tumult/mclu/
├── client/gui/
│   ├── frame/
│   │   ├── core/          # Event handling, state machines, UI management
│   │   └── geometry/      # Custom data structures and rendering primitives
│   ├── icons/             # Resource management and sprite registration
│   └── screens/           # HUD overlays and gameplay UI
└── CustomAttributes.java  # Entity attribute system using Forge events
```

## Links

[Trello Board](https://trello.com/b/yW5Mh0zp/mclu) - Project tracking and feature planning
