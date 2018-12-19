package AGENTGEN;

import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class TickerGenerate extends TickerBehaviour {
	private Agent agent;
	private AID[] links;
	private long hourDuration;
	private int[] gen;	
	public TickerGenerate(Agent agent, long hourDuration,AID[] links,int[] gen) {
		super(agent, hourDuration);
		this.agent = agent;
		this.links =links;
		this.gen = gen;

	}

	@Override
	protected void onTick() {
				
				//Текущее время в часах
				int timeH = TimeUtil.CurrentHour(); 
				//System.out.println(timeH);
				//Текущая выработка
				int genNow = gen[timeH];
				//Нахождение количесичества АРК
				int countAPK = 0;
				for ( int i = 0; i < links.length; i++) {
					if (links[i] != null) {
						countAPK++;
					}
				}
				//Передаваемая АРК мощность
				double sendGen = genNow/countAPK; 
				System.out.println(agent.getLocalName());
				System.out.println(sendGen);
				//Отправка передаваемой мощности
				ACLMessage loadToAPK= new ACLMessage(ACLMessage.INFORM);
				for (AID rec: links) {
					if (rec!=null) {
						loadToAPK.addReceiver(rec);
						loadToAPK.setProtocol("loadToAPK");
						loadToAPK.setContent(sendGen+"");
						agent.send(loadToAPK);
						loadToAPK.clearAllReceiver();
						
//						System.out.println(agent.getLocalName());
//						System.out.println(rec);
					}
					else {
						break;
					}
					
					
				}
				
		
	}

}
