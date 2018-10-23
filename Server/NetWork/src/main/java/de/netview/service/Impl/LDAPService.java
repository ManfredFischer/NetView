package de.netview.service.Impl;

import de.netview.data.ADUserData;
import de.netview.data.ADUserUpdateData;
import de.netview.model.Location;
import de.netview.service.ILDAPService;
import de.netview.service.ILocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class LDAPService implements ILDAPService {

	static String USER_BASE_NAME = "OU=intern,OU=Users,OU=Reservix,DC=rsvx,DC=it";
	static String SERVER_ADDRESS = "10.128.0.5";
	static String TEMPLATE_USER = "template";
	static String DOMAIN_NAME = "test.local";
	static String SECURITY_PRINCIPAL = "CN=Service LDAP IT,OU=service,OU=Users,OU=Reservix,DC=rsvx,DC=it";
	static String SECURITY_CREDENTIALS = "Smoke185+big";
	static String TRUST_STORE = "C:\\Daten\\KeyStore\\store";
	DirContext ldapContext;

	@Autowired
	private ILocationService locationService;

	private void LDAPConnect() {
		try {
			// the keystore that holds trusted root certificates
			System.setProperty("javax.net.ssl.trustStore", TRUST_STORE);
			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, "ldap://" + SERVER_ADDRESS);
			ldapEnv.put(Context.SECURITY_PROTOCOL, "ANY");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
			ldapEnv.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
			ldapContext = new InitialDirContext(ldapEnv);
		} catch (Exception e) {
			System.out.println(" bind error: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public List getUsers() {
		LDAPConnect();
		ArrayList<ADUserData> allInternUsers = new ArrayList<>();
		try {
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			String filter = "(objectClass=*)";
			NamingEnumeration values = ldapContext.search(USER_BASE_NAME, filter, searchControls);

			while (values.hasMoreElements()) {
				SearchResult searchResult = (SearchResult) values.next();
				Attributes attributes = searchResult.getAttributes();

				if (!(attributes.get("name").get().toString().equalsIgnoreCase("intern"))) {
					ADUserData adUserData = new ADUserData();
					adUserData.setUid(checkAndConvertObjectAttribute(attributes.get("displayName")));
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

	public void createNewUser(ADUserUpdateData adUserUpdateData) {
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
			
			Map<String, String> dataMap = createUserData(adUserUpdateData);
			LDAPConnect();
			for (Map.Entry<String, String> attribute : dataMap.entrySet()) {
				newAttributes.put(new BasicAttribute(attribute.getKey(), attribute.getValue()));
			}
			
			newAttributes.put(new BasicAttribute("userPrincipalName", username + "@" + SERVER_ADDRESS));
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

	public void createCloneUser(String username, String surname, String givenName) {
		LDAPConnect();
		try {
			Attributes modelAttributes = getUserAttributes(TEMPLATE_USER);
			String distinguishedName = "cn=" + username + USER_BASE_NAME;
			Attributes newAttributes = new BasicAttributes(true);
			newAttributes.put(modelAttributes.get("objectclass"));
			newAttributes.put(modelAttributes.get("userAccountControl"));
			newAttributes.put(new BasicAttribute("sAMAccountName", username));
			newAttributes.put(new BasicAttribute("userPrincipalName", username + "@" + DOMAIN_NAME));
			newAttributes.put(new BasicAttribute("cn", username));
			newAttributes.put(new BasicAttribute("sn", surname));
			newAttributes.put(new BasicAttribute("givenName", givenName));
			newAttributes.put(new BasicAttribute("displayName", givenName + " " + surname));
			ldapContext.createSubcontext(distinguishedName, newAttributes);
		} catch (Exception e) {
			System.out.println("create clone error: " + e);
			e.printStackTrace();
			System.exit(-1);
		}
		try {
			ldapContext.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map createUserData(ADUserUpdateData adUserUpdateData) throws Exception {

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

	public void updateUser(ADUserUpdateData adUserUpdateData) throws Exception {

		Map<String, String> updateData = createUserData(adUserUpdateData);
		LDAPConnect();
		for (Map.Entry<String, String> attribute : updateData.entrySet()) {
			try {

				String attributeName = attribute.getKey();
				String attributeValue = attribute.getValue();

				ModificationItem[] mods = new ModificationItem[1];
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute(attributeName, attributeValue));
				ldapContext.modifyAttributes(
						"cn=" + adUserUpdateData.getUid() + ",OU=intern,OU=Users,OU=Reservix,DC=test,DC=local", mods);

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

	public void updateUserPassword(String username, String password) {
		LDAPConnect();
		try {
			System.out.println("updating password...\n");
			String quotedPassword = "\"" + password + "\"";
			char unicodePwd[] = quotedPassword.toCharArray();
			byte pwdArray[] = new byte[unicodePwd.length * 2];
			for (int i = 0; i < unicodePwd.length; i++) {
				pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
				pwdArray[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
			}
			System.out.print("encoded password: ");
			for (int i = 0; i < pwdArray.length; i++) {
				System.out.print(pwdArray[i] + " ");
			}
			System.out.println();
			ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("UnicodePwd", pwdArray));
			ldapContext.modifyAttributes("cn=" + username + ",OU=intern,OU=Users,OU=Reservix,DC=test,DC=local", mods);
		} catch (Exception e) {
			System.out.println("update password error: " + e);
		}
		try {
			ldapContext.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Attributes getUserAttributes(String username) {
		LDAPConnect();
		Attributes attributes = null;
		try {
			System.out.println("fetching: " + username);
			DirContext o = (DirContext) ldapContext.lookup("cn=" + username + USER_BASE_NAME);
			System.out.println("search done\n");
			attributes = o.getAttributes("");
			for (NamingEnumeration<?> ae = attributes.getAll(); ae.hasMoreElements();) {
				javax.naming.directory.Attribute attr = (Attribute) ae.next();
				String attrId = attr.getID();
				for (NamingEnumeration<?> vals = attr.getAll(); vals.hasMore();) {
					String thing = vals.next().toString();
					System.out.println(attrId + ": " + thing);
				}
			}
		} catch (Exception e) {
			System.out.println(" fetch error: " + e);
		}
		try {
			ldapContext.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attributes;
	}

}
