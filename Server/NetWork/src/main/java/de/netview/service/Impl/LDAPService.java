package de.netview.service.Impl;

import de.netview.dao.ILDAPUserDao;
import de.netview.dao.Impl.SystemuserDao;
import de.netview.data.ADUserData;
import de.netview.data.ADUserUpdateData;
import de.netview.data.HardwareData;
import de.netview.data.HardwareInformation;
import de.netview.data.LDAPUserData;
import de.netview.data.LDAPUserInformation;
import de.netview.data.LizenzData;
import de.netview.data.SettingsData;
import de.netview.model.ADSetting;
import de.netview.model.Hardware;
import de.netview.model.LDAPUser;
import de.netview.model.Location;
import de.netview.model.Systemuser;
import de.netview.service.IHardwareService;
import de.netview.service.ILDAPService;
import de.netview.service.ILocationService;
import de.netview.service.ISettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class LDAPService implements ILDAPService {

	static String USER_BASE_NAME = "OU=intern,OU=Users,OU=Reservix,DC=rsvx,DC=it";
	static String DOMAIN_NAME = "test.local";
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

	private void LDAPConnect() {
		try {
			
			SettingsData settingsData = settingService.getSettings();
			adSetting = settingsData.getAd();
			
			USER_BASE_NAME = adSetting.getInterneuserou();
			DOMAIN_NAME = adSetting.getDomaine();
			 
			//System.setProperty("javax.net.ssl.trustStore", TRUST_STORE);
			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, "ldap://" + adSetting.getServer());
			ldapEnv.put(Context.SECURITY_PROTOCOL, "ANY");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn="+adSetting.getBinduser()+","+adSetting.getBinduserou());
			ldapEnv.put(Context.SECURITY_CREDENTIALS, adSetting.getPassword());
			ldapContext = new InitialDirContext(ldapEnv);
		} catch (Exception e) {
			System.out.println(" bind error: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	

	@Override
	public List getLDAPADUsers() {
		
		List<ADUserData> result = new ArrayList<>();
		try {
			ArrayList<ADUserData> ADUserDataList = (ArrayList<ADUserData>) getLDAPUserByFilter("(objectClass=*)");
			ArrayList<Location> LocationList = (ArrayList<Location>) locationService.getLocation();
			ArrayList<HardwareInformation> hardwareList = (ArrayList<HardwareInformation>) hardwareService.getAllHardware("clients");

			for (ADUserData aDUserdata : ADUserDataList) {
				for (Location location : LocationList) {
					if (location.getCity().equals(aDUserdata.getCity())
							&& location.getStreet().equals(aDUserdata.getStreetAddress())) {
						aDUserdata.setLid(location.getLid());
						break;
					}
				}
				
				for (HardwareInformation hardware : hardwareList) {
					if (hardware.getOwner() != null && hardware.getOwner().equalsIgnoreCase(aDUserdata.getFirstname()+"."+aDUserdata.getLastname())) {
						aDUserdata.setHardware(hardware);
						break;
					}
				}
				
				result.add(aDUserdata);
			}
			
			
		} catch (Exception e) {
			
		}
		
		
		return result;
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

	private String checkAndConvertObjectAttribute(Object attribute) throws NamingException {
		return attribute == null ? "" : (String) ((BasicAttribute) attribute).get();
	}

	public void createLDAPNewUser(ADUserUpdateData adUserUpdateData) {
		LDAPConnect();
		try {
			String username = adUserUpdateData.getFirstname() + "." + adUserUpdateData.getLastname();
			Attributes newAttributes = new BasicAttributes(true);
			javax.naming.directory.Attribute oc = new BasicAttribute("objectclass");
			oc.add("top");
			oc.add("person");
			oc.add("organizationalperson");
			oc.add("user");
			newAttributes.put(oc);
			
			Map<String, String> dataMap = createLDAPUserData(adUserUpdateData);
			LDAPConnect();
			for (Map.Entry<String, String> attribute : dataMap.entrySet()) {
				newAttributes.put(new BasicAttribute(attribute.getKey(), attribute.getValue()));
			}
			
			newAttributes.put(new BasicAttribute("userPrincipalName", username + "@" + DOMAIN_NAME));
			newAttributes.put(new BasicAttribute("cn", adUserUpdateData.getFirstname() + " " + adUserUpdateData.getLastname()));
			ldapContext.createSubcontext("cn=" + adUserUpdateData.getFirstname() + " " + adUserUpdateData.getLastname()
					+ "," + USER_BASE_NAME, newAttributes);
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

	private Map createLDAPUserData(ADUserUpdateData adUserUpdateData) throws Exception {

		Location location = locationService.getLocationById(adUserUpdateData.getLid());

		if (location == null) {
			throw new Exception("Location nicht gefunden");
		}

		Map<String, String> data = new HashMap();
		data.put("sAMAccountName", adUserUpdateData.getFirstname() + "." + adUserUpdateData.getLastname());
		data.put("displayName", adUserUpdateData.getFirstname() + " " + adUserUpdateData.getLastname());
		data.put("givenName", adUserUpdateData.getFirstname());
		data.put("sn", adUserUpdateData.getLastname());
		data.put("department", adUserUpdateData.getDepartment());
		data.put("title", adUserUpdateData.getPosition());
		data.put("streetAddress", location.getStreet());
		data.put("postalCode", location.getPlz());
		data.put("l", location.getCity());
		data.put("company", "Reservix GmbH");
		data.put("mobile", adUserUpdateData.getMobile());
		data.put("telephoneNumber", adUserUpdateData.getTel());

		return data;
	}

	public void updateLDAPUser(ADUserUpdateData adUserUpdateData) throws Exception {

		Map<String, String> updateData = createLDAPUserData(adUserUpdateData);
		LDAPConnect();
		for (Map.Entry<String, String> attribute : updateData.entrySet()) {
			try {

				String attributeName = attribute.getKey();
				String attributeValue = attribute.getValue();

				ModificationItem[] mods = new ModificationItem[1];
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute(attributeName, attributeValue));
				ldapContext.modifyAttributes(
						"cn=" + adUserUpdateData.getUid() + ","+USER_BASE_NAME, mods);

			} catch (Exception e) {
				System.out.println(" update error: " + attribute.getKey());
			}
		}
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
			ldapContext.modifyAttributes("cn=" + username + ","+USER_BASE_NAME, mods);
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
			for (NamingEnumeration<?> ae = attributes.getAll(); ae.hasMoreElements();) {
				javax.naming.directory.Attribute attr = (Attribute) ae.next();
				String attrId = attr.getID();
				for (NamingEnumeration<?> vals = attr.getAll(); vals.hasMore();) {
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
			return new ADUserData();
		}else {
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
			hardwareData.wrapperHardware(hardware);
			hardwareOwnerList.add(hardwareData);
		}

		for (Hardware hardware : hardwareService.getHardwareByUserList(username)) {
			HardwareData hardwareData = new HardwareData();
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
		}else {
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
		}else {
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
