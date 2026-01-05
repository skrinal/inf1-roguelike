package model.interfaces;

import model.Player;

/**
 * Interface for bosses.
 */
public interface Boss extends SpectralAttacker {
    void onBossTurn(Player player);
}
