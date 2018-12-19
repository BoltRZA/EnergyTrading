package POMXMLLOAD;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name= "Settings")
public class Setting {
	int[] load;

	public int[] getLoad() {
		return load;
	}

	public void setLoad(int[] load) {
		this.load = load;
	}
	
	
	

	
}
