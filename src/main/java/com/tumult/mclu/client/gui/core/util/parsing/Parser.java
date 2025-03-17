package com.tumult.mclu.client.gui.core.util.parsing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Float.parseFloat;

public class Parser {
//    public static IGuiComponent parse(String htmlContent, String cssContent) {
//        // Parse CSS first to build style information
//        CssParser cssParser = new CssParser();
//        StyleSheet styleSheet = cssParser.parse(cssContent);
//
//        // Then parse HTML with style information
//        HtmlParser htmlParser = new HtmlParser(styleSheet);
//        return htmlParser.parse(htmlContent);
//    }
//
//    public static IGuiComponent parseFromFiles(File htmlFile, File cssFile) throws IOException {
//        String htmlContent = new String(Files.readAllBytes(htmlFile.toPath()));
//        String cssContent = new String(Files.readAllBytes(cssFile.toPath()));
//        return parse(htmlContent, cssContent);
//    }
}

class CssParser {
//    public StyleSheet parse(String cssContent) {
//        StyleSheet styleSheet = new StyleSheet();
//
//        Pattern rulePattern = Pattern.compile("([^{]+)\\{([^}]*)}");
//        Matcher ruleMatcher = rulePattern.matcher(cssContent);
//
//        while (ruleMatcher.find()) {
//            String selector = ruleMatcher.group(1).trim();
//            String styleContent = ruleMatcher.group(2).trim();
//
//            Style style = new Style();
//            String[] declarations = styleContent.split(";");
//
//            for (String declaration : declarations) {
//                if (declaration.trim().isEmpty()) continue;
//
//                String[] parts = declaration.split(":", 2);
//                if (parts.length == 2) {
//                    String property = parts[0].trim();
//                    String value = parts[1].trim();
//                    style.property(property, value);
//                }
//            }
//            styleSheet.addStyle(selector, style);
//        }
//
//        return styleSheet;
//    }
}

// HTML parser implementation
class HtmlParser {
//    private static final Pattern TAG_PATTERN = Pattern.compile("<([^>]+)>(.*?)</\\1>|<([^/>]+)/>|<([^/>]+)>", Pattern.DOTALL);
//    private static final Pattern ATTR_PATTERN = Pattern.compile("(\\w+)=\"([^\"]*)\"");
//
//    private final StyleSheet styleSheet;
//    private final Map<String, IGuiComponent> idToComponent = new HashMap<>();
//
//    public HtmlParser(StyleSheet styleSheet) {
//        this.styleSheet = styleSheet;
//    }
//    public IGuiComponent parse(String htmlContent) {
//        // clean whitespace
//        htmlContent = htmlContent.replaceAll(">\\s+<", "><");
//        // parse into component tree
//        ContainerComponent root = new ContainerComponent("root");
//        parseRecursive(htmlContent, root);
//        return root;
//    }
//
//    private void parseRecursive(String htmlContent, IGuiComponent parent) {
//        Matcher matcher = TAG_PATTERN.matcher(htmlContent);
//        int lastEnd = 0;
//        while (matcher.find()) {
//            // handle text nodes between tags
//            if (matcher.start() > lastEnd) {
//                String textBetween = htmlContent.substring(lastEnd, matcher.start()).trim();
//                if (!textBetween.isEmpty()) {
//                    //addTextNode(parent, textBetween);
//                }
//            }
//            String tagName = null;
//            String tagContent = null;
//            String attributes = null;
//            // open and close tag
//            if (matcher.group(1) != null) {
//                String fullTagName = matcher.group(1);
//                tagName = extractTagName(fullTagName);
//                attributes = fullTagName.substring(tagName.length()).trim();
//                tagContent = matcher.group(2);
//            }
//            // self-closing tag
//            else if (matcher.group(3) != null) {
//                String fullTagName = matcher.group(3);
//                tagName = extractTagName(fullTagName);
//                attributes = fullTagName.substring(tagName.length()).trim();
//            }
//            // open tag without closing
//            else if (matcher.group(4) != null) {
//                String fullTagName = matcher.group(4);
//                tagName = extractTagName(fullTagName);
//                attributes = fullTagName.substring(tagName.length()).trim();
//            }
//            if (tagName != null) {
//                //processTag(parent, tagName.toLowerCase(), attributes, tagContent);
//            }
//            lastEnd = matcher.end();
//        }
//
//        // handle text after last tag
//        if (lastEnd < htmlContent.length()) {
//            String textAfter = htmlContent.substring(lastEnd).trim();
//            if (!textAfter.isEmpty()) {
//                //addTextNode(parent, textAfter);
//            }
//        }
//    }
//
//    private String extractTagName(String fullTagName) {
//        int spaceIndex = fullTagName.indexOf(' ');
//        return spaceIndex > 0 ? fullTagName.substring(0, spaceIndex) : fullTagName;
//    }
//
//    private void processTag(IGuiComponent parent, String tagName, String attributes, String content) {
//        Map<String, String> attributesMap = new HashMap<>();
//        IGuiComponent component = createComponent(tagName, attributesMap, content);
//
//        // add created component to parent
//        if (component != null) {
//            String id = attributesMap.get("id");
//            if (id != null && !id.isEmpty()) {
//                idToComponent.put(id, component);
//            }
//            //applyStyles(component, tagName, attributesMap);
//            if (parent != null) {
//                parent.addChild(component);
//            }
//            if (content != null && !content.isEmpty() && component instanceof ContainerComponent) {
//                parseRecursive(content, component);
//            }
//        }
//    }
//
//    private Map<String, String> parseAttributes(String attributes) {
//        Map<String, String> attributesMap = new HashMap<>();
//        if (attributes != null)
//            return attributesMap;
//
//        Matcher matcher = ATTR_PATTERN.matcher(attributes);
//        while (matcher.find()) {
//            String name = matcher.group(1);
//            String value = matcher.group(2);
//            attributesMap.put(name, value);
//        }
//        return attributesMap;
//    }
//
//    private IGuiComponent createComponent(String tagName, Map<String, String> attributes, String content) {
//        return switch (tagName) {
//            case "div", "section", "article", "header", "footer", "nav", "aside", "main"
//                    -> createContainer(attributes);
//            case "span", "p", "h1", "h2", "h3", "h4", "h5", "h6"
//                    -> createText(content, attributes);
//            case "button"
//                    -> createButton(content, attributes);
//            case "input"
//                    -> createInput(content, attributes);
//            case "img"
//                    -> createImage(content, attributes);
//            case "script", "style", "meta", "link", "head", "html", "body"
//                    -> null;
//            default
//                    -> createContainer(attributes); // Default for unknown tags
//        };
//    }
//
//    private ContainerComponent createContainer(Map<String, String> attributes) {
//        String id = attributes.getOrDefault("id", "");
//        ContainerComponent container = new ContainerComponent(id);
//        if (attributes.containsKey("class")) {
//            String[] classes = attributes.get("class").split("\\s+");
//            container.classes(classes);
//        }
//        if (attributes.containsKey("style")) {
//            applyInlineStyles(container, attributes.get("style"));
//        }
//        return container;
//    }
//
//    private TextComponent createText(String content, Map<String, String> attributes) {
//        TextComponent textComponent = new TextComponent(content);
//        if (attributes.containsKey("style")) {
//            applyInlineStyles(textComponent, attributes.get("style"));
//        }
//        return textComponent;
//    }
//
//    private ButtonComponent createButton(String content, Map<String, String> attributes) {
//        ButtonComponent button = new ButtonComponent(content);
//        if (attributes.containsKey("style")) {
//            applyInlineStyles(button, attributes.get("style"));
//        }
//        return button;
//    }
//
//    private ImageComponent createImage(String content, Map<String, String> attributes) {
//        String id = attributes.getOrDefault("id", "");
//        String src = attributes.getOrDefault("src", "");
//
//        ImageComponent imageComponent = new ImageComponent(src);
//        // Set width and height if provided
//        if (attributes.containsKey("width")) {
//            float width = parseFloat(attributes.get("width"), 0);
//            imageComponent.setWidth(width);
//        }
//
//        if (attributes.containsKey("height")) {
//            float height = parseFloat(attributes.get("height"), 0);
//            imageComponent.setHeight(height);
//        }
//
//        // Apply class if present
//        if (attributes.containsKey("class")) {
//            String[] classes = attributes.get("class").split("\\s+");
//            // Apply classes as needed
//        }
//
//        // Apply inline styles if present
//        if (attributes.containsKey("style")) {
//            applyInlineStyles(imageComponent, attributes.get("style"));
//        }
//        return imageComponent;
//    }
//
//    private InputComponent createInput(String content, Map<String, String> attributes) {
//        String id = attributes.getOrDefault("id", "");
//        InputComponent textInput = new InputComponent(id);
//        if (attributes.containsKey("class")) {
//            String[] classes = attributes.get("class").split("\\s+");
//        }
//        return textInput;
//    }
//
//    private void applyInlineStyles(IGuiComponent component, String styleStr) {
//        String[] styles = styleStr.split(";");
//        for (String style : styles) {
//            String[] parts = style.split(":", 2);
//            if (parts.length == 2) {
//                String property = parts[0].trim();
//                String value = parts[1].trim();
//
//
//                if (component instanceof ContainerComponent) {
//                    ((ContainerComponent) component).style(property, value);
//                } else if (component instanceof ButtonComponent) {
//                    ((ButtonComponent) component).style(property, value);
//                } else if (component instanceof TextComponent) {
//                    ((TextComponent) component).style(property, value);
//                } else if (component instanceof InputComponent) {
//                    ((InputComponent) component).style(property, value);
//                } else if (component instanceof ImageComponent) {
//                    ((ImageComponent) component).style(property, value);
//                }
//            }
//        }
//    }
//
//    public IGuiComponent getComponentById(String id) {
//        return idToComponent.get(id);
//    }
}