package de.netview.function.impl;

import java.util.*;
import java.math.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import de.netview.data.HardwareInformation;
import de.netview.function.IIPSort;

@Service
public class IPSort implements IIPSort {

	@Override
	public List<HardwareInformation> sortHardware(ArrayList<HardwareInformation> hardwareInformationList) {
		ArrayList<HardwareInformation> result = new ArrayList<HardwareInformation>();
		ArrayList<String> ips = new ArrayList<String>();
		ArrayList<HardwareInformation> dontCheck = new ArrayList<HardwareInformation>();

		for (HardwareInformation hardwareInformation : hardwareInformationList) {
			if (!StringUtils.isEmpty(hardwareInformation.getIp())) {
				boolean dontCheckThis = false;
				String[] tempArray = hardwareInformation.getIp().split("\\.");
				for (String ipBlock : tempArray) {
					if (!StringUtils.isNumeric(ipBlock)) {
						dontCheckThis = true;
						dontCheck.add(hardwareInformation);
						break;
					}
				}
				
				if (!dontCheckThis)
					ips.add(hardwareInformation.getIp());
			}
		}

		for (String ip : sort32BitIPs(ips)) {
			for (HardwareInformation hardwareInformation : hardwareInformationList) {
				if (!StringUtils.isEmpty(hardwareInformation.getIp())) {
					if (ip.equalsIgnoreCase(hardwareInformation.getIp())) {
						result.add(hardwareInformation);
						hardwareInformationList.remove(hardwareInformation);
						break;
					}
				}
			}
		}

		for (HardwareInformation hardwareInformation : hardwareInformationList) {
			if (StringUtils.isEmpty(hardwareInformation.getIp())) {
				result.add(hardwareInformation);
			}
		}
		
		result.addAll(dontCheck);

		return result;
	}

	private ArrayList<String> sort32BitIPs(ArrayList<String> bit32) {
		ArrayList<String> newBit32 = new ArrayList<String>();
		ArrayList<BigInteger> bigInt32Bit = new ArrayList<BigInteger>();

		for (String ip : bit32) {
			String[] tempArray = ip.split("\\.");
			int i = 0;
			for (String s : tempArray) {
				if (s.equals("")) {
					tempArray[i] = "0";
				}
				i++;
			}
			bigInt32Bit.add(convert32Bit(tempArray));
		}

		Collections.sort(bigInt32Bit);

		ArrayList<String> fixFormat = new ArrayList<String>();
		for (String ip : bit32) {
			String[] fixArray = ip.split("\\.");
			int i = 0;
			for (String s : fixArray) {
				if (s.equals("")) {
					fixArray[i] = "0";
				}
				i++;
			}

			StringBuilder strBuilder = new StringBuilder();
			for (int i2 = 0; i2 < 4; i2++) {
				if (i2 < 3) {
					try {
						if (!fixArray[i2].equals("")) {
							strBuilder.append(fixArray[i2] + ".");
						} else {
							strBuilder.append(".");
						}
					} catch (Exception e) {
						strBuilder.append("0.");
					}
				} else {
					try {
						strBuilder.append(fixArray[i2]);
					} catch (Exception e) {
						strBuilder.append("0");
					}
				}
			}
			String newString = strBuilder.toString();
			fixFormat.add(newString);
			bit32 = fixFormat;
		}

		for (BigInteger finalValue : bigInt32Bit) {
			for (String ip : bit32) {
				String[] tempArray = ip.split("\\.");
				int i = 0;
				for (String s : tempArray) {
					if (s.equals("")) {
						tempArray[i] = "0";
					}
					i++;
				}
				if (finalValue.equals(convert32Bit(tempArray))) {
					if (!newBit32.contains(ip)) {
						String str = bit32.toString();
						String findStr = ip;
						int lastIndex = 0;
						int count = 0;

						while (lastIndex != -1) {

							lastIndex = str.indexOf(findStr, lastIndex);

							if (lastIndex != -1) {
								count++;
								lastIndex += findStr.length();
							}
						}

						for (int k = 0; k < count; k++) {
							newBit32.add(ip);
						}
					}
				}
			}
		}

		return newBit32;
	}

	private BigInteger convert32Bit(String[] array) {
		int[] tempArray = new int[array.length];
		ArrayList<BigInteger> tempBigIntList = new ArrayList<BigInteger>();
		int i = 0;
		for (String s : array) {
			int power = 4 - i;
			tempArray[i] = Integer.parseInt(s);
			String string = Integer.toString(tempArray[i]);
			BigInteger myBigInt = new BigInteger(string);
			BigInteger num2 = myBigInt.multiply(new BigInteger("256").pow(power));
			tempBigIntList.add(num2);
			i++;
		}
		BigInteger bigInt32Bit = new BigInteger("0");
		for (BigInteger bI : tempBigIntList) {
			bigInt32Bit = bigInt32Bit.add(bI);
		}
		return bigInt32Bit;
	}

}