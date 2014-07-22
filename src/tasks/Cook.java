package tasks;

import methods.DebugMethods;
import methods.MyMethods;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;

import rawFoodCooker.Task;

public class Cook extends Task {

	public Cook(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		DebugMethods.println("cooking tile reachable: " + script().getAreaInfo().getCookingTile().tile().matrix(ctx).reachable());
		GameObject range = ctx.objects.select().id(script().getAreaInfo().getRangeId()).nearest().poll();
		DebugMethods.println("range.inViewport(): " + range.inViewport());
		DebugMethods.println("bank.opened() " + !ctx.bank.opened());
		return ctx.backpack.select().id(script().getSelectedFood().getId()).count() > 0
				&& script().getCookingWatcher().secondsSinceLastCooking() > 2.5d
				&& script().getAreaInfo().getCookingTile().tile().matrix(ctx).reachable()
				&& range.inViewport()
				&& !ctx.bank.opened();
	}

	@Override
	public void execute() {
		Component component = ctx.widgets.component(1370, 20);
		if (component.valid()) {
			if (component.click()) {
				MyMethods.sleep(500, 800);
			}
		} else {
			GameObject range = ctx.objects.select().id(script().getAreaInfo().getRangeId()).nearest().poll();
			if (!ctx.players.local().inMotion()) {
				if (script().getFoodSuccessfullyCooked() == 0) {
					cookFirstTime(range);
				} else {
					if (range.interact("Cook")) {
						MyMethods.sleep(700, 1100);
					}
				}
			}
		}
	}

	private void cookFirstTime(GameObject range) {

		if (script().getDebug()) {
			System.out.println("cookFirstTime(GameObject range)");
		}

		Item rawFood = ctx.backpack.select().id(script().getSelectedFood().getId()).poll();
		if (ctx.backpack.selectedItemIndex() == -1) {
			if (script().getDebug()) {
				System.out.println("need to select item to use");
			}
			if (rawFood.valid()) {
				if (script().getDebug()) {
					System.out.println("clicking use on item");
				}
				if (rawFood.interact("Use")) {
					MyMethods.sleep(400, 700);
				}
			}
		} else {
			if (rawFood.valid()) {
				if (script().getDebug()) {
					System.out.println("using " + rawFood.name()
							+ " on "
							+ script().getAreaInfo().getHotSurfaceName());
				}
				if (range.interact("Use", rawFood.name() + " -> " + script().getAreaInfo().getHotSurfaceName())) {
					MyMethods.sleep(900, 1100);
				}
			}
		}
	}

}
