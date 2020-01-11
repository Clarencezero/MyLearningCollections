package com.iluwatar.abstractfactory.bobi;

import com.iluwatar.abstractfactory.Castle;

public class BobiCastle  implements Castle {
    public static final String DESCRIPTION = "BOBI CASTLE";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
