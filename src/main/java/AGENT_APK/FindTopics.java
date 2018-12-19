package AGENT_APK;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.behaviours.WakerBehaviour;
import jade.core.messaging.TopicManagementHelper;


public class FindTopics extends WakerBehaviour {
	private Agent agent;
	private AID[] links;
	private double[] money;
	private double[] akum;
	private long timeout;
	private int[] countLink;
	private double maxPrice;

	public FindTopics(Agent agent, AID[] links, double[] money, double[] akum,int[] counLink,double maxPrice, long timeout) {
		super(agent,timeout);
		this.agent = agent;
		this.akum = akum;
		this.links = links;
		this.money = money;
		this.timeout = timeout;
		this.countLink = counLink;
		this.maxPrice = maxPrice;
	}
	@Override
	protected void onWake() {
		super.onWake();
		//���������� �������������� ������������
		int count = 0;
		for ( int i = 0; i < links.length; i++) {
			if (links[i] != null) {
				count++;
			}
		}
		countLink[0] = count;
		//����������� � ������ ��������� �� ����� 
		for (AID l:links) {
			if (l!=null) {
				String name = l.getLocalName();
				TopicManagementHelper topicHelper;
				try {
					topicHelper = (TopicManagementHelper) myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
					final AID topic = topicHelper.createTopic(name);
					topicHelper.register(topic);
					myAgent.addBehaviour(new WaitingForRequest(myAgent,links,money,akum,countLink,maxPrice,topic));
					//System.out.println(agent.getLocalName()+topic.getName());
					
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}
	}
}
}
