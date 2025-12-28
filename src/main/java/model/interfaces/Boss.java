package model.interfaces;

import model.Player;

public interface Boss extends SpectralAttacker {
    void onBossTurn(Player player);
}
