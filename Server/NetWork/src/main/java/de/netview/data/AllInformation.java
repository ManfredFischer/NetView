package de.netview.data;

import de.netview.function.impl.IPSort;
import de.netview.model.Changelog;
import de.netview.model.Hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AllInformation {


    private static List<HardwareInformation> clientsHardware = new ArrayList<>();
    private static List<HardwareInformation> netzHardware = new ArrayList<>();

    private static List<LizenzInformation> lizenz = new ArrayList<>();
    private static List<Changelog> changelog = new ArrayList<>();
    private static List<ADUserData> users = new ArrayList<>();

    public static List<HardwareInformation> getAllHardwareInformation() {
        return clientsHardware;
    }

    public static void setAllClientsHardwareInformation(List clientsHardware) {
        AllInformation.clientsHardware = clientsHardware;
    }

    public static List<HardwareInformation> getAllNetzHardwareInformation() {
        return netzHardware;
    }

    public static void setAllNetzHardwareInformation(List netzHardware) {
        AllInformation.netzHardware = netzHardware;
    }

    public static void setLizenz(List lizenz) {
        AllInformation.lizenz = lizenz;
    }

    public static List<LizenzInformation> getLizenz() {
        return lizenz;
    }

    public static void setChangelog(List<Changelog> changelog) {
        AllInformation.changelog = changelog;
    }

    public static List<Changelog> getChangelog() {
        return changelog;
    }

    public static void setADUsers(List<ADUserData> users) {
        AllInformation.users = users;
    }

    public static List<ADUserData> getADUsers() {
        return users;
    }

    public static void addClientsHardwareInformation(Hardware hardware) {
        AtomicBoolean insert = new AtomicBoolean(true);
        for (int index = 0; index < clientsHardware.size(); index++) {
            if (hardware.getHid().longValue() == clientsHardware.get(index).getHid().longValue()) {
                insert.set(false);
                clientsHardware.remove(clientsHardware.get(index));
                clientsHardware.add(new HardwareInformation(hardware));
                break;
            }
        }
        if (insert.get()) {
            netzHardware.add(new HardwareInformation(hardware));
        }
        clientsHardware = IPSort.sortHardware(clientsHardware);
    }

    public static void addNetzHardwareInformation(Hardware hardware) {
        AtomicBoolean insert = new AtomicBoolean(true);

        for (int index = 0; index < netzHardware.size(); index++) {
            if (hardware.getHid() == netzHardware.get(index).getHid()) {
                insert.set(false);
                netzHardware.remove(netzHardware.get(index));
                netzHardware.add(new HardwareInformation(hardware));
                break;
            }
        }

        if (insert.get()) {
            netzHardware.add(new HardwareInformation(hardware));
        }
        netzHardware = IPSort.sortHardware(netzHardware);
    }
}
