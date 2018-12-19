package POMXLGEN;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name= "Settings")
public class Setting {
	int[] gen;

	public int[] getGen() {
		return gen;
	}

	public void setGen(int[] gen) {
		this.gen = gen;
	}
	
	
	

	
}
