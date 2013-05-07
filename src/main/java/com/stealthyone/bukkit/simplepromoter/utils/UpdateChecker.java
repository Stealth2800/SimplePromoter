package com.stealthyone.bukkit.simplepromoter.utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;
import com.stealthyone.bukkit.simplepromoter.SimplePromoter.PluginLogger;

/**

 * SimplePromoter
 * UpdateChecker.java
 *
 * Checks for updates
 *
 * @author betterphp (http://www.youtube.com/user/betterphp), modified by Austin/Stealth2800
 * @website http://stealthyone.com/
 */

public class UpdateChecker {
	
	private SimplePromoter plugin;
	
	private URL filesFeed;
	
	private String newVersion;
	private String verLink;
	
	public UpdateChecker(SimplePromoter plugin, String url) {
		this.plugin = plugin;
		
		try {
			this.filesFeed = new URL(url);
		} catch (MalformedURLException e) {
			PluginLogger.debug("Malformed URL in update checker!");
			e.printStackTrace();
		}
	}
	
	public final boolean updateNeeded() {
		try {
			if (StringUtils.containsMultiple(plugin.getVersion(), "SNAPSHOT", "BETA", "ALPHA", "b")) {
				PluginLogger.info("Currently running a snapshot, beta, or alpha build. Update check cancelled.");
				return false;
			}
			InputStream input = this.filesFeed.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			
			Node latestFile = document.getElementsByTagName("item").item(0);
			NodeList children = latestFile.getChildNodes();
			
			this.newVersion = children.item(1).getTextContent().replace("v", "");
			this.verLink = children.item(3).getTextContent();
			PluginLogger.debug("Latest version: " + this.newVersion);
			
			String curVersion = plugin.getVersion();
			
			PluginLogger.debug("Current version: " + curVersion);
			
			if (!curVersion.equalsIgnoreCase(this.newVersion)) {
				return true;
			}
		} catch (Exception e) {
			PluginLogger.severe("Unable to check for updates!");
		}
		
		return false;
	}
	
	public final String getLink() {
		return this.verLink;
	}
	
	public final String getVersion() {
		if (newVersion == null) {
			updateNeeded();
		}
		return this.newVersion;
	}
}
