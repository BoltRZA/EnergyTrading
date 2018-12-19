package AGENT_APK;


import java.util.List;

import POMXMLAPK.Link;
import POMXMLAPK.Setting;
import POMXMLAPK.WorkWithConfiGFiles;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.df;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgentARK extends Agent {
	public double[] akum;
	public AID[] links;
	public double[] money;
	public int[] countLink;
	public double maxPrice;
	@Override
	protected void setup() {
		super.setup();
		links= new AID[10];
		money = new double[1];
		akum = new double[1];
		countLink = new int[1];
		maxPrice = 5.0;
		String agentName = this.getLocalName();
		Setting properties = WorkWithConfiGFiles.unMarshalAny(Setting.class, agentName+".xml");
		String link = properties.getLinks().get(0).getAgentName();
		akum[0] = properties.getLinks().get(0).getAkym();
		
		//����������� � ��
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd  = new ServiceDescription();
		sd.setType("APK");
		sd.setOwnership(link);
		sd.setName(agentName);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		addBehaviour(new ReceiveLoadFromGen(this, akum));
		addBehaviour(new FindLinkWithAP(this,1000,links));
		addBehaviour(new FindTopics(this,links,money,akum,countLink,maxPrice,16000));
	}

}
