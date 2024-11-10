package com.flappygrant.grantbrowse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File browserConfFile = new File("browser.conf");

        if (!browserConfFile.exists()) {
            try (FileWriter writer = new FileWriter(browserConfFile)) {
                writer.write("home_page=" + Settings.get("HOME_PAGE") + "\n");
                writer.write("url_bar_height=" + Settings.get("URL_BAR_HEIGHT") + "\n");
                writer.write("home_btn_width=" + Settings.get("HOME_BTN_WIDTH") + "\n");
                writer.write("settings_btn_width=" + Settings.get("SETTINGS_BTN_WIDTH") + "\n");
                writer.write("base_font_size=" + Settings.get("BASE_FONT_SIZE") + "\n");
                writer.write("paragraph_font_size=" + Settings.get("PARAGRAPH_FONT_SIZE") + "\n");
                writer.write("heading1_font_size=" + Settings.get("HEADING1_FONT_SIZE"));
            } catch (IOException e) {
                System.err.println("Error writing to browser.conf: " + e.getMessage());
                System.exit(1);
            }
        }

        try (BufferedReader bReader = new BufferedReader(new FileReader(browserConfFile))) {
            String line;

            while ((line = bReader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && line.contains("=")) {
                    String[] parts = line.split("=", 2);

                    if (parts.length == 2) {
                        String key = parts[0].trim().toUpperCase();
                        String value = parts[1].trim();
                        Settings.set(key, value);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading browser.conf: " + e.getMessage());
            System.exit(1);
        }

        WindowManager window = new WindowManager();
        window.visit((String) Settings.get("HOME_PAGE"));
        WindowManager.start(window);
    }
}
