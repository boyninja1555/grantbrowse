package com.flappygrant.grantbrowse;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class ContextMenu {
    int x, y;
    JPopupMenu contextMenu;
    JMenuItem[] menuItems;

    public ContextMenu(int x, int y) {
        this.x = x;
        this.y = y;
        this.contextMenu = new JPopupMenu();
    }

    public void show(JPanel panel) {
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem copyItem = new JMenuItem("Copy");
        menuItems = new JMenuItem[] { saveItem, copyItem };
        
        contextMenu = new JPopupMenu();
        contextMenu.add(saveItem);
        contextMenu.add(copyItem);
        contextMenu.show(panel, x, y);
    }

    public JMenuItem getMenuItem(int index) {
        return menuItems[index];
    }
}
