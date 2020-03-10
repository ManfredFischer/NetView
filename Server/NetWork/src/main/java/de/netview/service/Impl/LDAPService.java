package de.netview.service.Impl;

import de.netview.dao.ILDAPUserDao;
import de.netview.data.*;
import de.netview.function.impl.StringOwnUtils;
import de.netview.model.*;
import de.netview.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class LDAPService implements ILDAPService {

    static String USER_BASE_NAME = "OU=intern,OU=Users,OU=Reservix,DC=rsvx,DC=it";
    static String GROUP_BASE_NAME = "OU=Groups,OU=Reservix,DC=rsvx,DC=it";
    static String DOMAIN_NAME = "reservix.de";
    DirContext ldapContext;

    public ADSetting adSetting;

    @Autowired
    private IHardwareService hardwareService;

    @Autowired
    private ISettingService settingService;

    @Autowired
    private ILocationService locationService;

    @Autowired
    private ILDAPUserDao ldapUserDao;

    @Autowired
    private IChangelogService changelogService;

    @Autowired
    private ISMTPService SMTPService;

    private boolean LDAPConnect() {
        try {

            SettingsData settingsData = settingService.getSettings();
            adSetting = settingsData.getAd();

            USER_BASE_NAME = adSetting.getInterneuserou();

            Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
            ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            ldapEnv.put(Context.PROVIDER_URL, "ldap://" + adSetting.getServer());
            //ldapEnv.put(Context.SECURITY_PROTOCOL, "SSL");
            ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
            ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=" + adSetting.getBinduser() + "," + adSetting.getBinduserou());
            ldapEnv.put(Context.SECURITY_CREDENTIALS, adSetting.getPassword());
            ldapContext = new InitialDirContext(ldapEnv);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public List getLDAPADUsers() {

        if (AllInformation.getADUsers().isEmpty()) {
            List<ADUserData> result = new ArrayList<>();
            try {
                ArrayList<ADUserData> ADUserDataList = (ArrayList<ADUserData>) getLDAPUserByFilter("(objectClass=*)");
                ArrayList<Location> LocationList = (ArrayList<Location>) locationService.getLocation();
                ArrayList<HardwareInformation> hardwareList = (ArrayList<HardwareInformation>) hardwareService.getAllHardware("clients");

                for (ADUserData aDUserdata : ADUserDataList) {
                    for (Location location : LocationList) {
                        if (!StringUtils.isEmpty(location.getCity()) && !StringUtils.isEmpty(location.getStreet())) {
                            if (location.getCity().equals(aDUserdata.getCity())
                                    && location.getStreet().equals(aDUserdata.getStreetAddress())) {
                                aDUserdata.setLid(location.getLid());
                                break;
                            }
                        }
                    }

                    for (HardwareInformation hardware : hardwareList) {
                        if (hardware.getOwner() != null && hardware.getOwner().equalsIgnoreCase(aDUserdata.getFirstname() + "." + aDUserdata.getLastname())) {
                            aDUserdata.setHardware(hardware);
                            break;
                        }
                    }

                    result.add(aDUserdata);
                }


            } catch (Exception e) {
                System.out.printf(e.getMessage());
            }


            AllInformation.setADUsers(result);

        }

        return AllInformation.getADUsers();
    }

    @Override
    public List<LDAPGroup> getLDAPGroups() {
        List<LDAPGroup> ldapGroups = new ArrayList<>();

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        try {
            NamingEnumeration values = ldapContext.search(GROUP_BASE_NAME, "(objectclass=group)", searchControls);

            while (values.hasMoreElements()) {
                SearchResult searchResult = (SearchResult) values.next();
                Attributes attributes = searchResult.getAttributes();
                LDAPGroup ldapGroup = new LDAPGroup();
                ldapGroup.setGroupname(checkAndConvertObjectAttribute(attributes.get("name")));
                ldapGroup.setGroupdn(checkAndConvertObjectAttribute(attributes.get("distinguishedName")));
                ldapGroups.add(ldapGroup);
            }
        } catch (NamingException e) {

        }
        return ldapGroups;
    }

    private List<ADUserData> getLDAPUserByFilter(String filter) {
        LDAPConnect();
        ArrayList<ADUserData> allInternUsers = new ArrayList<>();
        try {
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            NamingEnumeration values = ldapContext.search(USER_BASE_NAME, filter, searchControls);

            while (values.hasMoreElements()) {
                SearchResult searchResult = (SearchResult) values.next();
                Attributes attributes = searchResult.getAttributes();

                if (!(attributes.get("name").get().toString().equalsIgnoreCase("intern"))) {
                    ADUserData adUserData = new ADUserData();
                    adUserData.setUid(checkAndConvertObjectAttribute(attributes.get("userPrincipalName")).split("@")[0]);
                    adUserData.setMail(checkAndConvertObjectAttribute(attributes.get("mail")));
                    adUserData.setDisplayName(checkAndConvertObjectAttribute(attributes.get("displayName")));
                    adUserData.setFirstname(checkAndConvertObjectAttribute(attributes.get("givenName")));
                    adUserData.setLastname(checkAndConvertObjectAttribute(attributes.get("sn")));
                    adUserData.setDepartment(checkAndConvertObjectAttribute(attributes.get("department")));
                    adUserData.setOtherTelepone(checkAndConvertObjectAttribute(attributes.get("othertelephone")));
                    adUserData.setTelephoneNumber(checkAndConvertObjectAttribute(attributes.get("telephoneNumber")));
                    adUserData.setTitle(checkAndConvertObjectAttribute(attributes.get("title")));
                    adUserData.setMobile(checkAndConvertObjectAttribute(attributes.get("mobile")));
                    adUserData.setStreetAddress(checkAndConvertObjectAttribute(attributes.get("streetAddress")));
                    adUserData.setCountry(checkAndConvertObjectAttribute(attributes.get("countryCode")));
                    adUserData.setCity(checkAndConvertObjectAttribute(attributes.get("l")));
                    adUserData.setPlz(checkAndConvertObjectAttribute(attributes.get("postCode")));
                    allInternUsers.add(adUserData);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            ldapContext.close();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allInternUsers;
    }

    @Override
    public boolean tryConnectionToLDAP() {
        boolean connected = LDAPConnect();
        try {
            if (connected)
                ldapContext.close();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connected;
    }

    @Override
    public void addUserToGroup(String userDn, String groupDn) throws NamingException {
        ModificationItem[] mods = new ModificationItem[1];
        Attribute mod = new BasicAttribute("member", userDn);
        mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, mod);
        ldapContext.modifyAttributes(groupDn, mods);
    }

    @Override
    public List<String> getLDAPUserGroups(String username) {
        return getLDAPUserGroupsWithPath(username, false);
    }

    @Override
    public List<String> getLDAPUserGroupsWithPath(String username, Boolean withAllPath) {
        LDAPConnect();
        ArrayList<String> groups = new ArrayList<>();
        try {
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            NamingEnumeration values = ldapContext.search(USER_BASE_NAME, "(SAMAccountName=" + username + ")", searchControls);

            while (values.hasMoreElements()) {
                SearchResult searchResult = (SearchResult) values.next();
                Attributes attributes = searchResult.getAttributes();

                if (!(attributes.get("name").get().toString().equalsIgnoreCase("intern"))) {

                    NamingEnumeration<?> memberOf = attributes.get("memberOf").getAll();

                    while (memberOf.hasMore()) {
                        String value = (String) memberOf.next();
                        if (withAllPath) {
                            groups.add(value);
                        } else {
                            groups.add(value.split(",")[0].split("CN=")[1]);
                        }
                    }
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            ldapContext.close();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return groups;
    }

    private String checkAndConvertObjectAttribute(Object attribute) throws NamingException {
        return attribute == null ? "" : (String) ((BasicAttribute) attribute).get();
    }

    public void createLDAPNewUser(WizardUser adUserUpdateData) {
        LDAPConnect();
        try {
            String username = StringOwnUtils.replaceUmlaut(adUserUpdateData.getUser().getFirstname()) + "." + StringOwnUtils.replaceUmlaut(adUserUpdateData.getUser().getLastname());
            adUserUpdateData.getUser().setMail(username + "@reservix.de");

            Attributes newAttributes = new BasicAttributes(true);
            javax.naming.directory.Attribute oc = new BasicAttribute("objectclass");
            oc.add("top");
            oc.add("person");
            oc.add("organizationalperson");
            oc.add("user");
            newAttributes.put(oc);

            Map<String, String> dataMap = createLDAPUserData(adUserUpdateData);
            for (Map.Entry<String, String> attribute : dataMap.entrySet()) {
                newAttributes.put(new BasicAttribute(attribute.getKey(), attribute.getValue()));
            }
            newAttributes.put(new BasicAttribute("userPassword", "{MD5}" + SystemuserService.cryptWithMD5("Start123!")));
            newAttributes.put(new BasicAttribute("userPrincipalName", username + "@reservix.de"));
            newAttributes.put(new BasicAttribute("cn", adUserUpdateData.getUser().getFirstname() + " " + adUserUpdateData.getUser().getLastname()));
            String userDN = "cn=" + adUserUpdateData.getUser().getFirstname() + " " + adUserUpdateData.getUser().getLastname() + ",OU=wizard," + USER_BASE_NAME;
            ldapContext.createSubcontext(userDN, newAttributes);

            // Add User to Group

            String reservixIntern = "CN=g_u_it,OU=intern,OU=Groups,OU=Reservix,DC=rsvx,DC=it";
            String reservixExtern = "";
            String confluence = "";
            String jira = "";

            //addUserToGroup(userDN,groupDN);

            SMTPService.sendUserInformation(adUserUpdateData.getUser());

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ldapContext.close();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private Map createLDAPUserData(WizardUser adUserUpdateData) throws Exception {
        Location location = locationService.getLocationById(1L);

        if (location == null) {
            throw new Exception("Location nicht gefunden");
        }

        Map<String, String> data = new HashMap();
        data.put("sAMAccountName", adUserUpdateData.getUser().getFirstname() + "." + adUserUpdateData.getUser().getLastname());
        data.put("displayName", adUserUpdateData.getUser().getFirstname() + " " + adUserUpdateData.getUser().getLastname());
        data.put("givenName", adUserUpdateData.getUser().getFirstname());
        data.put("sn", adUserUpdateData.getUser().getLastname());

        data.put("streetAddress", location.getStreet());
        data.put("postalCode", location.getPlz());
        data.put("l", location.getCity());
        data.put("company", "Reservix GmbH");
        data.put("mobile", adUserUpdateData.getUser().getMobile());
        data.put("telephoneNumber", adUserUpdateData.getUser().getPhone());
        data.put("mail", adUserUpdateData.getUser().getMail());


        data.put("department", "test");
        data.put("title", "test");

        return data;
    }

    public void updateLDAPUser(WizardUser wizardUser) throws Exception {

        /*Map<String, String> updateData = createLDAPUserData(adUserUpdateData);
        LDAPConnect();
        for (Map.Entry<String, String> attribute : updateData.entrySet()) {
            try {

                String attributeName = attribute.getKey();
                String attributeValue = attribute.getValue();

                ModificationItem[] mods = new ModificationItem[1];
                mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute(attributeName, attributeValue));
                ldapContext.modifyAttributes(
                        "cn=" + adUserUpdateData.getUid() + "," + USER_BASE_NAME, mods);

            } catch (Exception e) {
                System.out.println(" update error: " + attribute.getKey());
            }
        }*/
        try {
            ldapContext.close();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateLDAPUserPassword(String username, String password) {
        LDAPConnect();
        try {
            String quotedPassword = "\"" + password + "\"";
            char unicodePwd[] = quotedPassword.toCharArray();
            byte pwdArray[] = new byte[unicodePwd.length * 2];
            for (int i = 0; i < unicodePwd.length; i++) {
                pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
                pwdArray[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
            }
            for (int i = 0; i < pwdArray.length; i++) {
                System.out.print(pwdArray[i] + " ");
            }
            System.out.println();
            ModificationItem[] mods = new ModificationItem[1];
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("UnicodePwd", pwdArray));
            ldapContext.modifyAttributes("cn=" + username + "," + USER_BASE_NAME, mods);
        } catch (Exception e) {
        }
        try {
            ldapContext.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public Attributes getLDAPUserAttributes(String username) {
        LDAPConnect();
        Attributes attributes = null;
        try {
            DirContext o = (DirContext) ldapContext.lookup("cn=" + username + USER_BASE_NAME);
            attributes = o.getAttributes("");
            for (NamingEnumeration<?> ae = attributes.getAll(); ae.hasMoreElements(); ) {
                javax.naming.directory.Attribute attr = (Attribute) ae.next();
                String attrId = attr.getID();
                for (NamingEnumeration<?> vals = attr.getAll(); vals.hasMore(); ) {
                    String thing = vals.next().toString();
                }
            }
        } catch (Exception e) {
        }
        try {
            ldapContext.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return attributes;
    }


    @Override
    public ADUserData getLDAPUserByName(String name) {
        List<ADUserData> user = getLDAPUserByFilter("(SAMAccountName=" + name + ")");
        if (user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }


    @Override
    @Transactional
    public LDAPUserData getLDAPUserData(String username) {
        LDAPUserData userdata = new LDAPUserData();
        ArrayList<HardwareData> hardwareOwnerList = new ArrayList<>();
        ArrayList<HardwareData> hardwareAktivList = new ArrayList<>();
        ArrayList<LizenzData> hardwareAktivLizenzList = new ArrayList<>();

        username = username.split("_")[0] + "." + username.split("_")[1];
        ADUserData adUser = getLDAPUserByName(username);


        for (Hardware hardware : hardwareService.getHardwareByOwnerList(username)) {
            HardwareData hardwareData = new HardwareData();
            hardwareData.setChangelogList(changelogService.getChangelogListByHID(hardware.getHid()));
            hardwareData.wrapperHardware(hardware);
            hardwareOwnerList.add(hardwareData);
        }

        for (Hardware hardware : hardwareService.getHardwareByUserList(username)) {
            HardwareData hardwareData = new HardwareData();
            hardwareData.setChangelogList(changelogService.getChangelogListByHID(hardware.getHid()));
            hardwareData.wrapperHardware(hardware);
            hardwareAktivList.add(hardwareData);
            hardwareAktivLizenzList.addAll(hardwareData.getLizenz());
        }

        userdata.setOwnerHardware(hardwareOwnerList);
        userdata.setAktivHardware(hardwareAktivList);
        userdata.setLizenz(hardwareAktivLizenzList);
        userdata.setLdapUser(new LDAPUserInformation(ldapUserDao.getLDAPUserByName(username)));
        userdata.setUserData(adUser);

        return userdata;
    }


    @Override
    @Transactional
    public void addOrUpdateLDAPUser(LDAPUser ldapUser) {
        LDAPUser ldapUserOrg = ldapUserDao.getLDAPUserByName(ldapUser.getUsername());
        if (ldapUserOrg != null) {
            ldapUserOrg.setDescription(ldapUser.getDescription());
            ldapUserOrg.setToken(ldapUser.getToken());
        } else {
            ldapUserOrg = ldapUser;
        }
        ldapUserDao.addOrUpdateLDAPUser(ldapUserOrg);
    }

    @Override
    @Transactional
    public void rentHardwareForLDAPUser(Map value) {
        Hardware hardware = hardwareService.getHardwareById(Long.parseLong(value.get("hid").toString()));
        LDAPUser ldapUser = ldapUserDao.getLDAPUserByName(value.get("uid").toString());
        boolean save = true;
        if (ldapUser == null) {
            ldapUser = new LDAPUser();
            ldapUser.setUsername(value.get("uid").toString());
            ldapUser.getHardwarerent().add(hardware);
        } else {
            for (Hardware hardwareInfo : ldapUser.getHardwarerent()) {
                if (hardwareInfo.getHid() == Long.parseLong(value.get("hid").toString())) {
                    save = false;
                    break;
                }
            }
            if (save) {
                ldapUser.getHardwarerent().add(hardware);
            }
        }
        if (save) {
            ldapUserDao.addOrUpdateLDAPUser(ldapUser);
        }

        hardware.setVerliehen(true);
        hardware.setVerliehenBis(Long.valueOf(value.get("date").toString()));
        hardwareService.saveHardware(hardware);
    }

    @Override
    @Transactional
    public void getBackHardwareFromLDAPUser(Map value) {
        Hardware hardware = hardwareService.getHardwareById(Long.parseLong(value.get("hid").toString()));
        LDAPUser ldapUser = ldapUserDao.getLDAPUserByName(value.get("username").toString());
        if (ldapUser != null) {
            ldapUser.getHardwarerent().remove(hardware);
            ldapUserDao.addOrUpdateLDAPUser(ldapUser);
        }

        hardware.setVerliehen(false);
        hardwareService.saveHardware(hardware);
    }


}
