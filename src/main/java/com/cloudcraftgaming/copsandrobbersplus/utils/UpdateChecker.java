package com.cloudcraftgaming.copsandrobbersplus.utils;

import com.cloudcraftgaming.copsandrobbersplus.Main;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nova Fox on 5/8/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: CopsAndRobbersPlus.
 */
public class UpdateChecker {
    private Main plugin;
    private URL filesFeed;
    private String version;
    private String link;

    public UpdateChecker(Main plugin, String url) {
        this.plugin = plugin;
        try {
            this.filesFeed = new URL(url);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public boolean UpdateNeeded() {
        try {
            InputStream input = this.filesFeed.openConnection().getInputStream();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            Node latestFile = document.getElementsByTagName("item").item(0);
            NodeList children = latestFile.getChildNodes();
            this.version = children.item(1).getTextContent().replaceAll("[a-zA-z ]", "");
            this.link = children.item(3).getTextContent();
            if (!this.plugin.getDescription().getVersion().equals(this.version)) {
                return true;
            }
        }
        catch (Exception e) {
            plugin.getLogger().info("Could not check for updates! URL is not correct! Report this to Shades161 on dev bukkit!");
            e.printStackTrace();
        }
        return false;
    }

    public String getVersion() {
        return this.version;
    }

    public String getLink() {
        return this.link;
    }
}
