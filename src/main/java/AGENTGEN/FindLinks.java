package AGENTGEN;

import jade.core.AID;
import jade.core.Agent;

import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class FindLinks extends WakerBehaviour {
private Agent agent;
private long timeout;
public AID[] links;

	public FindLinks(Agent agent, long timeout,AID[] links) {
		super(agent, timeout);
		this.agent = agent;	
		this.links = links;
		
		
		
	}
	@Override
	protected void onWake() {
		super.onWake();
		DFAgentDescription mt = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setOwnership(agent.getLocalName());
		sd.setType("APK");
		mt.addServices(sd);
		try {

			DFAgentDescription[] result = DFService.search(myAgent, mt);
			for (int i = 0; i < result.length; ++i) {
				links[i] = result[i].getName();	
				
				//System.out.println(links);
			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	
	}

	
	
}

