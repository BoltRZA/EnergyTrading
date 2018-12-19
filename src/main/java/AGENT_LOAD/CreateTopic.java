package AGENT_LOAD;

import TIME.TimeUtil;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import java.util.Observable;
import java.util.Observer;

public class CreateTopic extends OneShotBehaviour {
	private Agent agent;
	private int[] buingLoad;
	private double[] spentMoney;
	private AID[] links;
	private int[] load;
	public CreateTopic(Agent agent, int[] buingLoad, double[] spentMoney, AID[] links, int[] load) {
		this.agent = agent;
		this.buingLoad = buingLoad;
		this.links = links;
		this.spentMoney = spentMoney;
		this.load = load;
	}


	public void action() {
		TopicManagementHelper topicHelper;
		try {
			//Создание топика
			topicHelper = (TopicManagementHelper) myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
			final AID topic = topicHelper.createTopic(myAgent.getLocalName());
			topicHelper.register(topic);
			System.out.println(agent.getLocalName()+topic.getName());
					//Поведение отправка сообщения о начале торгов
					myAgent.addBehaviour(new MessageForBuy(this.agent, buingLoad,spentMoney,links,load,topic,3000));
					//Поведения по сохранению лучшей цены
					myAgent.addBehaviour(new BestPriseBeh(this.agent, buingLoad,spentMoney,links,load,topic));
			//Запуск тикера покупающего ЭЭ каждый час через вэйкера
			long nextHour = TimeUtil.getSecMillTillNextHour();
			myAgent.addBehaviour(new WakerForBuy(this.agent,nextHour, buingLoad,spentMoney,links,load,topic));

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
