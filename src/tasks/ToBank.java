package tasks;

import methods.MyMethods;

import org.powerbot.script.rt6.ClientContext;

import rawFoodCooker.Task;

public class ToBank extends Task {

	public ToBank(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(script().getSelectedFood().getId()).count() == 0
				&& script().getAreaInfo().getBankingTile().matrix(ctx).reachable()
				&& script().getAreaInfo().getBankingTile().distanceTo(ctx.players.local()) > 4;
	}

	@Override
	public void execute() {
		if (ctx.movement.step(script().getAreaInfo().getBankingTile())) {
			MyMethods.sleep(300, 500);
		}
	}

}
