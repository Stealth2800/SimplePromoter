package com.stealthyone.bukkit.simplepromoter.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;
import com.stealthyone.bukkit.simplepromoter.SimplePromoter.Log;
import com.stealthyone.bukkit.stcommonlib.utils.StringUtils;

public final class UpdateChecker {
	
	private SimplePromoter plugin;
	
	private boolean updateNeeded = false;
	private String newVersion = "", versionLink = "";
	
	public UpdateChecker(SimplePromoter plugin) {
		this.plugin = plugin;
	}
	
	public final boolean isUpdateNeeded() {
		return updateNeeded;
	}
	
	public final String getNewVersion() {
		return newVersion;
	}
	
	public final String getVersionLink() {
		return versionLink;
	}
	
	public final void checkForUpdates() {
		try {
			String updateLink = SimplePromoter.UPDATE_URL;
			String curVersion = plugin.getVersion();
			if (updateLink.equalsIgnoreCase("somelink") || StringUtils.containsMultiple(curVersion, "SNAPSHOT", "BETA", "ALPHA")) {
				Log.info("Currently running a snapshot, beta, or alpha build. Update check cancelled.");
				updateNeeded = false;
			} else {
				URL filesFeed = new URL(updateLink);
				URLConnection connection = filesFeed.openConnection();
				connection.setConnectTimeout(30000);
				InputStream input = connection.getInputStream();
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
				
				Node latestFile = document.getElementsByTagName("item").item(0);
				NodeList children = latestFile.getChildNodes();
				
				newVersion = children.item(1).getTextContent().replace("v", "");
				versionLink = children.item(3).getTextContent();
				
				updateNeeded = !curVersion.equals(newVersion);
			}
		} catch (Exception e) {
			Log.severe("Unable to check for updates!");
			e.printStackTrace();
		}
	}
	
}