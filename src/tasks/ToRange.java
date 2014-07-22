package tasks;

import methods.MyMethods;

import org.powerbot.script.rt6.ClientContext;

import rawFoodCooker.Task;

public class ToRange extends Task {

	public ToRange(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(script().getSelectedFood().getId()).count() > 0
				&& !ctx.bank.opened()
				&& script().getAreaInfo().getCookingTile().matrix(ctx).reachable()
				&& script().getAreaInfo().getCookingTile().distanceTo(ctx.players.local()) > 3;
	}

	@Override
	public void execute() {
		if (ctx.movement.step(script().getAreaInfo().getCookingTile())) {
			MyMethods.sleep(100, 300);
		}
	}

}
