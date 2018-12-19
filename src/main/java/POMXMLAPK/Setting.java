package POMXMLAPK;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name= "Settings")
public class Setting {
	List<Link> links;

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
}
