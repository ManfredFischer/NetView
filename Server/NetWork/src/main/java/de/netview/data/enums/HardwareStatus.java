package de.netview.data.enums;

public enum HardwareStatus {

    AKTIV(0),
    PRUEFEN(1),
    LAGER(2),
    ARCHIV(3);

    private final int value;

    HardwareStatus(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }

    public Boolean compare(HardwareStatus hardwareStatus){
        return hardwareStatus.getValue() == getValue();
    }


}
