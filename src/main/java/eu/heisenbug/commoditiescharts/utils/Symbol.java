package eu.heisenbug.commoditiescharts.utils;

public enum Symbol {

    GOLD("XAU"),
    BITCOIN("BTC"),
    BRENT_CRUDE_OIL("BRENTOIL");

    public final String value;

    Symbol(String value) {
        this.value = value;
    }

    public static Symbol getSymbol(String value) {
        for (Symbol symbol : values()) {
            if (symbol.value.equals(value)) {
                return symbol;
            }
        }
        return null;
    }
}
