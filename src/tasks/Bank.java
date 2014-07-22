package tasks;

import methods.MyMethods;

import org.powerbot.script.rt6.ClientContext;

import rawFoodCooker.AreaInfo;
import rawFoodCooker.Task;

public class Bank extends Task {
	
	AreaInfo areaInfo;

	public Bank(ClientContext ctx, AreaInfo areaInfo) {
		super(ctx);
		this.areaInfo = areaInfo;
	}

	@Override
	public boolean activate() {
		return (ctx.bank.opened()
				|| (ctx.backpack.select().id(script().getSelectedFood().getId()).count() == 0 
				&& ctx.bank.nearest().tile().distanceTo(ctx.players.local()) <= 4))
				&& !ctx.players.local().inMotion();
	}

	@Override
	public void execute() {
		if (ctx.bank.opened()) {
			if (ctx.backpack.select().count() > 0
					&& ctx.backpack.select().id(script().getSelectedFood().getId()).count() == 0) {
				if (ctx.bank.depositInventory()) {
					MyMethods.sleep(400, 800);
				}
			} else if (ctx.backpack.select().count() == 0) {
				if (ctx.bank.select().id(script().getSelectedFood().getId()).count(true) == 0) {
					System.out.println("Out of " + script().getSelectedFood().name() + "(s)");
					ctx.controller.stop();
				} else {
					if (ctx.bank.withdraw(script().getSelectedFood().getId(), 0)) {
						MyMethods.sleep(800, 1100);
					}
				}
			} else {
				if (ctx.bank.close()) {
					MyMethods.sleep(100, 300);
				}
			}
		} else {
			if (ctx.bank.open()) {
				MyMethods.sleep(600, 900);
			}
		}
	}

}
