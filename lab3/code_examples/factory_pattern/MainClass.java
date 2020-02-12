/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorypattern;

import java.util.Scanner;

/**
 *
 * @author viro
 */
public class MainClass {

    public static void main(String[] args) {
        
        // Here we create the ship factory
        EnemyShip enemyShip = null;

        Scanner userInput = new Scanner(System.in);
        String typeOfShip = "";
        System.out.println("What type of ship? (U / R / B)");

        // Get user input (runtime will deciding class type)
        if (userInput.hasNextLine()) {
            typeOfShip = userInput.nextLine();
        }
        
        // The ship factory will create my ships now
        enemyShip = EnemyShipFactory.makeEnemyShip(typeOfShip);
        if (enemyShip != null) {
            doStuffEnemy(enemyShip);
        }
    }

    private static void doStuffEnemy(EnemyShip enemyShip) {
        enemyShip.displayEnemyShip();
        enemyShip.followHeroShip();
        enemyShip.enemyShipShoot();
    }
}
