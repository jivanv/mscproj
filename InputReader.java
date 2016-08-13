package inputReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class InputReader {
	
	static String batteryDetails;
	static String memoryDetails;
	static String timeHorizon;
	static ArrayList<String> windowsDownlinkUK = new ArrayList<String>();
	static ArrayList<String> windowsBeacon = new ArrayList<String>();
	static ArrayList<String> windowsDownlinkCapeTown = new ArrayList<String>();
	static ArrayList<String> windowsPanel = new ArrayList<String>();
	static ArrayList<String> windowsRM = new ArrayList<String>();
	static ArrayList<String> windowsJANUS = new ArrayList<String>();
	static ArrayList<String> windowsCamera = new ArrayList<String>();
	static ArrayList<String> windowsFUNCUBEedu = new ArrayList<String>();
	static ArrayList<String> windowsFUNCUBEama = new ArrayList<String>();
	static String UVTRXdownlinkRate;
	static String UVTRXbatteryConsumptionDownlink;
	static String UVTRXbatteryConsumptionBeacon;
	static String STXdownlinkRate;
	static String STXbatteryConsumption;
	static String JANUSbatteryConsumptionHigh;
	static String JANUSbatteryConsumptionLow;
	static String JANUSdataGenerated;
	static String TOPCATbatteryConsumption;
	static String TOPCATdataGenerated;
	static String C3DbatteryConsumption;
	static String C3DdataGeneratedCamera;
	static String C3DdataGeneratedRM;
	static String C3DdataGeneratedHouseKeeping;
	static String FUNCUBEbatteryConsumptionEdu;
	static String FUNCUBEbatteryConsumptionAma;
	static String MICbatteryConsumption;
	static String MICdataGenerated;
	static String constantlyActiveBatteryConsumption;
	static String batteryChargingRate;
	static String durationPhoto;
	static String durationRadiation;
	static String durationC3DturnOn;
	static String durationC3DturnOff;
	static String durationC3Dhk;
	static String durationTOPCAT;
	static String durationTOPCATturnOn;
	static String durationTOPCATturnOff;
	static String durationJANUShigh;
	static String durationJANUSlow;
	static String durationMIC;
	static String durationMICturnOn;
	static String durationMICturnOff;
	
	public static void main(String[] args) {
		generateDetailsFromInput();
		writeNDDLinitial();
		writeNDDLmodel();
	}
	
	public static void generateDetailsFromInput() {
		String csvFile = "/Users/Jivan/Documents/input.csv";
		BufferedReader br = null;
		String line = "";
		String comma = ",";
		
		try {
			FileReader fr = new FileReader(csvFile);
			br = new BufferedReader(fr);
			while((line = br.readLine()) != null) {
				String[] row = line.split(comma);
				
				// inputs for the initial state
				
				// initial battery and memory levels
				if(row[0].equals("Battery")) {
					batteryDetails = "Battery battery = new Battery("+row[1]+", 0, "+row[1]+");";
				}
				
				if(row[0].equals("Memory")) {
					memoryDetails = "Memory memory = new Memory(0, 0, "+row[1]+");";
				}
				
				if(row[0].equals("Horizon")) {
					timeHorizon = row[1];
				}
				
				// UK downlink
				if(row[0].equals("UVTRX_downlink")) {
					windowsDownlinkUK.add("fact(nanosat.uvtrx.downlinkWindow.Available downlinkWindowUK"+row[1]+");");
					windowsDownlinkUK.add("downlinkWindowUK"+row[1]+".start = "+row[2]+";");
					windowsDownlinkUK.add("downlinkWindowUK"+row[1]+".end = "+row[3]+";");
				}
				
				// beacon signal
				if(row[0].equals("UVTRX_beacon")) {
					windowsBeacon.add("fact(nanosat.uvtrx.beaconWindow.Available beaconWindow"+row[1]+");");
					windowsBeacon.add("beaconWindow"+row[1]+".start = "+row[2]+";");
					windowsBeacon.add("beaconWindow"+row[1]+".end = "+row[3]+";");
				}
				
				// Cape Town downlink
				if(row[0].equals("STX_downlink")) {
					windowsDownlinkCapeTown.add("fact(nanosat.stx.window.Available downlinkWindowCapeTown"+row[1]+");");
					windowsDownlinkCapeTown.add("downlinkWindowCapeTown"+row[1]+".start = "+row[2]+";");
					windowsDownlinkCapeTown.add("downlinkWindowCapeTown"+row[1]+".end = "+row[3]+";");
				}
				
				// sunlight window for solar panel
				if(row[0].equals("Sunlight")) {
					windowsPanel.add("fact(nanosat.panel.window.Available panelWindow"+row[1]+");");
					windowsPanel.add("panelWindow"+row[1]+".start = "+row[2]+";");
					windowsPanel.add("panelWindow"+row[1]+".end = "+row[3]+";");
				}
				
				// sunlight window for educational FUNCUBE2 communications
				if(row[0].equals("Sunlight")) {
					windowsFUNCUBEedu.add("fact(nanosat.funcube2.educationWindow.Available educationWindow"+row[1]+");");
					windowsFUNCUBEedu.add("educationWindow"+row[1]+".start = "+row[2]+";");
					windowsFUNCUBEedu.add("educationWindow"+row[1]+".end = "+row[3]+";");
				}
				
				// eclipse window for amateur FUNCUBE2 communications
				if(row[0].equals("Eclipse")) {
					windowsFUNCUBEama.add("fact(nanosat.funcube2.amateurWindow.Available amateurWindow"+row[1]+");");
					windowsFUNCUBEama.add("amateurWindow"+row[1]+".start = "+row[2]+";");
					windowsFUNCUBEama.add("amateurWindow"+row[1]+".end = "+row[3]+";");
				}
				
				// SAA for radiation monitor
				if(row[0].equals("SAA")) {
					windowsRM.add("fact(nanosat.c3d.rmWindow.Available SAA"+row[1]+");");
					windowsRM.add("SAA"+row[1]+".start = "+row[2]+";");
					windowsRM.add("SAA"+row[1]+".end = "+row[3]+";");
				}
				
				// SAA for JANUS high power
				if(row[0].equals("SAA")) {
					windowsJANUS.add("fact(nanosat.janus.window.Available SAAjanushighpower"+row[1]+");");
					windowsJANUS.add("SAAjanushighpower"+row[1]+".start = "+row[2]+";");
					windowsJANUS.add("SAAjanushighpower"+row[1]+".end = "+row[3]+";");
				}
				
				// sunlight window for camera
				if(row[0].equals("Sunlight")) {
					windowsCamera.add("fact(nanosat.c3d.camWindow.Available camWindow"+row[1]+");");
					windowsCamera.add("camWindow"+row[1]+".start = "+row[2]+";");
					windowsCamera.add("camWindow"+row[1]+".end = "+row[3]+";");
				}
				
				// inputs for the model
				
				if(row[0].equals("UVTRX_downlinkRate")) {
					UVTRXdownlinkRate = row[1];
				}
				
				if(row[0].equals("UVTRX_batteryConsumption_downlink")) {
					UVTRXbatteryConsumptionDownlink = row[1];
				}
				
				if(row[0].equals("UVTRX_batteryConsumption_beacon")) {
					UVTRXbatteryConsumptionBeacon = row[1];
				}
				
				if(row[0].equals("STX_downlinkRate")) {
					STXdownlinkRate = row[1];
				}
				
				if(row[0].equals("STX_batteryConsumption")) {
					STXbatteryConsumption = row[1];
				}
				
				if(row[0].equals("JANUS_batteryConsumption_high")) {
					JANUSbatteryConsumptionHigh = row[1];
				}
				
				if(row[0].equals("JANUS_batteryConsumption_low")) {
					JANUSbatteryConsumptionLow = row[1];
				}
				
				if(row[0].equals("JANUS_dataGenerated")) {
					JANUSdataGenerated = row[1];
				}
				
				if(row[0].equals("TOPCAT_batteryConsumption")) {
					TOPCATbatteryConsumption = row[1];
				}
				
				if(row[0].equals("TOPCAT_dataGenerated")) {
					TOPCATdataGenerated = row[1];
				}
				
				if(row[0].equals("C3D_batteryConsumption")) {
					C3DbatteryConsumption = row[1];
				}
				
				if(row[0].equals("C3D_dataGenerated_camera")) {
					C3DdataGeneratedCamera = row[1];
				}
				
				if(row[0].equals("C3D_dataGenerated_radiationMonitor")) {
					C3DdataGeneratedRM = row[1];
				}
				
				if(row[0].equals("C3D_dataGenerated_houseKeeping")) {
					C3DdataGeneratedHouseKeeping = row[1];
				}
				
				if(row[0].equals("FUNCUBE_batteryConsumption_education")) {
					FUNCUBEbatteryConsumptionEdu = row[1];
				}
				
				if(row[0].equals("FUNCUBE_batteryConsumption_amateur")) {
					FUNCUBEbatteryConsumptionAma = row[1];
				}
				
				if(row[0].equals("MIC_batteryConsumption")) {
					MICbatteryConsumption = row[1];
				}
		
				if(row[0].equals("MIC_dataGenerated")) {
					MICdataGenerated = row[1];
				}
				
				if(row[0].equals("ConstantlyActive_batteryConsumption")) {
					constantlyActiveBatteryConsumption = row[1];
				}
				
				if(row[0].equals("Battery_chargingRate")) {
					batteryChargingRate = row[1];
				}
				
				if(row[0].equals("C3D_duration_photo")) {
					durationPhoto = row[1];
				}
				
				if(row[0].equals("C3D_duration_radiation")) {
					durationRadiation = row[1];
				}
				
				if(row[0].equals("C3D_duration_hk")) {
					durationC3Dhk = row[1];
				}
				
				if(row[0].equals("C3D_duration_turnOn")) {
					durationC3DturnOn = row[1];
				}
				
				if(row[0].equals("C3D_duration_turnOff")) {
					durationC3DturnOff = row[1];
				}
				
				if(row[0].equals("TOPCAT_duration")) {
					durationTOPCAT = row[1];
				}
				
				if(row[0].equals("TOPCAT_duration_turnOn")) {
					durationTOPCATturnOn = row[1];
				}
				
				if(row[0].equals("TOPCAT_duration_turnOff")) {
					durationTOPCATturnOff = row[1];
				}
				
				if(row[0].equals("JANUS_duration_high")) {
					durationJANUShigh = row[1];
				}
				
				if(row[0].equals("JANUS_duration_low")) {
					durationJANUSlow = row[1];
				}
				
				if(row[0].equals("MIC_duration")) {
					durationMIC = row[1];
				}
		
				if(row[0].equals("MIC_duration_turnOn")) {
					durationMICturnOn = row[1];
				}
				
				if(row[0].equals("MIC_duration_turnOff")) {
					durationMICturnOff = row[1];
				}
			}
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		catch (IOException e)  {
			e.printStackTrace();
		}
		
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void writeNDDLinitial() {   
        try {
        	File file = new File("/Users/Jivan/Documents/initial.txt");     
        	FileReader fr = new FileReader(file);
        	BufferedReader br = new BufferedReader(fr);
        	String line = "", oldtext = "";
        	while((line = br.readLine()) != null) {
        		oldtext += line + "\r\n";
        	}
        	br.close();

        	String replacementText  = oldtext.replace("INSERTbatteryDetails", batteryDetails);
        	replacementText  = replacementText.replace("INSERTmemoryDetails", memoryDetails);
        	replacementText  = replacementText.replaceAll("INSERThorizon", timeHorizon);
        	
        	replacementText  = replacementText.replace("INSERTdownlinkWindowsUK", writeWindows(windowsDownlinkUK));
        	replacementText  = replacementText.replace("INSERTbeaconWindows", writeWindows(windowsBeacon));
        	replacementText  = replacementText.replace("INSERTdownlinkWindowsCapeTown", writeWindows(windowsDownlinkCapeTown));
        	replacementText  = replacementText.replace("INSERTpanelWindows", writeWindows(windowsPanel));
        	replacementText  = replacementText.replace("INSERTrmWindows", writeWindows(windowsRM));
        	replacementText  = replacementText.replace("INSERTjanusHighPowerWindows", writeWindows(windowsJANUS));
        	replacementText  = replacementText.replace("INSERTcameraWindows", writeWindows(windowsCamera));
        	replacementText  = replacementText.replace("INSERTfuncubeEduWindows", writeWindows(windowsFUNCUBEedu));
        	replacementText  = replacementText.replace("INSERTfuncubeAmaWindows", writeWindows(windowsFUNCUBEama));
        	
        	FileWriter writer = new FileWriter("/Users/Jivan/Documents/initial.txt");
        	writer.write(replacementText);
        	writer.close();
        }
	    catch (IOException e) {
	    	e.printStackTrace();
     	}
	 }

	public static String writeWindows(ArrayList<String> windows) {
		StringBuilder windowString = new StringBuilder();
    	for(String window : windows) {
    		windowString.append(window + "\r");
    	}	
    	String windowsToAdd = windowString.toString();
    	return windowsToAdd;
	}

	public static void writeNDDLmodel() {
		try {
        	File file = new File("/Users/Jivan/Documents/model.txt");     
        	FileReader fr = new FileReader(file);
        	BufferedReader br = new BufferedReader(fr);
        	String line = "", oldtext = "";
        	while((line = br.readLine()) != null) {
        		oldtext += line + "\r\n";
        	}
        	br.close();

        	String replacementText  = oldtext.replaceAll("INSERThorizon", timeHorizon);
        	replacementText  = replacementText.replaceAll("INSERTdurationPhoto", durationPhoto);
        	replacementText  = replacementText.replaceAll("INSERTdurationRadiation", durationRadiation);
        	replacementText  = replacementText.replaceAll("INSERTdurationC3DturnOn", durationC3DturnOn);
        	replacementText  = replacementText.replaceAll("INSERTdurationC3DturnOff", durationC3DturnOff);
        	replacementText  = replacementText.replaceAll("INSERTdurationC3Dhk", durationC3Dhk);
        	replacementText  = replacementText.replaceAll("INSERTdurationTOPCAT", durationTOPCAT);
        	replacementText  = replacementText.replaceAll("INSERTdurationTOPCATturnOn", durationTOPCATturnOn);
        	replacementText  = replacementText.replaceAll("INSERTdurationTOPCATturnOff", durationTOPCATturnOff);
        	replacementText  = replacementText.replaceAll("INSERTdurationJANUShigh", durationJANUShigh);
        	replacementText  = replacementText.replaceAll("INSERTdurationJANUSlow", durationJANUSlow);
        	replacementText  = replacementText.replaceAll("INSERTdurationMIC", durationMIC);
        	replacementText  = replacementText.replaceAll("INSERTdurationMICturnOn", durationMICturnOn);
        	replacementText  = replacementText.replaceAll("INSERTdurationMICturnOff", durationMICturnOff);
        	replacementText  = replacementText.replaceAll("INSERTC3DdataGeneratedCamera", C3DdataGeneratedCamera);
        	replacementText  = replacementText.replaceAll("INSERTC3DdataGeneratedHouseKeeping", C3DdataGeneratedHouseKeeping);
        	replacementText  = replacementText.replaceAll("INSERTC3DbatteryConsumption", C3DbatteryConsumption);
        	replacementText  = replacementText.replaceAll("INSERTC3DdataGeneratedRM", C3DdataGeneratedRM);
        	replacementText  = replacementText.replaceAll("INSERTTOPCATbatteryConsumption", TOPCATbatteryConsumption);
        	replacementText  = replacementText.replaceAll("INSERTTOPCATdataGenerated", TOPCATdataGenerated);
        	replacementText  = replacementText.replaceAll("INSERTJANUSbatteryConsumptionHigh", JANUSbatteryConsumptionHigh);
        	replacementText  = replacementText.replaceAll("INSERTJANUSbatteryConsumptionLow", JANUSbatteryConsumptionLow);
        	replacementText  = replacementText.replaceAll("INSERTJANUSdataGenerated", JANUSdataGenerated);
        	replacementText  = replacementText.replaceAll("INSERTMICbatteryConsumption", MICbatteryConsumption);
        	replacementText  = replacementText.replaceAll("INSERTMICdataGenerated", MICdataGenerated);
        	replacementText  = replacementText.replaceAll("INSERTFUNCUBEbatteryConsumptionEdu", FUNCUBEbatteryConsumptionEdu);
        	replacementText  = replacementText.replaceAll("INSERTFUNCUBEbatteryConsumptionAma", FUNCUBEbatteryConsumptionAma);
        	replacementText  = replacementText.replaceAll("INSERTconstantlyActiveBatteryConsumption", constantlyActiveBatteryConsumption);
        	replacementText  = replacementText.replaceAll("INSERTbatteryChargingRate", batteryChargingRate);
        	replacementText  = replacementText.replaceAll("INSERTSTXdownlinkRate", STXdownlinkRate);
        	replacementText  = replacementText.replaceAll("INSERTSTXbatteryConsumption", STXbatteryConsumption);
        	replacementText  = replacementText.replaceAll("INSERTUVTRXdownlinkRate", UVTRXdownlinkRate);
        	replacementText  = replacementText.replaceAll("INSERTUVTRXbatteryConsumptionDownlink", UVTRXbatteryConsumptionDownlink);
        	replacementText  = replacementText.replaceAll("INSERTUVTRXbatteryConsumptionBeacon", UVTRXbatteryConsumptionBeacon); 
        	
        	FileWriter writer = new FileWriter("/Users/Jivan/Documents/model.txt");
        	writer.write(replacementText);
        	writer.close();
        }
	    catch (IOException e) {
	    	e.printStackTrace();
     	}
	}
}