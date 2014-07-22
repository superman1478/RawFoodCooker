package rawFoodCooker;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Player;

import methods.MyCalculations;
import methods.MyMethods;

public class CookingWatcher extends Thread {

	private long lastCooking;

	private ClientContext ctx;
	
	public CookingWatcher(ClientContext ctx) {
		this.ctx = ctx;
		lastCooking = System.currentTimeMillis() - 2001;
	}

	public double secondsSinceLastCooking() {
		return MyCalculations.getSecondsSince(lastCooking);
	}

	@Override
	public void run() {
		try {
			Player me;
			while (!ctx.controller.isStopping()) {
				me = ctx.players.local();
				if (me.animation() != -1 && !me.inMotion()) {
					lastCooking = System.currentTimeMillis();
				}
				sleep(200);
			}
		}catch(Exception e) {
			MyMethods.println("CookingWatcher thread caught exception");
			e.printStackTrace();
		}
	}

}