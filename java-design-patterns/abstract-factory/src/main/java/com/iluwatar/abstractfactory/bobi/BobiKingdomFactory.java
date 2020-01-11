package com.iluwatar.abstractfactory.bobi;

import com.iluwatar.abstractfactory.*;

public class BobiKingdomFactory implements KingdomFactory {
    @Override
    public Castle createCastle() {
        return new BobiCastle();
    }

    @Override
    public King createKing() {
        return new BobiKing();
    }

    @Override
    public Army createArmy() {
        return new BobiArmy();
    }


    public static void main(String[] args) {
        App app = new App();
        KingdomFactory bokiFactory = new BobiKingdomFactory();
        app.createKingdom(bokiFactory);

        System.out.println(app.getArmy().getDescription());
        System.out.println(app.getCastle().getDescription());
        System.out.println(app.getKing().getDescription());



    }
}
