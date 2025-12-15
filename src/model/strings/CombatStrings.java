package model.strings;

import model.Enemy;
import model.Player;
import model.enums.status.StatusEffects;

public class CombatStrings {
    private static final int LEFT_PANEL_WIDTH = 37;
    private static final int CENTER_PANEL_WIDTH = 30;
    private static final int RIGHT_PANEL_WIDTH = 37;

    public CombatStrings() { }

    public void printCombatMenu(Player player, Enemy enemy) {
        StringBuilder combinedUI = new StringBuilder();
        String[] leftPanel = this.createLeftPanel(player).split("\n");
        String[] centerPanel = this.createCenterPanel(player).split("\n");
        String[] rightPanel = this.createRightPanel(enemy).split("\n");

        int maxLines = Math.max(leftPanel.length, Math.max(centerPanel.length, rightPanel.length));

        for (int i = 0; i < maxLines; i++) {
            String left = i < leftPanel.length ? leftPanel[i] : "";
            String center = i < centerPanel.length ? centerPanel[i] : "";
            String right = i < rightPanel.length ? rightPanel[i] : "";

            // Pad each panel to its fixed width
            combinedUI.append(this.padToWidth(left, LEFT_PANEL_WIDTH));
            combinedUI.append(" "); // Spacer
            combinedUI.append(this.padToWidth(center, CENTER_PANEL_WIDTH));
            combinedUI.append(" "); // Spacer
            combinedUI.append(this.padToWidth(right, RIGHT_PANEL_WIDTH));
            combinedUI.append("\n");
        }

        System.out.println(combinedUI);
    }

    private String padToWidth(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        return text + " ".repeat(width - text.length());
    }

    private String createLeftPanel(Player player) {
        StringBuilder ui = new StringBuilder();

        // Top border
        ui.append("╔═════════════════════════════════╗\n");

        //TODO: Rewrite to one function as its duplicated in createRightPanel
        // Name and class line - padded to fit within the box
        String nameClass = "  " + player.getName().toUpperCase() + " [" + player.getClassType() + "]";
        ui.append("║")
                .append(this.padLine(nameClass, 33))
                .append("║\n");

        // Separator
        ui.append("╠═════════════════════════════════╣\n");

        // HP line
        String hpText = " HP:      [" + player.getHealthBar() + "] " +
                player.getHp() + "/" + player.getMaxHp();
        ui.append("║")
                .append(this.padLine(hpText, 33))
                .append("║\n");

        // Power line (Mana/Energy/Rage)
        String powerText = " " + player.getPowerString() + ":    [" +
                player.getPowerBar() + "] " +
                player.getPower() + "/" + player.getMaxPower();
        ui.append("║")
                .append(this.padLine(powerText, 33))
                .append("║\n");


        ui.append("║")
                .append(this.padLine("", 33))
                .append("║\n");
        ui.append("║")
                .append(this.padLine(" Effects:", 33))
                .append("║\n");

        if (!player.isStatusEffectsEmpty()) {
            for (StatusEffects status : player.getStatusEffects().keySet()) {
                String statusText = " " + status.toString() + " - " + status.getDescription();
                ui.append("║").append(this.padLine(statusText, 33)).append("║\n");
            }
        }

        // Bottom border
        ui.append("╚═════════════════════════════════╝");

        return ui.toString();
    }

    private String createCenterPanel(Player player) {
        StringBuilder ui = new StringBuilder();
        ui.append("╔══════════════════════════╗\n");


        ui.append("║")
                .append(this.padLine(
                    String.format(" [1] %s (%s)", player.getBasicAbilityName(), player.getBasicAbilityCost()), 26))
                .append("║\n");

        ui.append("║")
                .append(this.padLine(
                    String.format(" [2] %s (%s)", player.getSpecialAbilityName(), player.getSpecialAbilityCost()), 26))
                .append("║\n");

        ui.append("║")
                .append(this.padLine(
                    String.format(" [3] %s (%s)", player.getUtilityAbilityName(), player.getUtilityAbilityCost()
                ), 26))
                .append("║\n");


        ui.append("║")
                .append(this.padLine("  [4] Rest ", 26))
                .append("║\n");
        ui.append("║")
                .append(this.padLine("  [5] Inventory", 26))
                .append("║\n");

        ui.append("╚══════════════════════════╝");

        return ui.toString();
    }

    private String createRightPanel(Enemy enemy) {
        StringBuilder ui = new StringBuilder();

        // Top border
        ui.append("╔═════════════════════════════════╗\n");

        // Name and type line
        String nameType = "  " + enemy.getName().toUpperCase() + " [" + enemy.getEnemyType() + "]";
        ui.append("║")
                .append(this.padLine(nameType, 33))
                .append("║\n");

        // Separator
        ui.append("╠═════════════════════════════════╣\n");

        // HP line
        String hpText = " HP:      [" + enemy.getHealthBar() + "] " +
                enemy.getHp() + "/" + enemy.getMaxHp();
        ui.append("║").append(this.padLine(hpText, 33)).append("║\n");

        // Defense line
        String defText = " Defense: " + enemy.getTotalDefense();
        ui.append("║").append(this.padLine(defText, 33)).append("║\n");

        // Bottom border
        ui.append("╚═════════════════════════════════╝");

        return ui.toString();
    }

    /**
     * Pads a line to exact width (for content inside box borders)
     * The width should be panel width - 2 (for the borders)
     */
    private String padLine(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width); // Truncate if too long
        }
        return text + " ".repeat(width - text.length());
    }
}