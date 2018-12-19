package AGENT_LOAD;

import TIME.TimeUtil;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;

public class StartBuy extends WakerBehaviour {
	private Agent agent;
	private int[] buingLoad;
	private double[] spentMoney;
	private AID[] links;
	private int[] load;
	private long timeout;
	public StartBuy(Agent agent,long timeout, int[] buingLoad, double[] spentMoney, AID[] links, int[] load) {
		super(agent, timeout);
		this.agent = agent;
		this.buingLoad = buingLoad;
		this.links = links;
		this.spentMoney = spentMoney;
		this.load = load;
	}
	@Override
	protected void onWake() {
		super.onWake();
		int hourD = TimeUtil.CurrentHour();
		buingLoad[0]=load[hourD];
		//Регистрация топика, покупка	
		myAgent.addBehaviour(new CreateTopic(this.agent, buingLoad,spentMoney,links,load));	
		//запуск тикера в следующем часу через вэйкера, который запустит поведение покупки тикера в начале сл часа
		
		
	}
	
	

}
