package AGENTGEN;

import POMXLGEN.Setting;
import POMXLGEN.WorkWithConfiGFiles;
import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgentGen extends Agent {
	public AID[] links;
	public long nextSend;
	@Override
	protected void setup() {

		super.setup();
		links = new AID[10];
		String agentName = this.getLocalName();
		Setting properties = WorkWithConfiGFiles.unMarshalAny(Setting.class, agentName+".xml");
		int[] gen = properties.getGen();
		//Время до следующего часа
		nextSend = TimeUtil.getSecMillTillNextHour();
		//Регистрация
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd  = new ServiceDescription();
		sd.setType("GEN");
		sd.setName(agentName);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		//Поиск связей с АРК
		addBehaviour(new FindLinks(this,1,links));
		addBehaviour(new StartOfGenerate(this,15000,links,gen,nextSend));
	}

}


