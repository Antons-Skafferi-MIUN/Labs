/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern;

/**
 *
 * @author viro
 */
public class EnemyShipFactory {

    private EnemyShipFactory() {
    }
    
    public static EnemyShip makeEnemyShip(String shipType) {

        switch (shipType) {
            case "U":
                return new UFOEnemyShip();
            case "R":
                return new RocketEnemyShip();
            case "B": 
                return new BigUFOEnemeyShip();
            default:
                return null;
        }
    }
}
