# MCLU - Minecraft Mod with Custom UI Framework

A Minecraft mod featuring a custom-built UI framework and real-time HUD system, demonstrating event-driven architecture, state machine design, and hierarchical component systems.

**Platform:** Forge 1.20.1  
**Language:** Java

## Technical Highlights

### State Machine Architecture
Implemented finite state machines for multiple systems, demonstrating patterns applicable to autonomous systems and robotics:

- **Interactive Event Handler** (`EventHandler.java`, `DraggableRect.java`): 6-state FSM managing UI interaction states (IDLE, HOVERED, PRESSED, HOLDING, PRESSED_OUTSIDE, HOLDING_OUTSIDE) with transition logic and event propagation

https://github.com/user-attachments/assets/0749ae26-a5d2-4269-b79b-349d171bcb13
  
- **Animation State Controller** (`CustomAttributeHudOverlay.java`): 5-state FSM orchestrating UI animations (IDLE, FLASH_DELAY, ANTICIPATION, INTERPOLATION, FOLLOW_THROUGH) with tick-based timing and interpolation

https://github.com/user-attachments/assets/5e808242-b3ce-4644-ba76-63205cd16e69

### Hierarchical Communication System
Designed a tree-based component architecture with parent-child relationships and message passing:

- **Node Tree Structure** (`Node.java`): Recursive rendering and event propagation through component hierarchy
- **Input Capture Protocol**: Child components can block parent input handling, similar to interrupt priority systems
- **Event Bubbling**: Mouse events propagate through the component tree with boundary checking and state coordination

**Priority-Based Input Arbitration:**
Components use a capture flag to implement mutual exclusion for concurrent input events:

| Interaction          | CloseBtn Capturing | ResizeHandle Capturing | Parent Dragging |
|---------------------|-------------------|--------------------------|-----------------|
| Click close button  | Y                 | N                        |    BLOCKED      |
| Drag resize handle  | N                 | Y                        |    BLOCKED      |
| Drag empty area     | N                 | N                        |    ACTIVE       |

This demonstrates conflict resolution patterns applicable to multi-agent systems where competing processes need coordinated resource access.

### Real-Time System Design
Implemented tick-based update loops with frame-perfect rendering:

- **UIManager** (`UIManager.java`): Centralized input polling and screen coordinate management
- **Synchronized State Updates**: Components update state based on mouse position and button states each frame
- **Drag System**: Offset-based dragging with coordinate transformation and boundary clamping

### Data Structures & Software Design

**Custom Geometry Library:**
- `Vector2DPoint`: 2D point operations (arithmetic, geographic calculations)
- `Vector4DRect`: Rectangle representation with drag tracking and bounds testing
- Efficient containment checks for hit testing

**Design Patterns:**
- Singleton pattern for icon management (`IconUtils`)
- Template method pattern for drawable components
- Observer pattern in event handling
- Strategy pattern for different interactive behaviors

### Software Engineering Practices

- **Modular Architecture**: Separation of concerns (geometry, rendering, interaction, state management)
- **Interface-Driven Design**: `IInteractive` interface enables polymorphic event handling
- **Code Reusability**: Base classes (`DrawableRect`, `DrawableSprite`, `InteractiveRect`) extended for specific behaviors
- **Resource Management**: Centralized texture loading and sprite registration

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

## Key Components

- **Event System**: Multi-state interactive components with transition logic
- **Animation Controller**: FSM-based animation sequencing with timing control
- **Rendering Pipeline**: Custom vertex buffer management with OpenGL integration
- **Attribute System**: Player stat management using Minecraft Forge event bus

## Development Practices

- Object-oriented design with clear inheritance hierarchies
- Event-driven architecture for decoupled components
- Real-time constraint handling (frame-rate independent updates)
- Efficient memory management (buffer reuse, lazy initialization)

## Links

[Trello Board](https://trello.com/b/yW5Mh0zp/mclu) - Project tracking and feature planning
