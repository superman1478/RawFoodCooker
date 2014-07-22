package tasks;

import methods.MyMethods;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import rawFoodCooker.Task;

public class Obsticale extends Task {

	public Obsticale(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		GameObject closedDoor = ctx.objects.select().at(script().getAreaInfo().getClosedDoorTile()).poll();
		Tile cookingTile = script().getAreaInfo().getCookingTile();
		return closedDoor.valid()
				&& (ctx.backpack.select().id(script().getSelectedFood().getId()).count() > 0
						&& !cookingTile.matrix(ctx).reachable())
				|| (ctx.backpack.select().id(script().getSelectedFood().getId()).count() == 0
						&& !script().getAreaInfo().getBankingTile().matrix(ctx).reachable())
				&& !ctx.players.local().inMotion();
	}

	@Override
	public void execute() {
		GameObject closedDoor = ctx.objects.select().at(script().getAreaInfo().getClosedDoorTile()).poll();
		if (ctx.players.local().tile().distanceTo(closedDoor) > 4) {
			if (ctx.movement.step(closedDoor.tile())) {
				MyMethods.sleep(800, 1100);
			}
		} else {
			if (closedDoor.interact("Open")) {
				MyMethods.sleep(500, 800);
			}
		}
	}

}
