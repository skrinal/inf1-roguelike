package model.strings;

import model.Enemy;
import model.Player;
import model.enums.status.StatusEffects;

/**
 * A utility class for generating and displaying combat-related textual interfaces.
 * This class provides methods to print styled combat messages and menus to the console.
 */
public class CombatStrings {
    private final int leftPanelWidth = 40;
    private final int centerPanelWidth = 26;
    private final int rightPanelWidth = 36;
    private final int paddingInBetween = 4;

    public CombatStrings() { /* No need to initialize anything */ }

    /**
     * Outputs an message to the console indicating the start of a combat sequence.
     */
    public void printCombatStart() {
        System.out.println("\n╔════════════════╗");
        System.out.println("║  COMBAT START  ║");
        System.out.println("╚════════════════╝");

    }

    /**
     * Outputs a message to the console indicating that an enemy has been defeated.
     */
    public void printEnemyDefeated() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║                                  ║");
        System.out.println("║    Enemy has been defeated !!!   ║");
        System.out.println("║                                  ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    /**
     * Outputs the combat menu to the console.
     * At first we create three panels, each with a fixed width. Which we split into lines.
     * Then we print each line to the console, padding it to the correct width.
     * @param player
     * @param enemy
     */
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
            combinedUI.append(this.padToWidth(left, this.leftPanelWidth + this.paddingInBetween));
            combinedUI.append(" "); // Spacer
            combinedUI.append(this.padToWidth(center, this.centerPanelWidth + this.paddingInBetween));
            combinedUI.append(" "); // Spacer
            combinedUI.append(this.padToWidth(right, this.rightPanelWidth + this.paddingInBetween));
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
        ui.append("╔").append("═".repeat(this.leftPanelWidth)).append("╗\n");

        // Name and class line + level
        String nameClass = "  " + player.getName().toUpperCase() + " [" + player.getCombatTag() + "]" + " Level " + player.getLevel();
        ui.append("║")
                .append(this.padLine(nameClass, this.leftPanelWidth))
                .append("║\n");

        // Separator
        ui.append("╠").append("═".repeat(this.leftPanelWidth)).append("╣\n");

        // HP line
        String hpText = " HP:      [" + player.getHealthBar() + "] " +
                player.getHp() + "/" + player.getMaxHp();
        ui.append("║")
                .append(this.padLine(hpText, this.leftPanelWidth))
                .append("║\n");

        // Power line (Mana/Energy/Rage)
        String powerText = " " + player.getPowerString() + ":    [" +
                player.getPowerBar() + "] " +
                player.getPower() + "/" + player.getMaxPower();
        ui.append("║")
                .append(this.padLine(powerText, this.leftPanelWidth))
                .append("║\n");


        ui.append("║")
                .append(this.padLine("", this.leftPanelWidth))
                .append("║\n");
        ui.append("║")
                .append(this.padLine(" Effects:", this.leftPanelWidth))
                .append("║\n");

        if (!player.isStatusEffectsEmpty()) {
            for (StatusEffects status : player.getStatusEffects().keySet()) {
                String statusText = " " + status.getName() + " - " + status.getDescription();
                ui.append("║").append(this.padLine(statusText, this.leftPanelWidth)).append("║\n");
            }
        }

        // Bottom border
        ui.append("╚").append("═".repeat(this.leftPanelWidth)).append("╝");

        return ui.toString();
    }

    private String createCenterPanel(Player player) {
        StringBuilder ui = new StringBuilder();

        ui.append("╔").append("═".repeat(this.centerPanelWidth)).append("╗\n");

        ui.append("║").append(this.padLine(
                String.format(" [1] %s (%s)", player.getBasicAbilityName(), player.getBasicAbilityCost()), this.centerPanelWidth)).append("║\n");

        ui.append("║").append(this.padLine(
                String.format(" [2] %s (%s)", player.getSpecialAbilityName(), player.getSpecialAbilityCost()), this.centerPanelWidth)).append("║\n");

        ui.append("║").append(this.padLine(
                String.format(" [3] %s (%s)", player.getUtilityAbilityName(), player.getUtilityAbilityCost()), this.centerPanelWidth)).append("║\n");

        ui.append("║").append(this.padLine(" [4] Rest ", this.centerPanelWidth)).append("║\n");

        ui.append("║").append(this.padLine(" [5] Inventory", this.centerPanelWidth)).append("║\n");

        ui.append("╚").append("═".repeat(this.centerPanelWidth)).append("╝");

        return ui.toString();
    }

    private String createRightPanel(Enemy enemy) {
        StringBuilder ui = new StringBuilder();

        // Top border
        ui.append("╔").append("═".repeat(this.rightPanelWidth)).append("╗\n");

        // Name and type line
        String nameType = "  " + enemy.getName().toUpperCase() + " [" + enemy.getCombatTag() + "]" + " Level " + enemy.getLevel();
        ui.append("║")
                .append(this.padLine(nameType, this.rightPanelWidth))
                .append("║\n");

        // Separator
        ui.append("╠").append("═".repeat(this.rightPanelWidth)).append("╣\n");

        // HP line
        String hpText = " HP:      [" + enemy.getHealthBar() + "] " +
                enemy.getHp() + "/" + enemy.getMaxHp();
        ui.append("║").append(this.padLine(hpText, this.rightPanelWidth)).append("║\n");

        // Defense line
        String defText = " Defense: " + enemy.getTotalDefense();
        ui.append("║").append(this.padLine(defText, this.rightPanelWidth)).append("║\n");
        ui.append("║")
                .append(this.padLine("", this.rightPanelWidth))
                .append("║\n");
        ui.append("║")
                .append(this.padLine(" Effects:", this.rightPanelWidth))
                .append("║\n");

        if (!enemy.isStatusEffectsEmpty()) {
            for (StatusEffects status : enemy.getStatusEffects().keySet()) {
                String statusText = " " + status.getName() + " - " + status.getDescription();
                ui.append("║").append(this.padLine(statusText, this.rightPanelWidth)).append("║\n");
            }
        }
        // Bottom border
        ui.append("╚").append("═".repeat(this.rightPanelWidth)).append("╝\n");

        return ui.toString();
    }

    private String padLine(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        return text + " ".repeat(width - text.length());
    }
}