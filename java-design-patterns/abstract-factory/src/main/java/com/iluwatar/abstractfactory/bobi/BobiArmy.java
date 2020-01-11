package com.iluwatar.abstractfactory.bobi;

import com.iluwatar.abstractfactory.Army;

public class BobiArmy implements Army {
    public static final String DESCRIPTION = "BOBI ARMY";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
