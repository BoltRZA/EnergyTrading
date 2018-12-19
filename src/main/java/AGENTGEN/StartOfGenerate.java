package AGENTGEN;


import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.lang.acl.ACLMessage;

public class StartOfGenerate extends WakerBehaviour {
	private Agent agent;
	private AID[] links;
	private long timeout;
	private long nextSend;
	private int[] gen;
	public boolean startgen = false;

	public StartOfGenerate(Agent agent, long timeout, AID[] links, int[] gen, long nextSend) {
		super(agent, timeout);
		this.agent = agent;
		this.links =links;
		this.gen = gen;
		this.nextSend = nextSend;
	}
	@Override
	protected void onWake() {
		super.onWake();
		//Текущее время в часах
		int timeH = TimeUtil.CurrentHour(); 
		System.out.println(timeH+" текущий час");
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
				startgen = true;
				//				System.out.println(agent.getLocalName());
				//				System.out.println(rec);
			}
			else {
				break;
			}


		}
		
		nextSend = TimeUtil.getSecMillTillNextHour();
		myAgent.addBehaviour(new WakerGen(this.agent,nextSend,links,gen));
		//Запуск тикера на передачу через  nextSend(мс) до следующего часа через тредслип, тикер просыпается каждый час 
//		try {
//			Thread.sleep(nextSend);
//			long hourDuration = TimeUtil.hourDuration;
//			myAgent.addBehaviour(new TickerGenerate(this.agent,hourDuration,links,gen));
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}


}	

