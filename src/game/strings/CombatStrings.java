//package game.strings;
//
//import model.Enemy;
//import model.Player;
//
//public class CombatStrings {
//    private static final int LEFT_PANEL_WIDTH = 37;
//    private static final int CENTER_PANEL_WIDTH = 32;
//    private static final int RIGHT_PANEL_WIDTH = 37;
//
//    public static void printCombatMenu(Player player, Enemy enemy) {
//        StringBuilder combinedUI = new StringBuilder();
//        String[] leftPanel = createLeftPanel(player).split("\n");
//        String[] centerPanel = createCenterPanel(player).split("\n");
//        String[] rightPanel = createRightPanel(enemy).split("\n");
//
//        int maxLines = Math.max(leftPanel.length, Math.max(centerPanel.length, rightPanel.length));
//
//        for (int i = 0; i < maxLines; i++) {
//            combinedUI.append(i < leftPanel.length ? leftPanel[i] : " ".repeat(leftPanel[0].length()));
//            combinedUI.append(" "); // Spacer
//            combinedUI.append(i < centerPanel.length ? centerPanel[i] : " ".repeat(centerPanel[0].length()));
//            combinedUI.append(" "); // Spacer
//            combinedUI.append(i < rightPanel.length ? rightPanel[i] : " ".repeat(rightPanel[0].length()));
//            combinedUI.append("\n");
//        }
//
////        for (int i = 0; i < maxLines; i++) {
////            // Use the defined width constants for padding empty lines. This is safer.
////            combinedUI.append(leftPanel[i] + centerPanel[i] + rightPanel[i]);
////        }
//
//        System.out.println(combinedUI.toString());
//    }
//
//    private static String createLeftPanel(Player player) {
//        StringBuilder ui = new StringBuilder();
//        ui.append("╔═════════════════════════════════╗\n");
//        ui.append("║  " + player.getName().toUpperCase() + " [" + player.getClassType() + "]\n");
//        ui.append("╠═════════════════════════════════╣\n");
//        ui.append("║ HP:      [" + player.getHealthBar() + "] " + player.getHp() + "/" + player.getMaxHp() + "\n");
//        ui.append("║ " + player.getPowerName() + ":    [" + player.getPowerBar() + "] " + player.getPower() + "/" + player.getMaxPower() + "\n");
//        ui.append("╚═════════════════════════════════╝ \n");
//
//        return ui.toString();
//    }
//
//    private static String createCenterPanel(Player player) {
//        StringBuilder ui = new StringBuilder();
//        ui.append("╔══════════════════════════╗\n");
//
//        switch (player.getClassType()) {
//            case MAGE -> {
//                ui.append("║    [1] Fireblast (50)    ║\n");
//                ui.append("║    [2] Alter Time (20)   ║\n"); // TODO: A bit hard -> maybe "Mage Armor"
//                ui.append("║    [3] Fireball (10)     ║\n");
//                ui.append("║    [4] Rest              ║\n");
//            }
//            case ROGUE -> {
//                ui.append("║ [1] Sinister Strike (30) ║ " );
//                ui.append("║ [2] Vanish (70)          ║ " );
//                ui.append("║ [3] First Aid (10)       ║ " );
//                ui.append("║ [4] Rest                 ║ " );
//            }
//            case WARRIOR -> {
//                ui.append("║   [1] Bloodthirst (30)   ║ " );
//                ui.append("║   [2] Execute (50)       ║ " );
//                ui.append("║   [3] Ignore Pain (10)   ║ " );
//                ui.append("║   [4] Rest               ║ " );
//            }
//        }
//
//        ui.append("╠══════════════════════════╣\n");
//        ui.append("║         [D]efend         ║\n");
//        ui.append("║         [I]tem           ║\n");
//        ui.append("╚══════════════════════════╝\n");
//
//        return ui.toString();
//    }
//
//    private static String createRightPanel(Enemy enemy) {
//        StringBuilder ui = new StringBuilder();
//        ui.append("╔═════════════════════════════════╗\n");
//        ui.append("║  " + enemy.getName().toUpperCase() + " [" + enemy.getEnemyType() + "]\n");
//        ui.append("╠═════════════════════════════════╣\n");
//        ui.append("║ HP:      [" + enemy.getHealthBar() + "] " + enemy.getHp() + "/" + enemy.getMaxHp() + "\n");
//        ui.append("║ Defense: " + enemy.getTotalDefense() + "\n");
//        ui.append("╚═════════════════════════════════╝ \n");
//
//        return ui.toString();
//    }
//}

package game.strings;

import model.Enemy;
import model.Player;

public class CombatStrings {
    private static final int LEFT_PANEL_WIDTH = 37;
    private static final int CENTER_PANEL_WIDTH = 30;
    private static final int RIGHT_PANEL_WIDTH = 37;

    public static void printCombatMenu(Player player, Enemy enemy) {
        StringBuilder combinedUI = new StringBuilder();
        String[] leftPanel = createLeftPanel(player).split("\n");
        String[] centerPanel = createCenterPanel(player).split("\n");
        String[] rightPanel = createRightPanel(enemy).split("\n");

        int maxLines = Math.max(leftPanel.length, Math.max(centerPanel.length, rightPanel.length));

        for (int i = 0; i < maxLines; i++) {
            String left = i < leftPanel.length ? leftPanel[i] : "";
            String center = i < centerPanel.length ? centerPanel[i] : "";
            String right = i < rightPanel.length ? rightPanel[i] : "";

            // Pad each panel to its fixed width
            combinedUI.append(padToWidth(left, LEFT_PANEL_WIDTH));
            combinedUI.append(" "); // Spacer
            combinedUI.append(padToWidth(center, CENTER_PANEL_WIDTH));
            combinedUI.append(" "); // Spacer
            combinedUI.append(padToWidth(right, RIGHT_PANEL_WIDTH));
            combinedUI.append("\n");
        }

        System.out.println(combinedUI.toString());
    }

    private static String padToWidth(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        return text + " ".repeat(width - text.length());
    }

    private static String createLeftPanel(Player player) {
        StringBuilder ui = new StringBuilder();

        // Top border
        ui.append("╔═════════════════════════════════╗\n");

        // Name and class line - padded to fit within the box
        String nameClass = "  " + player.getName().toUpperCase() + " [" + player.getClassType() + "]";
        ui.append("║").append(padLine(nameClass, 33)).append("║\n");

        // Separator
        ui.append("╠═════════════════════════════════╣\n");

        // HP line
        String hpText = " HP:      [" + player.getHealthBar() + "] " +
                player.getHp() + "/" + player.getMaxHp();
        ui.append("║").append(padLine(hpText, 33)).append("║\n");

        // Power line (Mana/Energy/Rage)
        String powerText = " " + player.getPowerName() + ":    [" +
                player.getPowerBar() + "] " +
                player.getPower() + "/" + player.getMaxPower();
        ui.append("║").append(padLine(powerText, 33)).append("║\n");

        // Bottom border
        ui.append("╚═════════════════════════════════╝");

        return ui.toString();
    }

    private static String createCenterPanel(Player player) {
        StringBuilder ui = new StringBuilder();
        ui.append("╔══════════════════════════╗\n");

        switch (player.getClassType()) {
            case MAGE -> {
                ui.append("║").append(padLine("    [1] Fireblast (50)", 26)).append("║\n");
                ui.append("║").append(padLine("    [2] Alter Time (20)", 26)).append("║\n");
                ui.append("║").append(padLine("    [3] Fireball (10)", 26)).append("║\n");
                ui.append("║").append(padLine("    [4] Rest", 26)).append("║\n");
            }
            case ROGUE -> {
                ui.append("║").append(padLine(" [1] Sinister Strike (30)", 26)).append("║\n");
                ui.append("║").append(padLine(" [2] Vanish (70)", 26)).append("║\n");
                ui.append("║").append(padLine(" [3] First Aid (10)", 26)).append("║\n");
                ui.append("║").append(padLine(" [4] Rest", 26)).append("║\n");
            }
            case WARRIOR -> {
                ui.append("║").append(padLine("   [1] Bloodthirst (30)", 26)).append("║\n");
                ui.append("║").append(padLine("   [2] Execute (50)", 26)).append("║\n");
                ui.append("║").append(padLine("   [3] Ignore Pain (10)", 26)).append("║\n");
                ui.append("║").append(padLine("   [4] Rest", 26)).append("║\n");
            }
        }

        ui.append("╠══════════════════════════╣\n");
        ui.append("║").append(padLine("         [D]efend", 26)).append("║\n");
        ui.append("║").append(padLine("         [I]tem", 26)).append("║\n");
        ui.append("╚══════════════════════════╝");

        return ui.toString();
    }

    private static String createRightPanel(Enemy enemy) {
        StringBuilder ui = new StringBuilder();

        // Top border
        ui.append("╔═════════════════════════════════╗\n");

        // Name and type line
        String nameType = "  " + enemy.getName().toUpperCase() + " [" + enemy.getEnemyType() + "]";
        ui.append("║").append(padLine(nameType, 33)).append("║\n");

        // Separator
        ui.append("╠═════════════════════════════════╣\n");

        // HP line
        String hpText = " HP:      [" + enemy.getHealthBar() + "] " +
                enemy.getHp() + "/" + enemy.getMaxHp();
        ui.append("║").append(padLine(hpText, 33)).append("║\n");

        // Defense line
        String defText = " Defense: " + enemy.getTotalDefense();
        ui.append("║").append(padLine(defText, 33)).append("║\n");

        // Bottom border
        ui.append("╚═════════════════════════════════╝");

        return ui.toString();
    }

    /**
     * Pads a line to exact width (for content inside box borders)
     * The width should be panel width - 2 (for the borders)
     */
    private static String padLine(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width); // Truncate if too long
        }
        return text + " ".repeat(width - text.length());
    }
}