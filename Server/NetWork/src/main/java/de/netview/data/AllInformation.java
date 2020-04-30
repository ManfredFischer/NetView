package de.netview.data;

import de.netview.function.impl.IPSort;
import de.netview.model.Changelog;

import java.util.ArrayList;
import java.util.List;

public class AllInformation {


    private static List<HardwareInformation> clientsHardware = new ArrayList<>();
    private static List<HardwareInformation> netzHardware = new ArrayList<>();

    private static List<LizenzInformation> lizenz = new ArrayList<>();
    private static List<Changelog> changelogs = new ArrayList<>();
    private static List<ADUserData> users = new ArrayList<>();

    public static List getClients(){
        return getOrUpdateClients(null,null, null);
    }

    public static List updateOrRemoveClients(HardwareInformation hardware, Boolean remove){
        return getOrUpdateClients(null, hardware, remove);
    }

    public static List setClients(List data){
        return getOrUpdateClients(data,null, null);
    }

    private synchronized static List getOrUpdateClients(List data, HardwareInformation hardware, Boolean remove){
        if (data != null){
            clientsHardware = data;
            return clientsHardware;
        }

        if (hardware == null){
            return clientsHardware;
        }

        if (remove){
            for (int index = 0; index < clientsHardware.size(); index++) {
                if (hardware.getHid() == clientsHardware.get(index).getHid()) {
                    clientsHardware.remove(clientsHardware.get(index));
                    break;
                }
            }
        } else {
            boolean insert = true;
            for (int index = 0; index < clientsHardware.size(); index++) {
                if (hardware.getHid().longValue() == clientsHardware.get(index).getHid().longValue()) {
                    insert = false;
                    clientsHardware.remove(clientsHardware.get(index));
                    clientsHardware.add(hardware);
                    break;
                }
            }
            if (insert) {
                netzHardware.add(hardware);
            }
            clientsHardware = IPSort.sortHardware(clientsHardware);
        }

        return clientsHardware;
    }

    public static List getServer(){
        return getOrUpdateServer(null,null, null);
    }

    public static List updateOrRemoveServer(HardwareInformation hardware, Boolean remove){
        return getOrUpdateServer(null, hardware, remove);
    }

    public static List setServer(List data){
        return getOrUpdateServer(data,null, null);
    }

    private synchronized static List getOrUpdateServer(List data, HardwareInformation hardware, Boolean remove){
        if (data != null){
            netzHardware = data;
            return netzHardware;
        }

        if (hardware == null){
            return netzHardware;
        }

        if (remove){
            for (int index = 0; index < netzHardware.size(); index++) {
                if (hardware.getHid() == netzHardware.get(index).getHid()) {
                    netzHardware.remove(netzHardware.get(index));
                    break;
                }
            }
        } else {
            boolean insert = true;
            for (int index = 0; index < netzHardware.size(); index++) {
                if (hardware.getHid().longValue() == netzHardware.get(index).getHid().longValue()) {
                    insert = false;
                    netzHardware.remove(netzHardware.get(index));
                    netzHardware.add(hardware);
                    break;
                }
            }
            if (insert) {
                netzHardware.add(hardware);
            }
            netzHardware = IPSort.sortHardware(netzHardware);
        }

        return netzHardware;
    }

    public static List getChangelog(){
        return getOrUpdateChangelog(null,null, null);
    }

    public static List updateOrRemoveChangelog(Changelog changelog, Boolean remove){
        return getOrUpdateChangelog(null, changelog, remove);
    }

    public static List setChangelog(List data){
        return getOrUpdateChangelog(data,null, null);
    }

    private synchronized static List getOrUpdateChangelog(List data, Changelog changelog, Boolean remove){
        if (data != null){
            changelogs = data;
            return changelogs;
        }

        if (changelog == null){
            return changelogs;
        }

        if (remove != null) {
            if (remove) {
                for (int index = 0; index < changelogs.size(); index++) {
                    if (changelog.getClid() == changelogs.get(index).getClid()) {
                        changelogs.remove(changelogs.get(index));
                        break;
                    }
                }
            } else {
                boolean insert = true;
                for (int index = 0; index < changelogs.size(); index++) {
                    if (changelog.getClid() == changelogs.get(index).getClid()) {
                        insert = false;
                        changelogs.remove(changelogs.get(index));
                        changelogs.add(changelog);
                        break;
                    }
                }
                if (insert) {
                    changelogs.add(changelog);
                }
            }
        }
        return changelogs;
    }

    public static List getLizenz(){
        return getOrUpdateLizenz(null,null, null);
    }

    public static List updateOrRemoveLizenz(LizenzInformation lizenz, Boolean remove){
        return getOrUpdateLizenz(null, lizenz, remove);
    }

    public static List setLizenz(List data){
        return getOrUpdateLizenz(data,null, null);
    }

    private synchronized static List getOrUpdateLizenz(List data, LizenzInformation lizenzData, Boolean remove){
        if (data != null){
            lizenz = data;
            return lizenz;
        }

        if (lizenzData == null){
            return lizenz;
        }
        if (remove != null) {
            if (remove) {
                for (int index = 0; index < lizenz.size(); index++) {
                    if (lizenzData.getLid() == lizenz.get(index).getLid()) {
                        lizenz.remove(lizenz.get(index));
                        break;
                    }
                }
            } else {
                boolean insert = true;
                for (int index = 0; index < lizenz.size(); index++) {
                    if (lizenzData.getLid() == lizenz.get(index).getLid()) {
                        insert = false;
                        lizenz.remove(lizenz.get(index));
                        lizenz.add(lizenzData);
                        break;
                    }
                }
                if (insert) {
                    lizenz.add(lizenzData);
                }
            }
        }
        return changelogs;
    }

    public static List getUsers(){
        return getOrUpdateUsers(null,null, null);
    }

    public static List updateOrRemoveUsers(ADUserData userData, Boolean remove){
        return getOrUpdateUsers(null, userData, remove);
    }

    public static List setUsers(List data){
        return getOrUpdateUsers(data,null, null);
    }

    private synchronized static List getOrUpdateUsers(List data, ADUserData userData, Boolean remove) {
        if (data != null) {
            users = data;
            return users;
        }

        if (userData == null) {
            return users;
        }
        if (remove != null){
            if (remove) {
                for (int index = 0; index < users.size(); index++) {
                    if (userData.getUid().equalsIgnoreCase(users.get(index).getUid())) {
                        users.remove(users.get(index));
                        break;
                    }
                }
            } else {
                boolean insert = true;
                for (int index = 0; index < users.size(); index++) {
                    if (userData.getUid().equalsIgnoreCase(users.get(index).getUid())) {
                        insert = false;
                        users.remove(users.get(index));
                        users.add(userData);
                        break;
                    }
                }
                if (insert) {
                    users.add(userData);
                }
            }
        }
        return users;
    }


}
