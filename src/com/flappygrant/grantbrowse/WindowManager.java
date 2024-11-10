package com.flappygrant.grantbrowse;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WindowManager extends JFrame {
    private Dimension screenSize;
    private JsonObject[] ELEMENTS;
    private WebViewPanel webViewPanel;
    private JTextField urlBar;
    private JButton homeBtn;
    private JButton settingsBtn;

    public WindowManager() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setTitle("Grantbrowse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenSize.width, screenSize.height);
        setLocation(0, 0);
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        urlBar = new JTextField(20);
        urlBar.setPreferredSize(new Dimension(getWidth(), (int) Settings.get("URL_BAR_HEIGHT")));
        urlBar.setBounds(0, 0, getWidth() - ((int) Settings.get("SETTINGS_BTN_WIDTH") + (int) Settings.get("HOME_BTN_WIDTH")), (int) Settings.get("URL_BAR_HEIGHT"));
        urlBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visit(urlBar.getText());
            }
        });
        urlBar.addFocusListener(new FocusAdapter() {
            private boolean firstFocus = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (firstFocus) {
                    urlBar.selectAll();
                    firstFocus = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                firstFocus = true;
            }
        });
        add(urlBar);

        homeBtn = new JButton("Home");
        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visit((String) Settings.get("HOME_PAGE"));
            }
        });
        homeBtn.setBounds(getWidth() - ((int) Settings.get("SETTINGS_BTN_WIDTH") + (int) Settings.get("HOME_BTN_WIDTH")), 0, (int) Settings.get("HOME_BTN_WIDTH"), (int) Settings.get("URL_BAR_HEIGHT"));
        add(homeBtn);

        settingsBtn = new JButton("Settings");
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visit("browser:settings#home");
            }
        });
        settingsBtn.setBounds(getWidth() - (int) Settings.get("SETTINGS_BTN_WIDTH"), 0, (int) Settings.get("SETTINGS_BTN_WIDTH"), (int) Settings.get("URL_BAR_HEIGHT"));
        add(settingsBtn);

        webViewPanel = new WebViewPanel();

        JScrollPane scrollPane = new JScrollPane(webViewPanel);
        scrollPane.setBounds(0, (int) Settings.get("URL_BAR_HEIGHT"), getWidth(), getHeight() - (int) Settings.get("URL_BAR_HEIGHT"));
        add(scrollPane);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeContent();
            }
        });
        revalidate();
        repaint();
    }

    private void resizeContent() {
        urlBar.setBounds(0, 0, getWidth() - ((int) Settings.get("SETTINGS_BTN_WIDTH") + (int) Settings.get("HOME_BTN_WIDTH")), (int) Settings.get("URL_BAR_HEIGHT"));
        homeBtn.setBounds(getWidth() - ((int) Settings.get("SETTINGS_BTN_WIDTH") + (int) Settings.get("HOME_BTN_WIDTH")), 0, (int) Settings.get("HOME_BTN_WIDTH"), (int) Settings.get("URL_BAR_HEIGHT"));
        settingsBtn.setBounds(getWidth() - (int) Settings.get("SETTINGS_BTN_WIDTH"), 0, (int) Settings.get("SETTINGS_BTN_WIDTH"), (int) Settings.get("URL_BAR_HEIGHT"));
        webViewPanel.setBounds(0, (int) Settings.get("URL_BAR_HEIGHT"), getWidth(), getHeight() - (int) Settings.get("URL_BAR_HEIGHT"));

        revalidate();
        repaint();
    }

    public void setPageName(String title) {
        setTitle(title + " - " + Settings.get("DEFAULT_TITLE"));
    }

    private class WebViewPanel extends JPanel {
        public WebViewPanel() {
            setFocusable(true);
            setBackground(Color.WHITE);
            setLayout(null);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        int x = e.getX();
                        int y = e.getY();
                        
                        ContextMenu menu = new ContextMenu(x, y);
                        menu.show(WebViewPanel.this);
                        menu.getMenuItem(0).addActionListener(event -> {
                            System.out.println("Saving page...");
                        });
                        menu.getMenuItem(1).addActionListener(event -> {
                            System.out.println("Copying text...");
                        });
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (ELEMENTS != null && ELEMENTS.length > 0) {
                for (JsonObject element : ELEMENTS) {
                    String type = element.get("type").getAsString();
                    String content = element.has("content") ? element.get("content").getAsString() : "";
                    int x = element.has("x") ? element.get("x").getAsInt() : 0;
                    int y = element.has("y") ? element.get("y").getAsInt() : 0;

                    g.setColor(Color.BLACK);

                    Font originalFont = g.getFont();

                    if ("meta".equals(type)) {
                        String title = element.get("title").getAsString();
                        setPageName(title);
                    } else if ("heading1".equals(type)) {
                        g.setFont(
                                originalFont.deriveFont((float) ((int) Settings.get("HEADING1_FONT_SIZE")) * ((int) Settings.get("BASE_FONT_SIZE"))));
                    } else if ("paragraph".equals(type)) {
                        g.setFont(originalFont
                                .deriveFont((float) ((int) Settings.get("PARAGRAPH_FONT_SIZE")) * ((int) Settings.get("BASE_FONT_SIZE"))));
                    } else if ("input-number".equals(type)) {
                        g.setFont(originalFont
                                .deriveFont((float) ((int) Settings.get("PARAGRAPH_FONT_SIZE")) * ((int) Settings.get("BASE_FONT_SIZE"))));

                        int value = element.get("value").getAsInt();
                        String text = content + " (" + value + ")";
                        int padding = element.get("styles").getAsJsonObject().get("padding").getAsInt();
                        int width = g.getFontMetrics().stringWidth(text);
                        int height = g.getFontMetrics().getHeight();
                        int squareSize = (int) Math.sqrt(width * width + height * height);
                        int squareX = x - squareSize / 2;
                        int squareY = y - squareSize / 2;
                        int squareWidth = squareSize + padding * 2;
                        int squareHeight = squareSize + padding * 2;

                        g.drawRect(squareX, squareY, squareWidth, squareHeight);
                        g.drawString(text, x, y);
                    }

                    if (element.has("styles")) {
                        JsonObject styles = element.getAsJsonObject("styles");

                        if (styles.has("font-size")) {
                            Font newFont = originalFont.deriveFont(
                                    Float.parseFloat(styles.get("font-size").getAsString()) * ((int) Settings.get("BASE_FONT_SIZE")));
                            g.setFont(newFont);
                        }

                        if (styles.has("text-color")) {
                            try {
                                g.setColor(Color.decode(styles.get("text-color").getAsString()));
                            } catch (Exception e) {
                                g.setColor(Color.BLACK);
                            }
                        }

                        if (styles.has("text-align")) {
                            String textAlign = styles.get("text-align").getAsString();

                            if ("center".equals(textAlign)) {
                                FontMetrics metrics = g.getFontMetrics(g.getFont());
                                x = (getWidth() - metrics.stringWidth(content)) / 2;
                            }
                        }
                    }

                    g.drawString(content, x, y);
                }
            }
        }
    }

    public void visit(String url) {
        webViewPanel.requestFocusInWindow();
        ELEMENTS = new JsonObject[0];

        setPageName("Untitled");

        if (url.startsWith("browser:")) {
            urlBar.setText(url);

            String[] parts = url.split("browser:")[1].split("#");
            String defaultPageName = parts[0];
            String subpageName = parts.length > 1 ? parts[1] : "home";
            String filePath = "com/flappygrant/grantbrowse/default_pages/" + defaultPageName + "/" + subpageName
                    + ".json";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            if (inputStream == null) {
                loadErrorPage("Page not found: " + defaultPageName + "/" + subpageName);
                return;
            }

            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                JsonElement jsonElement = JsonParser.parseReader(reader);

                if (jsonElement.isJsonArray()) {
                    JsonArray jsonArray = jsonElement.getAsJsonArray();
                    ELEMENTS = new JsonObject[jsonArray.size()];

                    for (int i = 0; i < jsonArray.size(); i++) {
                        if (jsonArray.get(i).isJsonObject()) {
                            ELEMENTS[i] = jsonArray.get(i).getAsJsonObject();
                        }
                    }
                } else {
                    loadErrorPage("Invalid page format: " + defaultPageName + "/" + subpageName);
                }
            } catch (Exception e) {
                loadErrorPage("Error loading page: " + defaultPageName + "/" + subpageName);
            }

            updateWebView();
            return;
        }

        if (!url.startsWith("https://")) {
            url = "https://" + url;
        }

        urlBar.setText(url);

        WebsiteClient client = new WebsiteClient(url, this);
        JsonObject[] elements = client.getElementTree();

        if (elements != null && elements.length > 0) {
            ELEMENTS = elements;
        } else {
            loadErrorPage("Could not load content from " + url);
        }

        updateWebView();
    }

    public void loadErrorPage(String message) {
        JsonObject errorElement = new JsonObject();
        errorElement.addProperty("type", "heading1");
        errorElement.addProperty("content", message);
        errorElement.addProperty("x", 0);
        errorElement.addProperty("y", 100);

        JsonObject styles = new JsonObject();
        styles.addProperty("text-align", "center");
        errorElement.add("styles", styles);

        ELEMENTS = new JsonObject[] { errorElement };
        updateWebView();
    }

    private int calculateContentHeight() {
        int maxY = 0;

        if (ELEMENTS != null) {
            for (JsonObject element : ELEMENTS) {
                int y = element.has("y") ? element.get("y").getAsInt() : 0;
                int height = element.has("height") ? element.get("height").getAsInt() : 0;
                maxY = Math.max(maxY, y + height);
            }
        }

        return maxY + 100;
    }

    public void updateWebView() {
        int contentHeight = calculateContentHeight();
        webViewPanel.setPreferredSize(new Dimension(getWidth(), contentHeight));
        webViewPanel.setBounds(0, (int) Settings.get("URL_BAR_HEIGHT"), getWidth(), contentHeight);

        revalidate();
        repaint();
    }

    public static void start(JFrame window) {
        window.setVisible(true);
    }
}
